package net.rowf.sigilia.game.engine;

import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.physical.Motion;

public class MotionEngine implements Engine {
	private static final float EPSILON  = 0.001f;
	private float previous = Float.NEGATIVE_INFINITY; 
			
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		if (previous == Float.NEGATIVE_INFINITY) {
			previous = timeStamp;
		}
		float timeStep = timeStamp - previous;
		if (timeStep >= EPSILON) {
			for (Entity e : entities) {
				Motion m = e.getComponent(Motion.class);
				if (m != null) m.move(e, timeStep);
			}
			previous = timeStamp;
		}
	}
}
