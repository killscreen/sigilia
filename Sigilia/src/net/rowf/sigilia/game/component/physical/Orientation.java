package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

/**
 * Describes the orientation (rotation) of an entity in space.
 * @author woeltjen
 *
 */
public interface Orientation extends Component {
	public Vector getRotation(Entity e);
}
