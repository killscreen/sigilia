package net.rowf.sigilia;

import java.util.HashMap;
import java.util.Map;

import net.rowf.sigilia.scenario.ArcherScenario;
import net.rowf.sigilia.scenario.GoblinScenario;
import net.rowf.sigilia.scenario.WizardScenario;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;



public class ScenarioSelectActivity extends FullscreenActivity {	
	private int[] scenarios = { R.id.scenario_1, R.id.scenario_2, R.id.scenario_3 };
	private Map<Integer, String> scenarioMap = new HashMap<Integer, String>();
	private int activeScenario = 0;	
	
	private static final String HIGHEST_SCENARIO_KEY = "HIGHEST_SCENARIO"; 
	
	private int highestScenarioAvailable = 0;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			highestScenarioAvailable = savedInstanceState.getInt(HIGHEST_SCENARIO_KEY);
		}
		
		scenarioMap.put(R.id.scenario_1, GoblinScenario.class.getName());
		scenarioMap.put(R.id.scenario_2, ArcherScenario.class.getName());
		scenarioMap.put(R.id.scenario_3, WizardScenario.class.getName());
		
		setContentView(R.layout.select);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// May include scenario success, etc.
		for (int i = 0; i < scenarios.length; i++) {
			View v = findViewById(scenarios[i]);
			// TODO: Better visibility?
			v.setVisibility(isAvailableToPlayer(scenarios[i]) ? View.VISIBLE : View.INVISIBLE);
		}
	}

	private boolean isAvailableToPlayer(int scenarioId) {
		for (int i = 0; i < scenarios.length; i++) {
			if (i <= highestScenarioAvailable && 
			    scenarios[i] == scenarioId) {
				return true;
			}
		}
		return false;
	}

	private void recordVictory(int scenarioId) {
		for (int i = 0; i < scenarios.length; i++) {
			if (scenarios[i] == activeScenario) {
				highestScenarioAvailable = i + 1;
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(HIGHEST_SCENARIO_KEY, highestScenarioAvailable);
	}

	public void chooseScenario(View trigger) {
		if (isAvailableToPlayer(trigger.getId())) {
			String scenario = scenarioMap.get(trigger.getId());
			if (scenario != null) {
				activeScenario = trigger.getId();
				Intent runScenario = new Intent(this, ScenarioActivity.class);			
				runScenario.putExtra(ScenarioActivity.SCENARIO_KEY, scenario);
				startActivityForResult(runScenario, ScenarioActivity.SCENARIO_REQUEST);
			} 
//			else {
//				Intent runScenario = new Intent(this, ECSDemoActivity.class);//ScenarioActivity.class);
//				startActivity(runScenario);
//			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case ScenarioActivity.SCENARIO_SUCCESS:
			Log.i(getClass().getSimpleName(), "Scenario success");
			recordVictory(activeScenario);
			break;
		case ScenarioActivity.SCENARIO_FAILURE:
			Intent hint = new Intent(this, HintActivity.class);
			hint.putExtra(HintActivity.HINT_KEY, activeScenario);
			startActivity(hint);
			Log.i(getClass().getSimpleName(), "Scenario failed");
			break;		
		}
	}
	
	

}
