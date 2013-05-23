package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

/**
 * Describes an axis-aligned bounding box around an entity
 * @author woeltjen
 *
 */
public interface Boundary extends Component {
	public Vector getMinimum();
	public Vector getMaximum();
	
	public boolean touches(Entity other);
}
