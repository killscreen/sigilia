package net.rowf.sigilia.game.entity.environment;

import java.util.Random;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.entity.NamedPrototype;
import android.util.FloatMath;

/**
 * Describes the floating columns seen in the third scenario.
 * 
 * @author woeltjen
 *
 */
public class Column extends NamedPrototype {
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Size.class, new Size(2f, 4f, 2f));
		e.setComponent(Motion.class, new ColumnMotion());
	}
	
	private static class ColumnMotion implements Motion {
		private static final Random RANDOM = new Random();
		private float t;
		private float s;
		
		public ColumnMotion() {
			t = RANDOM.nextFloat() * (float) Math.PI * 2f;
			s = RANDOM.nextFloat() + 1f;
		}

		@Override
		public void move(Entity e, float timeStep) {
			// Move to a specific y - float sinusoidally
			t += timeStep;
			float y = FloatMath.sin(t*s) * 0.25f;
			Position p = e.getComponent(Position.class);
			if (p != null) {
				y -= p.getY();
				p.shift(0, y, 0);
			}
		}
		
	}
}
