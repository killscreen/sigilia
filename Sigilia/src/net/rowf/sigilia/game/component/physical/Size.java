package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;

/**
 * Describes the Size of a component. Typically used to generate appropriate 
 * BoundingBoxes when instantiating an entity from a prototype
 * @author woeltjen
 *
 */
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
