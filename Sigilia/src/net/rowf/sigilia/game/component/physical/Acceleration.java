package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.geometry.Vector;

/**
 * Accumulates acceleration 
 * @author woeltjen
 *
 */
public class Acceleration extends Vector {

	public Acceleration() {
		super(0,0,0);
	}
	
	public void accelerate(float x, float y, float z) {
		elements[0] += x;
		elements[1] += y;
		elements[2] += z;
	}
	
	public void clear() {
		elements[0] = 0;
		elements[1] = 0;
		elements[2] = 0;
	}

}
