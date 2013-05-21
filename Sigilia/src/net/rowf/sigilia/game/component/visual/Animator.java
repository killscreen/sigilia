package net.rowf.sigilia.game.component.visual;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

/**
 * An Animator is responsible for handling changes to the animation 
 * of an entity, typically selecting the next key frame after the 
 * current target key frame is reached.
 * 
 * @author woeltjen
 *
 */
public interface Animator extends Component {
	public void animate(Entity entity, Animation animation);
}
