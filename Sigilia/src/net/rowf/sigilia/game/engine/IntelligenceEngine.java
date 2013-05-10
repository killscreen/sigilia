package net.rowf.sigilia.game.engine;

import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.mental.Intellect;

public class IntelligenceEngine implements Engine {
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (Entity entity : entities) {
			Intellect intellect = entity.getComponent(Intellect.class);
			if (intellect != null) {
				intellect.think(entity, timeStamp, entities);
			}
		}
	}
}
