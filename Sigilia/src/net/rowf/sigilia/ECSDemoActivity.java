package net.rowf.sigilia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rowf.sigilia.background.Background;
import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.engine.InputEngine;
import net.rowf.sigilia.game.engine.InputEngine.InputElement;
import net.rowf.sigilia.game.engine.MotionEngine;
import net.rowf.sigilia.game.engine.PeriodicEngine;
import net.rowf.sigilia.game.engine.RenderingEngine;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.game.entity.weapon.DefaultWeapon;
import net.rowf.sigilia.input.TouchInputListener;
import net.rowf.sigilia.input.WeaponInput;
import net.rowf.sigilia.renderer.PerspectiveRenderer;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.PerspectiveRenderer.RenderableInitializer;
import net.rowf.sigilia.renderer.StandardRenderable;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.model.Backdrop;
import net.rowf.sigilia.renderer.model.Billboard;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.shader.Program;
import net.rowf.sigilia.renderer.shader.program.FlatTextureShader;
import net.rowf.sigilia.renderer.texture.Texture;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.FloatMath;
import android.view.Window;
import android.view.WindowManager;

public class ECSDemoActivity extends FullscreenActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final RenderingEngine renderingEngine = new RenderingEngine();
        final AssetInitializer initializer = new AssetInitializer();
        final MotionEngine motion = new MotionEngine();
        final Engine pruner = new PruneEngine();
        
        final Decorator<Representation> particleRepresentation = 
        		new DeferredRepresentation( FlatTextureShader.DEFERRED_FORM,
        				new DeferredTexture(BitmapFactory.decodeResource(getResources(), R.drawable.generic_particle)), 
        				Billboard.UNIT);
        
        
        Map<String, Decorator<Representation>> decorum = new HashMap<String, Decorator<Representation>>();
        decorum.put(DefaultWeapon.class.getSimpleName(), particleRepresentation);

        final Engine decorator = new PeriodicEngine(0.025f, 
        		new DecorationEngine<Representation>(Representation.class, decorum));
        
        Prototype weapon = new DefaultWeapon();
        
        PerspectiveRenderer r = new PerspectiveRenderer(renderingEngine, initializer);
        TouchInputListener touchInput = new TouchInputListener(r);

        final Engine input = new PeriodicEngine(0.001f, 
        		new InputEngine(Arrays.<InputElement>asList(new WeaponInput(weapon, touchInput, 0.05f))));

        
        GLSurfaceView view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(r);
        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        view.setOnTouchListener(touchInput);
        
        final List<Entity> entities = new ArrayList<Entity>();
        
        
        new Thread() {
        	@Override
        	public void run() {
        		while (true) {
	        		float timeStamp = ((float) SystemClock.uptimeMillis()) / 1000f;
	        		initializer.runCycle(entities, timeStamp);
	        		renderingEngine.runCycle(entities, timeStamp);
	        		motion.runCycle(entities, timeStamp);
	        		decorator.runCycle(entities, timeStamp);
	        		input.runCycle(entities, timeStamp);
	        		pruner.runCycle(entities, timeStamp);
        		}
        	}
        }.start();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);
    }
    
    private class PruneEngine implements Engine {
    	private List<Entity> toRemove = new ArrayList<Entity>();
    	
		@Override
		public void runCycle(List<Entity> entities, float timeStamp) {
			toRemove.clear();
			for (Entity e : entities) {
				Position p = e.getComponent(Position.class);
				if (p != null && p.getZ() > 12f) {
					toRemove.add(e);
				}
			}
			entities.removeAll(toRemove);
		}
    	
    }
    
    private class AssetInitializer implements Engine, RenderableInitializer {
    	private boolean initialized = false; 
    	private List<Entity> toIntroduce = new ArrayList<Entity>();
    	private FlatTextureShader shader;
    	private Texture           magic;
    	
    	public synchronized void addParticle(float x, float y, float z) {
    		
    	}

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

    			
    			shader  = new FlatTextureShader();
    			
    			final Texture           texture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.monster));
    			final Model             model   = new Billboard(2);
    			for (int i = 0; i < 12; i++) {
    				final float x = -5f + 10f * ((float) i)/12f;
    				float y = -1f;// + 2f * ((float) i)/30f;
    				float z = 5 + (float)Math.cos(i);
//    				final float[] mat = new float[16];
//    				Matrix.setIdentityM(mat, 0);
//    				Matrix.translateM(mat, 0, x, y, z);
    				Representation rep = new Representation() {
						
						@Override
						public Renderable makeRenderable(Entity e) {
							float[] mat = new float[16];
							Matrix.setIdentityM(mat, 0);
							Position p = e.getComponent(Position.class);
							if (p != null) {
								Matrix.translateM(mat, 0, p.getX(), p.getY() + .5f, p.getZ());
							}
//							Matrix.translateM(mat, 0, x, 0f, (float) Math.sin((float) SystemClock.uptimeMillis() / 1000f)*4 + 5f);
							return new StandardRenderable(shader, model, mat, texture);
						}    					
    				};
    				Motion motion = new Motion() {
    					private float t = 0;
						@Override
						public void move(Entity e, float timeStep) {
							t += timeStep;
							Position p = e.getComponent(Position.class);
							p.shift(0.0f, 0.0f,
									timeStep * FloatMath.sin(t));
						}    					
    				};
    				Entity ent = new StandardEntity();
    				ent.setComponent(Representation.class, rep);
    				ent.setComponent(Position.class, new Position(x,y,z));
    				ent.setComponent(Motion.class, motion);
    				toIntroduce.add(ent);
    			}    
    			
    			Entity env = new StandardEntity();
    			env.setComponent(Representation.class, new Representation() {
    				private Renderable r = new StandardRenderable(
    						new FlatTextureShader(),
    						new Backdrop(),
    						mat(),
    						new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.cave_background))
    						);
					@Override
					public Renderable makeRenderable(Entity e) {					
						return r;
					}    			
					
					private float[] mat() {
						float mat[] = new float[16];
						Matrix.setIdentityM(mat, 0);						
						Matrix.translateM(mat, 0, 0,-1,8);
						Matrix.scaleM(mat, 0, 5,5,8);
						return mat;
					}
    			});
    			env.setComponent(Position.class, new Position(0,0,8f));
    			toIntroduce.add(env);
    			initialized = true;
    		}
    	}
    }
}
