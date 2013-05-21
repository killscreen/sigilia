package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Entity;

/**
 * Describes the motion of an entity which should move at a constant 
 * velocity in some direction.
 * 
 * @author woeltjen
 *
 */
public class ConstantMotion implements Motion {
	private Vector velocity;
	
	public ConstantMotion(Vector velocity) {
		super();
		this.velocity = velocity;
	}

	@Override
	public void move(Entity e, float timeStep) {
		Position p = e.getComponent(Position.class);
		if (p != null) {
			p.shift(velocity.getX() * timeStep, 
					velocity.getY() * timeStep, 
					velocity.getZ() * timeStep);
		}
	}

}
