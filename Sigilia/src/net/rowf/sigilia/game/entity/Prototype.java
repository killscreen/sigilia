package net.rowf.sigilia.game.entity;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

/**
 * A prototype describes all of the initial set up (the adding of 
 * specific Components) used to define an entity of some type.
 * 
 * @author woeltjen
 */
public interface Prototype extends Component {
	public void apply(Entity e);	
}
