package net.rowf.sigilia.game.component.metadata;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

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
