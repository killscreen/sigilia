package net.rowf.sigilia.game.component.metadata;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

/**
 * Spawn indicates that one Entity wishes to spawn another Entity into 
 * the game (such as when a Goblin throws a Rock). Spawns are processed 
 * by the SpawnEngine.
 * 
 * @see net.rowf.sigilia.game.engine.SpawnEngine
 * 
 * @author woeltjen
 *
 */
public class Spawn implements Component {
	private Entity entity;

	public Spawn(Entity entity) {
		super();
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
