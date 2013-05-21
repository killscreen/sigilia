package net.rowf.sigilia.game.component.metadata;

import net.rowf.sigilia.game.Component;

/**
 * A text name given to an entity.
 * 
 * @author woeltjen
 *
 */
public class Name implements Component {
	private String name;
	
	public Name(String name) {
		this.name = name;
	}
	
	public String get() {
		return name;
	}
}
