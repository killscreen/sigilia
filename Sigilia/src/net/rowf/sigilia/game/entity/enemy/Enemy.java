package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.entity.NamedPrototype;

public abstract class Enemy extends NamedPrototype {

	@Override
	public void apply(Entity e) {
		// TODO Add normal collision stuffs
		super.apply(e);
	}
	
}