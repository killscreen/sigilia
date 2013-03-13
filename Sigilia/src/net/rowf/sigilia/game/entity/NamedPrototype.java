package net.rowf.sigilia.game.entity;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Name;

public abstract class NamedPrototype implements Prototype {
	private Name name;
	
	public NamedPrototype() {
		name = new Name(getClass().getSimpleName());
	}
	
	@Override
	public void apply(Entity e) {
		e.setComponent(Name.class, name);
		applyAdditional(e);
	}
	
	protected abstract void applyAdditional(Entity e);

}
