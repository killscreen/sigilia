package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;

/**
 * Time-corrected Verlet integrator
 * 
 * http://lonesock.net/article/verlet.html
 * 
 * NOTE: This implementation is very broken! Should not be used at 
 * this point.
 * 
 * @author woeltjen
 *
 */
public class VerletMotion implements Motion {
	private static final float MINIMUM_TIME_STEP = 0.001f; // 1 ms
	
	@Override
	public void move(Entity e, float timeStamp) {
		Position position = e.getComponent(Position.class);
		if (position == null) return; // Can't move something if there is no position.

		float dx = 0;
		float dy = 0;
		float dz = 0;
		float dt = MINIMUM_TIME_STEP;
		
		PriorPosition prior = e.getComponent(PriorPosition.class);
		if (prior == null) {
			prior = new PriorPosition(position, timeStamp, MINIMUM_TIME_STEP);
			e.setComponent(PriorPosition.class, prior);
		} else {
			dt = timeStamp - prior.getTimestamp();
			if (dt < MINIMUM_TIME_STEP) return;			
			float timeCorrection = dt / prior.getTimestep();
			dx += (position.getX() - prior.getX()) * timeCorrection;
			dy += (position.getY() - prior.getY()) * timeCorrection;
			dz += (position.getZ() - prior.getZ()) * timeCorrection;			
		}
		
		// TODO: Acceleration: dx += acceleration.getX() * dt * dt
		//       Adjust position!
		
		prior.set(position, timeStamp, dt);
	}

}
