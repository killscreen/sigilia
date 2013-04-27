package net.rowf.sigilia.game.entity;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Boundary;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.visual.Animation;

public class Player extends NamedPrototype {
	private BoundingBox BOUND = new BoundingBox(0, 0, 1, 1.05f);
	
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Health.class, new Health(100));
		e.setComponent(Animation.class, new HealthAnimation(e.getComponent(Health.class)));
		e.setComponent(Boundary.class, BOUND);
		e.setComponent(Position.class, BOUND);
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Liveness.class, Liveness.ALIVE);
	}

	private class HealthAnimation extends Animation {
		private Health health;

		public HealthAnimation(Health health) {
			super();
			this.health = health;
		}

		@Override
		public float getProgress() {
			return health.getRatio();
		}
		
		
	}
}
