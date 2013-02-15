package net.rowf.sigilia.game.component;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.geometry.Vector;

public class Position extends Vector implements Component {

	public Position(float x, float y, float z) {
		super(x, y, z);
	}

	public void shift(float dx, float dy, float dz) {
		elements[0] += dx;
		elements[1] += dy;
		elements[2] += dz;
	}
	
}
