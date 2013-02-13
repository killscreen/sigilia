package net.rowf.sigilia.game.engine;

import java.util.Comparator;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;

public abstract class SpatialEngine implements Engine {
	public static final Comparator<Entity> Z_ORDER_COMPARATOR = new Comparator<Entity>() {
		@Override
		public int compare(Entity lhs, Entity rhs) {
			Position l = lhs.getComponent(Position.class);
			Position r = rhs.getComponent(Position.class);
			
			/* First, omit cases where an entity has no position. */
			if (l == null && r == null) return 0;
			if (l == null && r != null) return -1;
			if (l != null && r == null) return 1;
			
			return (int) Math.signum(l.getZ() - r.getZ());
		}
	};

	
	public Comparator<Entity> ordering() {
		return Z_ORDER_COMPARATOR;
	}
}
