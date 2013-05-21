package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

/**
 * Describes the movement of an entity, as run by the MotionEngine
 * 
 * @author woeltjen
 *
 */
public interface Motion extends Component {
	public void move (Entity e, float timeStep);
}
