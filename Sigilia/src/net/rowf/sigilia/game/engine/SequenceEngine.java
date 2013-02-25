package net.rowf.sigilia.game.engine;

import java.util.ArrayList;
import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;

public class SequenceEngine implements Engine {
	private List<Engine> engines = new ArrayList<Engine>();

	public SequenceEngine() {		
	}
	
	public SequenceEngine(List<Engine> engines) {
		this.engines.addAll(engines);
	}
	
	public void add(Engine engine) {
		engines.add(engine);
	}
	
	public void remove(Engine engine) {
		engines.remove(engine);
	}
	
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (Engine engine : engines) {
			engine.runCycle(entities, timeStamp);
		}
	}
}
