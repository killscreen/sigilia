package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Entity;
import android.util.Log;

/**
 * Implements motion effected by gravity
 * 
 * @author woeltjen
 *
 */
public class NewtonianMotion extends Velocity implements Motion {
	public static final float GRAVITY = -8f;
	private float gravity;
	
	/**
	 * 
	 * @param x initial x velocity
	 * @param y initial y velocity
	 * @param z initial z velocity
	 */
	public NewtonianMotion(float x, float y, float z, float g) {
		super(x, y, z);
		this.gravity = g;
	}
	/**
	 * 
	 * @param x initial x velocity
	 * @param y initial y velocity
	 * @param z initial z velocity
	 */	
	public NewtonianMotion(float x, float y, float z) {
		this(x,y,z,GRAVITY);
	}

	@Override
	public void move(Entity e, float timeStep) {
		elements[1] += gravity * timeStep; // TODO: Generalize to forces/acceleration?
		
		float g = gravity * timeStep;
		if (g == 0) Log.e(getClass().getName(), "GRAVITY REDUCED TO NOTHING");
		
		Position p = e.getComponent(Position.class);
		if (p != null) {
			p.shift(getX()*timeStep, getY()*timeStep, getZ()*timeStep);
		}		
	}

}
