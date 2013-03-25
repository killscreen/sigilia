package net.rowf.sigilia.game.engine;

import java.util.ArrayList;
import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.metadata.Liveness;

public class RemovalEngine implements Engine {
	private List<Criterion> criteria = new ArrayList<Criterion>();
	
	// Reuse to avoid reallocating memory every cycle
	private List<Entity> toRemove = new ArrayList<Entity>();
	
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (Entity e : entities) {
			for (Criterion c : criteria) {
				if (c.evaluate(e)) {
					toRemove.add(e);
					break;
				}
			}
		}
		entities.removeAll(toRemove);
		toRemove.clear();
	}
	
	public RemovalEngine addCriterion(Criterion c) {
		criteria.add(c);
		return this;
	}
	
	public RemovalEngine removeCriterion(Criterion c) {
		criteria.remove(c);
		return this;
	}
	

	
	/**
	 * Convenience method to specify that entities are farther than some distance
	 * 
	 * @param distance
	 * @return
	 */
	public static Criterion fartherThan(final float distance) {
		return new Criterion() {
			@Override
			public boolean evaluate(Entity e) {
				Position p = e.getComponent(Position.class);
				return (p != null) && (p.getZ() > distance);
			}			
		};
	}
	
	public static final Criterion LIVENESS = new Criterion() {
		@Override
		public boolean evaluate(Entity e) {
			Liveness liveness = e.getComponent(Liveness.class);
			if (liveness != null) {
				return !liveness.isAlive();
			}
			return false;
		}
	};
	
	public static interface Criterion {
		/**
		 * Determine whether or not an entity should be removed.
		 * @param e the entity to consider
		 * @return true if entity should be scheduled for removal
		 */
		public boolean evaluate(Entity e);
	}
}
