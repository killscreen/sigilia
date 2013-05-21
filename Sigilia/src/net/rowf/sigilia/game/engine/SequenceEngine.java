package net.rowf.sigilia.game.engine;

import java.util.ArrayList;
import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;

/**
 * Runs multiple engines in a sequence.
 * 
 * @author woeltjen
 */
public class SequenceEngine implements Engine {
	private List<Engine> engines = new ArrayList<Engine>();

	public SequenceEngine(List<Engine> engines) {
		this.engines.addAll(engines);
	}
	
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (Engine engine : engines) {
			engine.runCycle(entities, timeStamp);
		}
	}
}
