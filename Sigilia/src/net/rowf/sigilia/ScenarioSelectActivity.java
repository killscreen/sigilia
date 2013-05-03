package net.rowf.sigilia;

import java.util.HashMap;
import java.util.Map;

import net.rowf.sigilia.scenario.ArcherScenario;
import net.rowf.sigilia.scenario.SampleScenario;
import net.rowf.sigilia.scenario.WizardScenario;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;



public class ScenarioSelectActivity extends FullscreenActivity {
	
	private Map<Integer, String> scenarioMap = new HashMap<Integer, String>();
	private int activeScenario = 0;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: Need to set images to grey if things are unavailable
		
		scenarioMap.put(R.id.scenario_1, SampleScenario.class.getName());
		scenarioMap.put(R.id.scenario_2, ArcherScenario.class.getName());
		scenarioMap.put(R.id.scenario_3, WizardScenario.class.getName());
		
		setContentView(R.layout.select);
	}
	
	public void chooseScenario(View trigger) {
		String scenario = scenarioMap.get(trigger.getId());
		if (scenario != null) {
			activeScenario = trigger.getId();
			Intent runScenario = new Intent(this, ScenarioActivity.class);			
			runScenario.putExtra(ScenarioActivity.SCENARIO_KEY, scenario);
			startActivityForResult(runScenario, ScenarioActivity.SCENARIO_REQUEST);
		} else {
			Intent runScenario = new Intent(this, ECSDemoActivity.class);//ScenarioActivity.class);
			startActivity(runScenario);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case ScenarioActivity.SCENARIO_SUCCESS:
			Log.i(getClass().getSimpleName(), "Scenario success");
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
