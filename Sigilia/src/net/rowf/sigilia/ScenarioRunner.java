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

public class ScenarioRunner implements Runnable, RenderableInitializer {
	private Scenario scenario;
	private List<Engine> engines;
	
	private Semaphore signal = new Semaphore(0);
	private List<Entity> entities = new ArrayList<Entity>();
	
	public ScenarioRunner(Scenario scenario, List<Engine> engines) {
		super();
		this.scenario = scenario;
		this.engines = engines;
	}

	@Override
	public void initialize() {
		// This needs to happen after GL has started - it 
		scenario.populate(entities);
		notify();
	}

	@Override
	public void run() {
		try {
			// Wait for initialization - this may occur on separate thread
			wait();
			
			while (true) {
				float timeStamp = ((float) SystemClock.uptimeMillis()) / 1000f;
				for (Engine e : engines) {
					e.runCycle(entities, timeStamp);
				}
			}
			
		} catch (InterruptedException e) {
			Log.e(getClass().toString(), e.getMessage());
		}
	}

}
