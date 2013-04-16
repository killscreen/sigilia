package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.geometry.Vector;

public class Size implements Component {
	private Vector sz;

	public Size(float sz) {
		super();
		this.sz = new Vector(sz,sz,sz);
	}
	
	public Size(float x, float y, float z) {
		this.sz = new Vector(x,y,z);
	}
	
	public Vector get() {
		return sz;
	}
}
