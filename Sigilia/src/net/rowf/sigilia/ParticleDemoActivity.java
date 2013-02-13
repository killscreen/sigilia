package net.rowf.sigilia;

import android.app.Activity;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;


public class ParticleDemoActivity extends Activity {
	protected static Resources res;
    
    public ParticleDemoActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final ParticleRenderer pr;
        GLSurfaceView view = new GLSurfaceView(this);
        res = view.getResources();
        view.setEGLContextClientVersion(2);
        view.setRenderer(pr = new ParticleRenderer());
        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				for (int i = 0; i < event.getPointerCount(); i++) {
					pr.addParticle(event.getX(i), event.getY(i));
				}
				return true;
			}
        	
        });
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);
    }
}
