package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Boundary;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.geometry.Vector;

public class BoundingBox extends Position implements Boundary {
	private float width;
	private float height;
	private float depth;
	
	private Bound minimum = new Bound(-1);
	private Bound maximum = new Bound(1);
	
	public BoundingBox(float x, float y, float z, float sz) {
		this(x,y,z,sz,sz,sz);
	}
	
	public BoundingBox(float x, float y, float z, Vector sz) {
		this(x,y,z,sz.getX(), sz.getY(), sz.getZ());
	}
	
	public BoundingBox(float x, float y, float z, float width, float height, float depth) {
		super(x,y,z);
		this.width = width;
		this.height = height;
		this.depth = depth;
		shift(0,0,0);
		
	}
	@Override
	public Vector getMinimum() {
		return minimum;
	}

	@Override
	public Vector getMaximum() {
		return maximum;
	}
	@Override
	public boolean touches(Entity other) {
		Vector otherMin = null;
		Vector otherMax = null;
		
		// First, check bounding box
		Boundary otherBound = other.getComponent(Boundary.class);
		if (otherBound != null) {
			otherMin = otherBound.getMinimum();
			otherMax = otherBound.getMaximum();
		}
		
		// Otherwise, try Position
		Position pos = other.getComponent(Position.class);
		if (pos != null) {
		    otherMin = pos;
		    otherMax = pos;	
		}		

		return (otherMin != null &&
				otherMax != null &&
				otherMin.getX() < maximum.getX() && 
				otherMin.getY() < maximum.getY() &&
				otherMin.getZ() < maximum.getZ() &&
				otherMax.getX() > minimum.getX() &&
				otherMax.getY() > minimum.getY() &&
				otherMax.getZ() > minimum.getZ());
	}
	
	
	
	@Override
	public void shift(float dx, float dy, float dz) {
		super.shift(dx, dy, dz);
		minimum.setCenter(this);
		maximum.setCenter(this);
	}

	private class Bound extends Vector {
		private float sign; 
		
		public Bound (float sign) {
			super(0,0,0);
			this.sign = sign;
		}
		
		public void setCenter(Vector center) {
			elements[0] = center.getX() + sign * width / 2;
			elements[1] = center.getY() + sign * height / 2;
			elements[2] = center.getZ() + sign * depth / 2;			
		}
	}
}