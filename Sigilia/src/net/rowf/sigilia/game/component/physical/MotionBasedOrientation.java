package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Entity;

/**
 * Describes the orientation of an entity which is intended to change 
 * based on its motion, as is the case for arrows.
 * 
 * @author woeltjen
 *
 */
public class MotionBasedOrientation extends Vector implements Orientation {
	public static final Vector NONE = new Vector(0,0,0);
	private boolean observed = false;
	private Vector orientation = NONE;

	public MotionBasedOrientation() {
		super(0,0,0);
	}
	
	@Override
	public Vector getRotation(Entity e) {
		Position p = e.getComponent(Position.class);
		if (p != null) {
			if (observed) {
				float deltaX = p.getX() - getX();
				float deltaY = p.getY() - getY();
				float deltaZ = p.getZ() - getZ();
				//    PI/2
				//    |
				//     /
				//    / 
				//    -----
				float rotY   = (deltaX != 0 || deltaZ != 0) ?
						-3.14159f + (float) Math.atan2(-deltaX, deltaZ) : 0f;
				float rotX   = (deltaY != 0 || deltaZ != 0) ?
						(float) Math.atan2(deltaZ, deltaY) : 0f;

				if (deltaX != 0 || deltaZ != 0 || deltaY != 0) {
					orientation = new Vector(0, rotY, 0);
				}
			} else {
				elements[0] = p.getX();
				elements[1] = p.getY();
				elements[2] = p.getZ();
			}
		}
		return orientation;
	}
}
