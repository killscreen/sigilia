package net.rowf.sigilia.game.component.visual;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

public interface Animator extends Component {
	public void animate(Entity entity, Animation animation);
}
