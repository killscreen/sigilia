package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

/**
 * Handles the behavior of an entity when it collides with another 
 * entity.
 * 
 * @author woeltjen
 *
 */
public interface Impact extends Component {
	public void impact(Entity source, Entity other);
}
