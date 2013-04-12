package net.rowf.sigilia.game.engine;

import java.util.ArrayList;
import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Spawn;

/**
 * Used to introduce new entities into the scene. An Goblin, for 
 * instance, may throw a rock by spawning a new rock entity.
 * 
 * @author woeltjen
 *
 */
public class SpawnEngine implements Engine {
	private List<Entity> spawned = new ArrayList<Entity>();
	
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		spawned.clear();
		for (Entity e : entities) {
			Spawn spawn = e.getComponent(Spawn.class);
			if (spawn != null) {
				spawned.add(spawn.getEntity());
				e.setComponent(Spawn.class, null);
			}
		}
		entities.addAll(spawned);
	}

}
