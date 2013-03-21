package net.rowf.sigilia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class ScenarioSelectActivity extends FullscreenActivity {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);	
	}
	
	public void chooseScenario(View trigger) {
		startActivity(new Intent(this, ECSDemoActivity.class));
	}

}
