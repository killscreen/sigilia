package net.rowf.sigilia;

import java.util.ArrayList;
import java.util.List;

import net.rowf.sigilia.background.Background;
import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.RenderingEngine;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.renderer.PerspectiveRenderer;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.PerspectiveRenderer.RenderableInitializer;
import net.rowf.sigilia.renderer.StandardRenderable;
import net.rowf.sigilia.renderer.model.Billboard;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.model.Texture;
import net.rowf.sigilia.renderer.shader.Program;
import net.rowf.sigilia.renderer.shader.program.FlatTextureShader;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class ECSDemoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final RenderingEngine renderingEngine = new RenderingEngine();
        final AssetInitializer initializer = new AssetInitializer();
        
        Renderer r = new PerspectiveRenderer(renderingEngine, initializer);
        
        GLSurfaceView view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(r);
        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        
        final List<Entity> entities = new ArrayList<Entity>();
        
        new Thread() {
        	@Override
        	public void run() {
        		while (true) {
	        		float timeStamp = SystemClock.elapsedRealtime() / 1000f;
	        		initializer.runCycle(entities, timeStamp);
	        		renderingEngine.runCycle(entities, timeStamp);
        		}
        	}
        }.start();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);
    }
    
    private class AssetInitializer implements Engine, RenderableInitializer {
    	private boolean initialized = false; 
    	private List<Entity> toIntroduce = new ArrayList<Entity>();

    	@Override
		public void runCycle(List<Entity> entities, float timeStamp) {
			if (initialized) {
				entities.addAll(toIntroduce);
				toIntroduce.clear();
			}
		}
    	
    	@Override
    	public void initialize() {
    		if (!initialized) {
    			final Background bg = new Background(new Program(
    					"varying lowp vec2 vCoord;" +
    						    "uniform mat4 uMatrix;" +
    						    "attribute vec2 vTexCoord;" +
    						    "attribute vec4 vPosition;" +
    						    "void main() {" +
    						    "  vCoord = vec2(abs(vTexCoord.x), abs(vTexCoord.y));" +
    						    "  gl_Position = uMatrix * vPosition;" +
    						    "}",
    						    
    						    "uniform sampler2D uTexture;" +
    						    "varying lowp vec2 vCoord;" +
    						    "precision mediump float;" +
    						    "void main() {" +
    						    "  gl_FragColor = texture2D(uTexture, vCoord);" +
    						    "}"
    					), BitmapFactory.decodeResource(getResources(), R.drawable.cave_background));

    			
    			final FlatTextureShader shader  = new FlatTextureShader();
    			final Texture           texture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.cave_background));
    			final Model             model   = Billboard.UNIT;
    			for (int i = 0; i < 30; i++) {
    				float x = -5f + 10f * ((float) i)/30f;
    				float y = -1f + 2f * ((float) i)/30f;
    				float z = 5;
    				final float[] mat = new float[16];
    				Matrix.setIdentityM(mat, 0);
    				Matrix.translateM(mat, 0, x, y, z);
    				Representation rep = new Representation() {
						@Override
						public Renderable makeRenderable(Entity e) {
							return new StandardRenderable(shader, model, mat, texture);
						}    					
    				};
    				Entity ent = new StandardEntity();
    				ent.setComponent(Representation.class, rep);
    				toIntroduce.add(ent);
    			}    			
    			initialized = true;
    		}
    	}
    }
}
