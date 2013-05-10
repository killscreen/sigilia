package net.rowf.sigilia.game.component.mental;

import java.util.List;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

/**
 * Handles the "intelligence" of the entity
 * 
 * @author woeltjen
 *
 */
public interface Intellect extends Component {
	/**
	 * Perform one AI evaluation.
	 * 
	 * @param e the entity being controlled by this intellect
	 * @param timeStamp the current timeStamp
	 * @param world TODO
	 */
	public void think (Entity e, float timeStamp, List<Entity> world);
}
