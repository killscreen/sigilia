package net.rowf.sigilia.game.entity.enemy;

import java.util.Random;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.Animator;
import android.util.FloatMath;
import android.util.Log;

public class Goblin extends Enemy {

	@Override
	protected void applyAdditional(Entity e) {
		GoblinController controller = new GoblinController();
		e.setComponent(Motion.class, controller);
		e.setComponent(Intellect.class, controller);
		e.setComponent(Animator.class, new GoblinWalk());
	}
	
	private static class GoblinController implements Intellect, Motion {
		private static Random random = new Random();
		private float nextThink = 0; 
		private float dx = 0;
		private float dz = 0;
		// Note: No y-movement
		
		@Override
		public void think(Entity e, float timeStamp) {
			if (timeStamp > nextThink) {
				dx = (float) (random.nextFloat() * 2f) - 1f;
				dz = (float) (random.nextFloat() * 2f) - 1f;
				nextThink = timeStamp + (float) (random.nextFloat() * 0.5) + 0.5f;
			}					
		}

		@Override
		public void move(Entity e, float timeStep) {
			Position p = e.getComponent(Position.class);
			if (p != null) {
				// Bound motion
				if (p.getZ() < 2) {
					dz = 2 - p.getZ();
				}
				if (p.getZ() > 10) {
					dz = 10 - p.getZ();
				}
				if (p.getX() < -6) {
					dx = -6 - p.getX();
				}
				if (p.getX() > 6) {
					dx = 6 - p.getX();
				}
				
				Log.d("GOBLIN", "dx,dz" + dx + " " + dz);
				// Move the goblin
				p.shift(dx * timeStep, 0, dz * timeStep);
			}
		}
		
	}
	
	private static class GoblinMotion implements Motion {
		private float t = 0;
		@Override
		public void move(Entity e, float timeStep) {
			t += timeStep;
			Position p = e.getComponent(Position.class);
			p.shift(0.0f, 0.0f,
					timeStep * FloatMath.sin(t));
		}  
	};

	private static class GoblinWalk implements Animator {
		private String[] cycle = {"Facing", "Stepleft", "Facing", "Stepright"};
		private int frame = (int) (Math.random() * cycle.length);
		
		@Override
		public void animate(Entity entity, Animation animation) {
			frame++;
			frame = frame % cycle.length;
			animation.setNextFrame(cycle[frame], 0.5f);
		}
	};
}
