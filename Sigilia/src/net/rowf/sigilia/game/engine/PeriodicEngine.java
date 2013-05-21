package net.rowf.sigilia.game.engine;

import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;

/**
 * A PeriodicEngine wraps some other engine, and invokes it only at 
 * pre-defined intervals. This can be used to prevent relatively 
 * long-running engines from executing every cycle. 
 * 
 * @author woeltjen
 */
public class PeriodicEngine implements Engine {
	private float next = -1;
	private float period;
	private Engine engine;

	public PeriodicEngine(float period, Engine engine) {
		super();
		this.period = period;
		this.engine = engine;
	}


	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		if (next < 0) next = timeStamp;
		while (next <= timeStamp) {
			engine.runCycle(entities, next);
			next += period;			
		}
	}
}
