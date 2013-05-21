package net.rowf.sigilia.game.entity;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Name;

/**
 * Convenience superclass for named entities. A NamedPrototype will 
 * attach its run-time simple class name as a Name object to 
 * the entity it is used to create. 
 * 
 * @author woeltjen
 *
 */
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
