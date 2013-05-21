package net.rowf.sigilia.game;

import java.util.List;

/**
 * An engine is responsible for running some aspect of game logic, 
 * typically by iterating across the current set of entities.
 * @author woeltjen
 *
 */
public interface Engine {
	/**
	 * Run one game cycle for this engine.
	 * 
	 * Note that engines may change the ordering and contents of the list 
	 * of entities provided, and may also change entities themselves.
	 *  
	 * @param entities the entities visible to this engine
	 * @param timeStamp the current timeStamp, in seconds
	 */
	public void runCycle(List<Entity> entities, float timeStamp);
}
