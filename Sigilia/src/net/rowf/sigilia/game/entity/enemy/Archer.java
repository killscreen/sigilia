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

public class Archer extends Enemy {
	private static final Vector ORIGIN = new Vector(0,0,1);
	private static final Projectile projectile = new Arrow();
	
	
	@Override
	protected void applyAdditional(Entity e) {
		ArcherController controller = new ArcherController();
		e.setComponent(Motion.class, controller);
		e.setComponent(Intellect.class, controller);
		e.setComponent(Animator.class, controller);
	}
	
	private static class ArcherController implements Intellect, Motion, Animator {
		private static final float THINK_DELAY = 0.1f;
		private static final float FIRE_FREQUENCY = 0.02f;
		private static Random random = new Random();
		private float nextThink = 0;
		private float nextToss = 0;
		private boolean shouldFire = false;
		private ArcherFrame archerFrame = ArcherFrame.FINISH;
		
		// Note: No y-movement
		
		@Override
		public void think(Entity e, float timeStamp) {
			if (timeStamp > nextThink) {
				nextThink = timeStamp + THINK_DELAY;
				if (archerFrame == ArcherFrame.FINISH && random.nextFloat() < FIRE_FREQUENCY) {
					archerFrame = ArcherFrame.START;
				} else if (shouldFire) {
					shouldFire = false;
					Position p = e.getComponent(Position.class);
					if (p != null) {
						e.setComponent(Spawn.class, 
								projectile.spawnProjectile(p.getX() - 0.8f, p.getY() + 0.8f, p.getZ() - 0.25f, new Vector(random.nextFloat() - random.nextFloat(), random.nextFloat() * 0.33f, 1)));
					}
				}
			}					
		}

		@Override
		public void move(Entity e, float timeStep) {
			Position p = e.getComponent(Position.class);
			if (p != null) {
			}
		}

		@Override
		public void animate(Entity entity, Animation animation) {
			animation.setNextFrame(archerFrame.name, archerFrame.duration);
			if (archerFrame != ArcherFrame.FINISH) {
				if (archerFrame == ArcherFrame.UNDRAW) {
					shouldFire = true;
				}
				archerFrame = ArcherFrame.values()[archerFrame.ordinal() + 1];
			}
		}
		
		private enum ArcherFrame {
			START("Squat", 0.25f),
			DRAW("Draw", 0.15f),
			FIRE("Fire", 0.15f),
			UNDRAW("Draw", 0.1f),
			RETURN("Squat", 0.25f),
			FINISH("Crouch", 0.25f)
			;
			public final String name;
			public final float duration;
			
			private ArcherFrame(String name, float duration) {
				this.name = name;
				this.duration = duration;
			}
		}
	}

}
