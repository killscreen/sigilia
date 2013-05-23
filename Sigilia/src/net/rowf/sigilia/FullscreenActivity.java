package net.rowf.sigilia;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Utility superclass for various game activities, so that they don't need to 
 * individually manage common things (like refusing window title or constraining 
 * orientation) 
 * 
 * @author woeltjen
 *
 */
public abstract class FullscreenActivity extends Activity {
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
