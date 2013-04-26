package net.rowf.sigilia.game.entity.enemy;

import java.util.Random;

import android.util.FloatMath;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.metadata.Spawn;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.Animator;
import net.rowf.sigilia.geometry.Vector;

public class Wizard extends Enemy {
	private static final Vector ORIGIN = new Vector(0,0,1);
	private static final Projectile projectile = new Fireball();
	
	
	@Override
	protected void applyAdditional(Entity e) {
		WizardController controller = new WizardController();
		e.setComponent(Motion.class, controller);
		e.setComponent(Intellect.class, controller);
		e.setComponent(Animator.class, controller);
	}
	
	private static class WizardController implements Intellect, Motion, Animator {
		private static final float THROW_FREQUENCY = 0.15f;
		private static Random random = new Random();
		private boolean throwToggle = false;
		private boolean animToggle = false;
		private float nextThink = 0;
		private float nextToss = 0;
		private float dx = 0;
		private float dy = 0;
		private float dz = 0;
		// Note: No y-movement
		
		@Override
		public void think(Entity e, float timeStamp) {
			if (timeStamp > nextThink) {
				dx += random.nextFloat() * 8f - 4f;
				dy += FloatMath.sin(timeStamp) * 1f;
				dz += random.nextFloat() * 4f - 2f;
				nextThink = timeStamp + (float) (random.nextFloat() * 0.5) + 0.5f;
				if (random.nextFloat() < THROW_FREQUENCY) {
					Position p = e.getComponent(Position.class);
					if (p != null) {
						float x = p.getX() + (throwToggle ? 1f : -1f);
						throwToggle = !throwToggle;
						e.setComponent(Spawn.class, projectile.spawnProjectile(x, p.getY() + 0.25f, p.getZ() - 1, ORIGIN));
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
			animToggle = !animToggle;
			String frameSuffix = animToggle ? "1" : "2";
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
}
