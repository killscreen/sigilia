package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.Animator;
import android.util.FloatMath;

public class Goblin extends Enemy {

	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Motion.class, new GoblinMotion());
		e.setComponent(Animator.class, new GoblinWalk());
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
