package net.rowf.sigilia.game.collision;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

public interface Impact extends Component {
	public void impact(Entity source, Entity other);
}
