package net.rowf.sigilia.game.engine;

import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.physical.Motion;

public class MotionEngine implements Engine {
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (Entity e : entities) {
			Motion m = e.getComponent(Motion.class);
			if (m != null) m.move(e, timeStamp);
		}
	}
}
