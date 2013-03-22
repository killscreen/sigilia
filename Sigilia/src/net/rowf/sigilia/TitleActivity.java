package net.rowf.sigilia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;



public class TitleActivity extends FullscreenActivity {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title);
		//new Handler().postDelayed(nextScreen, 2500); // Switch after 2.5sec
	}
	
	public void startGame(View v) {
		//TODO: Animate transition
		startActivity(new Intent(TitleActivity.this, ScenarioSelectActivity.class));
	}
	
	private Runnable nextScreen = new Runnable() {

		@Override
		public void run() {
			startGame(null);
		}
		
	};
}
