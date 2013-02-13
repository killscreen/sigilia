package net.rowf.sigilia.game;

import java.util.List;

public interface Engine {
	public void runCycle(List<Entity> entities, float timeStamp);
}
