package net.rowf.sigilia.game.entity;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

public interface Prototype extends Component {
	public void apply(Entity e);	
}
