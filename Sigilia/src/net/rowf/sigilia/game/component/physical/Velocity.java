package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;

/**
 * Describes the current velocity of an entity in three-dimensional space. 
 * @author woeltjen
 *
 */
public class Velocity extends Vector implements Component {
	public Velocity(float x, float y, float z) {
		super(x, y, z);
	}
}
