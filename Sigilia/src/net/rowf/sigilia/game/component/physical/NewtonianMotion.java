package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.geometry.Vector;

/**
 * Implements gravity
 * 
 * @author woeltjen
 *
 */
public class NewtonianMotion extends Vector implements Motion {
	public static final float GRAVITY = -1.25f;
	
	/**
	 * 
	 * @param x initial x velocity
	 * @param y initial y velocity
	 * @param z initial z velocity
	 */
	public NewtonianMotion(float x, float y, float z) {
		super(x, y, z);
	}

	@Override
	public void move(Entity e, float timeStep) {
		elements[1] += GRAVITY * timeStep * timeStep; // TODO: Generalize to forces/acceleration?
		
		Position p = e.getComponent(Position.class);
		if (p != null) {
			p.shift(getX()*timeStep, getY()*timeStep, getZ()*timeStep);
		}		
	}

}
