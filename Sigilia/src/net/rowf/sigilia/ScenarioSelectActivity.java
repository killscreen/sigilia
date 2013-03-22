package net.rowf.sigilia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.scenario.SampleScenario;
import net.rowf.sigilia.scenario.Scenario;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;



public class ScenarioSelectActivity extends FullscreenActivity {
	
	private Map<Integer, String> scenarioMap = new HashMap<Integer, String>();
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: Need to set images to grey if things are unavailable
		
		scenarioMap.put(R.id.scenario_1, SampleScenario.class.getName());
		
		setContentView(R.layout.select);
	}
	
	public void chooseScenario(View trigger) {
		String scenario = scenarioMap.get(trigger.getId());
		if (scenario != null) {
			Intent runScenario = new Intent(this, ScenarioActivity.class);			
			runScenario.putExtra(ScenarioActivity.SCENARIO_KEY, scenario);
			startActivity(runScenario);
		} else {
			Intent runScenario = new Intent(this, ECSDemoActivity.class);//ScenarioActivity.class);
			startActivity(runScenario);
		}
	}

}
