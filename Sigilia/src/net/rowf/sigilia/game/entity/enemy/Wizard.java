package net.rowf.sigilia.game.entity.enemy;

import java.util.List;
import java.util.Random;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.metadata.Lifetime;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Spawn;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.Animator;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.game.entity.weapon.IceWeapon;
import net.rowf.sigilia.game.entity.weapon.VisibleSigil;
import net.rowf.sigilia.geometry.Vector;
import android.util.FloatMath;

public class Wizard extends Enemy {	
	private static final Vector ORIGIN = new Vector(0,0,1);
	private static final Projectile projectile = new Fireball();
	private static final Prototype shield = new IceShield();
	private static final VisibleSigil iceSigil = new IceWeapon().visibleSigil;
	
	@Override
	protected void applyAdditional(Entity e) {
		WizardController controller = new WizardController();
		e.setComponent(Motion.class, controller);
		e.setComponent(Intellect.class, controller);
		e.setComponent(Animator.class, controller);
	}
	
	private static class WizardController implements Intellect, Motion, Animator {
		private static final float THROW_FREQUENCY = 0.25f;
		private static final float SHIELD_FREQUENCY = 0.5f;
		private static final float SHIELD_DURATION = 3f;
		private static Random random = new Random();
		private boolean throwToggle = false;
		private float nextThink = 0;
		private float dx = 0;
		private float dy = 0;
		private float dz = 0;
		private float shieldTime = 0;
		private boolean shieldUp = false;
		// Note: No y-movement
		
		@Override
		public void think(Entity e, float timeStamp, List<Entity> world) {
			if (timeStamp > nextThink) {
				if (timeStamp > shieldTime) {
					if (random.nextFloat() < SHIELD_FREQUENCY) {
						shieldTime = timeStamp + SHIELD_DURATION;
						spawnShield(e);
					}
				}
				shieldUp = timeStamp < shieldTime;
				if (shieldUp) {
					// Stay still if shield is up
					dx = dy = dz = 0;
				} else {
					dx += random.nextFloat() * 8f - 4f;
					dy += FloatMath.sin(timeStamp) * 1f;
					dz += random.nextFloat() * 4f - 2f;
				}
				nextThink = timeStamp + (float) (random.nextFloat() * 0.5) + 0.5f;
				if (shieldUp || random.nextFloat() < THROW_FREQUENCY) {
					Position p = e.getComponent(Position.class);
					if (p != null) {
						float x = p.getX() + (throwToggle ? 1f : -1f) * (shieldUp ? 1.25f : 1);						
						throwToggle = !throwToggle;
						if (e.getComponent(Spawn.class) == null) { // Don't override ice shield
							e.setComponent(Spawn.class, projectile.spawnProjectile(x, p.getY() + 0.5f, p.getZ() - 1, ORIGIN));
						}
					}
				}
			}					
		}

		@Override
		public void move(Entity e, float timeStep) {
			Position p = e.getComponent(Position.class);
			if (p != null) {
				// Bound motion
				// TODO: This should be the world's problem
				if (p.getZ() < 2) {
					dz = 2 - p.getZ() + 1f;
				}
				if (p.getZ() > 10) {
					dz = 10 - p.getZ() - 1f;
				}
				if (p.getX() < (-0.75f * p.getZ())) {
					dx = Math.max(dx, (-0.75f * p.getZ()) - p.getX() + 1f);
				}
				if (p.getX() > ( 0.75f * p.getZ())) {
					dx = Math.min(dx, (0.75f * p.getZ()) - p.getX() - 1f);
				}
				if (p.getY() < (-0.25f * p.getZ())) {
					dy = (-0.25f * p.getZ()) - p.getY() + 1f;
				}
				if (p.getY() > ( 0.25f * p.getZ())) {
					dy = ( 0.25f * p.getZ()) - p.getY() - 1f;
				}				
				// Move the wizard
				p.shift(dx * timeStep, dy * timeStep, dz * timeStep);
			}
		}

		@Override
		public void animate(Entity entity, Animation animation) {
			if (shieldUp) {
				animation.setNextFrame("Raised", 0.25f);
			} else {
				String frameSuffix = "1";
				String framePrefix = "Cast";
				if (Math.abs(dx) > 1) {
					framePrefix = (dx > 0) ? "Left" : "Right";
					frameSuffix = Math.abs(dx) > 2 ? "1" : "2";
				} else {
					frameSuffix = (dx > 0) ? "1" : "2";
				}
				
				animation.setNextFrame(framePrefix + frameSuffix, 0.25f);
			}
		}
		
		private void spawnShield(Entity e) {
			Position p = e.getComponent(Position.class);
			if (p != null) {
				Entity iceShield = new StandardEntity();
				shield.apply(iceShield);
				float dist = FloatMath.sqrt(p.getX() * p.getX() + p.getY() * p.getY() + p.getZ() * p.getZ());
				float scale = (dist-1f) / dist;
				BoundingBox.setBoundary(iceShield, p.getX()*scale, p.getY()*scale, p.getZ()*scale);
				Lifetime lifetime = new Lifetime(SHIELD_DURATION);
				iceShield.setComponent(Liveness.class, lifetime);
				iceShield.setComponent(Intellect.class, lifetime);				
				e.setComponent(Spawn.class, new Spawn(iceShield));
				
				scale = (dist-1.25f) / dist;
				Entity sigil = iceSigil.spawn(p.getX()*scale, p.getY()*scale, p.getZ()*scale);
				iceShield.setComponent(Spawn.class, new Spawn(sigil));
			}
		}
	}
}
