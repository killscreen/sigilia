package net.rowf.sigilia.game.entity.enemy;

import java.util.Random;

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
	private static final Projectile projectile = new Rock();
	
	
	@Override
	protected void applyAdditional(Entity e) {
		WizardController controller = new WizardController();
		e.setComponent(Motion.class, controller);
		e.setComponent(Intellect.class, controller);
		e.setComponent(Animator.class, new WizardAnimator());
	}
	
	private static class WizardController implements Intellect, Motion {
		private static final float THROW_FREQUENCY = 0.15f;
		private static Random random = new Random();
		private float nextThink = 0;
		private float nextToss = 0;
		private float dx = 0;
		private float dz = 0;
		// Note: No y-movement
		
		@Override
		public void think(Entity e, float timeStamp) {
			if (timeStamp > nextThink) {
				dx = (float) (random.nextFloat() * 2f) - 1f;
				dz = (float) (random.nextFloat() * 2f) - 1f;
				nextThink = timeStamp + (float) (random.nextFloat() * 0.5) + 0.5f;
				if (random.nextFloat() < THROW_FREQUENCY) {
					Position p = e.getComponent(Position.class);
					if (p != null) {
						e.setComponent(Spawn.class, projectile.spawnProjectile(p.getX() + 0.25f, p.getY() + 1f, p.getZ() - 1, ORIGIN));
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
					dz = 2 - p.getZ();
				}
				if (p.getZ() > 10) {
					dz = 10 - p.getZ();
				}
				if (p.getX() < -4) {
					dx = -6 - p.getX();
				}
				if (p.getX() > 4) {
					dx = 6 - p.getX();
				}
				
				// Move the goblin
				p.shift(dx * timeStep, 0, dz * timeStep);
			}
		}
		
	}

	private static class WizardAnimator implements Animator {
		private String[] cycle = {"Left1", "Left2", "Right1", "Right2", "Raised", "Still", "Cast1", "Cast2"};
		private int frame = (int) (Math.random() * cycle.length);
		
		@Override
		public void animate(Entity entity, Animation animation) {			
			frame = (int) (Math.random() * cycle.length);
			animation.setNextFrame(cycle[frame], 0.5f);
		}
	};
}
