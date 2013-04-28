package net.rowf.sigilia.game.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import android.os.SystemClock;
import android.util.Log;

public class DebugSequenceEngine implements Engine {
	private Map<Engine, Long> engineTimes = new HashMap<Engine, Long>();
	private List<Engine> engines = new ArrayList<Engine>();
	private long iterations = 0;
	private float lastLog = 0;
	private long sum = 0;

	public DebugSequenceEngine() {		
	}
	
	public DebugSequenceEngine(List<Engine> engines) {
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
			if (!engineTimes.containsKey(engine)) {
				engineTimes.put(engine, 0L);
			}
			long start = SystemClock.currentThreadTimeMillis();
			engine.runCycle(entities, timeStamp);
			long duration = SystemClock.currentThreadTimeMillis() - start;
			engineTimes.put(engine, engineTimes.get(engine) + duration);
			sum += duration;
		}
		iterations++;
		if (timeStamp > lastLog + 1f) {
			logTimes();
			lastLog = timeStamp;
		}
	}
	
	private void logTimes() {
		for (Entry<Engine, Long> durations : engineTimes.entrySet()) {
			String name = durations.getKey().getClass().getSimpleName();
			long   time = durations.getValue();
			float  relativeTime = (((float) time) / ((float) sum)) * 100f;
			Log.d(getClass().getName(), "Engine: " + name + " running at " + relativeTime);
		}
		Log.d(getClass().getName(), "AVERAGE CYCLE: " + ((float)sum) / ((float)iterations));
	}
}
