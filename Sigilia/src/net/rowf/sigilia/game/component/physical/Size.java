package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;

public class Size implements Component {
	private float sz;

	public Size(float sz) {
		super();
		this.sz = sz;
	}
	
	public float get() {
		return sz;
	}
}
