package net.rowf.sigilia.game.entity.enemy;

import android.util.FloatMath;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.physical.Motion;

public class Goblin extends Enemy {

	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Motion.class, new GoblinMotion());
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

}
