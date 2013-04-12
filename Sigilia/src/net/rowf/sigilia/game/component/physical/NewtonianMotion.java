package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.geometry.Vector;
import android.util.Log;

/**
 * Implements gravity
 * 
 * @author woeltjen
 *
 */
public class NewtonianMotion extends Vector implements Motion {
	public static final float GRAVITY = -8f;
	
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
		elements[1] += GRAVITY * timeStep; // TODO: Generalize to forces/acceleration?
		
		float g = GRAVITY * timeStep;
		if (g == 0) Log.e(getClass().getName(), "GRAVITY REDUCED TO NOTHING");
		
		Position p = e.getComponent(Position.class);
		if (p != null) {
			p.shift(getX()*timeStep, getY()*timeStep, getZ()*timeStep);
		}		
	}

}
