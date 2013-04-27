package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.geometry.Vector;

public interface Orientation extends Component {
	public Vector getRotation(Entity e);
}
