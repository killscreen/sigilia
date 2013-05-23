package net.rowf.sigilia.game.component.physical;

/**
 * A three-component vector, typically used to describe a position 
 * or direction within three-dimensional space. 
 * 
 * @author woeltjen
 *
 */
public class Vector {
	protected final float[] elements;
	
	public Vector(float x, float y, float z) {
		elements = new float[] { x , y , z };
	}
	
	public float getX() {
		return elements[0];
	}
	
	public float getY() {
		return elements[1];		
	}
	
	public float getZ() {
		return elements[2];
	}
}
