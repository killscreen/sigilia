package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

public interface Motion extends Component {
	public void move (Entity e, float timeStamp);
}
