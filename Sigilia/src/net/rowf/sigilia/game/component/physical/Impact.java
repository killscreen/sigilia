package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

public interface Impact extends Component {
	public void impact(Entity source, Entity other);
}
