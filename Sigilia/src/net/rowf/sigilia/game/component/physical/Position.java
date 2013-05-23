package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;

/**
 * Describes the position or location of an entity in space. Note 
 * that the player is always assumed to be fixed at the origin.
 * @author woeltjen
 *
 */
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
