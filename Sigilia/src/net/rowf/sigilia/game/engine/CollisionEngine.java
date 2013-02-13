package net.rowf.sigilia.game.engine;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Boundary;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.geometry.Vector;

public class CollisionEngine implements Engine {
	private static final Comparator<Entity> Z_MINIMUM_COMPARATOR = new Comparator<Entity>() {
		@Override
		public int compare(Entity lhs, Entity rhs) {
			Vector l = getVector(lhs);
			Vector r = getVector(rhs);
			
			/* First, omit cases where an entity has no position. */
			if (l == null && r == null) return 0;
			if (l == null && r != null) return -1;
			if (l != null && r == null) return 1;
			
			return (int) Math.signum(l.getZ() - r.getZ());
		}
		
		/**
		 * Get a vector that describes the minimum position of an entity
		 * @param e
		 * @return
		 */
		private Vector getVector(Entity e) {
			Boundary b = e.getComponent(Boundary.class);
			if (b != null) return b.getMinimum();
			else           return e.getComponent(Position.class);
		}
	};

	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		Collections.sort(entities, Z_MINIMUM_COMPARATOR);
		for (int i = 0; i < entities.size() - 1; i++) {
			for (int j = i + 1; j < entities.size(); j++) {
				// TODO: Break early if b.z.min > a.z.max
				Entity a = entities.get(i);
				Entity b = entities.get(j);
				if (overlap(a, b)) {
					//TODO: Invoke collision behavior
				}
			}
		}
	}
	
	private boolean overlap(Entity a, Entity b) {
		Boundary bounds = a.getComponent(Boundary.class);		
		if (bounds != null) return bounds.touches(b);
		
		bounds = b.getComponent(Boundary.class);		
		if (bounds != null) return bounds.touches(a);

		return false; // Neither entity occupies space
	}
	

}
