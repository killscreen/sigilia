package net.rowf.sigilia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class TitleActivity extends FullscreenActivity {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title);
		new Handler().postDelayed(nextScreen, 2500); // Switch after 2.5sec
	}
	
	private Runnable nextScreen = new Runnable() {

		@Override
		public void run() {
			// TODO Animate transition
			startActivity(new Intent(TitleActivity.this, ScenarioSelectActivity.class));
		}
		
	};
}
