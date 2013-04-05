package net.rowf.sigilia.game.component;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.geometry.Vector;

public interface Boundary extends Component {
	public Vector getMinimum();
	public Vector getMaximum();
	
	public boolean touches(Entity other);
}
