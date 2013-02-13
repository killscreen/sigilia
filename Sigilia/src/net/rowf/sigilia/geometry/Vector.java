package net.rowf.sigilia.geometry;

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
