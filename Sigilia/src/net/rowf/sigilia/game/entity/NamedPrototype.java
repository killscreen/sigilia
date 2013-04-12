package net.rowf.sigilia.game.entity;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Name;

public abstract class NamedPrototype extends Name implements Prototype {
	
	public NamedPrototype() {
		super("");
	}
	
	@Override
	public void apply(Entity e) {
		e.setComponent(Prototype.class, this);
		e.setComponent(Name.class, this);
		applyAdditional(e);
	}
	
	@Override
	public String get() {
		return getClass().getSimpleName();
	}

	protected abstract void applyAdditional(Entity e);

}
