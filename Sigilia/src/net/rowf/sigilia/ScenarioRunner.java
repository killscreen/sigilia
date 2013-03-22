package net.rowf.sigilia;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.renderer.PerspectiveRenderer.RenderableInitializer;
import net.rowf.sigilia.scenario.Scenario;
import android.os.SystemClock;
import android.util.Log;

public class ScenarioRunner implements Runnable {
	private Scenario scenario;
	private Engine engine;
	private boolean running;
	
	private List<Entity> entities = new ArrayList<Entity>();
	
	public ScenarioRunner(Scenario scenario, Engine engine) {
		super();
		this.scenario = scenario;
		this.engine = engine;
	}

	@Override
	public void run() {
		entities.clear();
		scenario.populate(entities);
		running = true;
		while (running) {
			engine.runCycle(entities, ((float)SystemClock.uptimeMillis()) / 1000f);
		}
	}
	
	/**
	 * Stop running the current scenario
	 */
	public void stop() {
		// Note - this may be called from another thread
		running = false;
	}

}
