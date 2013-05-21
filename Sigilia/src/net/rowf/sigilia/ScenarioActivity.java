package net.rowf.sigilia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.AnimationEngine;
import net.rowf.sigilia.game.engine.CollisionEngine;
import net.rowf.sigilia.game.engine.CompletionEngine;
import net.rowf.sigilia.game.engine.CompletionEngine.CompletionCallback;
import net.rowf.sigilia.game.engine.CompletionEngine.DoesNotContain;
import net.rowf.sigilia.game.engine.DecorationEngine;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.engine.EventEngine;
import net.rowf.sigilia.game.engine.InputEngine;
import net.rowf.sigilia.game.engine.InputEngine.InputElement;
import net.rowf.sigilia.game.engine.IntelligenceEngine;
import net.rowf.sigilia.game.engine.MotionEngine;
import net.rowf.sigilia.game.engine.RemovalEngine;
import net.rowf.sigilia.game.engine.RenderingEngine;
import net.rowf.sigilia.game.engine.RenderingEngine.RenderableReceiver;
import net.rowf.sigilia.game.engine.SequenceEngine;
import net.rowf.sigilia.game.engine.SpawnEngine;
import net.rowf.sigilia.game.entity.Player;
import net.rowf.sigilia.game.entity.enemy.VictorySentinel;
import net.rowf.sigilia.game.entity.weapon.BeeWeapon;
import net.rowf.sigilia.game.entity.weapon.DefaultWeapon;
import net.rowf.sigilia.game.entity.weapon.FireWeapon;
import net.rowf.sigilia.game.entity.weapon.IceWeapon;
import net.rowf.sigilia.game.entity.weapon.LightningWeapon;
import net.rowf.sigilia.game.entity.weapon.Weapon;
import net.rowf.sigilia.input.TouchInputListener;
import net.rowf.sigilia.input.WeaponInput;
import net.rowf.sigilia.renderer.PerspectiveRenderer;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Camera;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.PerspectiveRenderer.RenderableProvider;
import net.rowf.sigilia.scenario.Scenario;
import android.opengl.GLSurfaceView;
import android.util.Log;

/**
 * Runs a game scenario to completion. This is the activity used for 
 * main gameplay sequences. 
 * 
 * @author woeltjen
 *
 */
public class ScenarioActivity extends FullscreenActivity implements CompletionCallback<Boolean> {
	public static final String SCENARIO_KEY = ScenarioActivity.class.getPackage().getName() + ".scenario_class";

	public static final int SCENARIO_SUCCESS = 0;
	public static final int SCENARIO_FAILURE = 1;
	
	public static final int SCENARIO_REQUEST = 0;
	
	private ScenarioRunner activeScenario = null;
	
	private static final Vector WORLD_MIN = new Vector (-20, -5, -1);
	private static final Vector WORLD_MAX = new Vector (20,  20, 20);
	
	@Override
	public void onResume() {
		super.onResume();

		if (activeScenario != null) {
			activeScenario.stop();
		}
		
		String scenarioClass = getIntent().getExtras().getString(SCENARIO_KEY);
		if (scenarioClass != null) {
			try {
				runScenario((Scenario) (Class.forName(scenarioClass).newInstance()));
			} catch (Exception e) {
				Log.e(ScenarioActivity.class.getName(), "Failed to load scenario " + scenarioClass);
				if (e instanceof RuntimeException) {
					throw (RuntimeException) e;
				}
			}
		} else {
			// Log?
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (activeScenario != null) {
			activeScenario.stop();
		}
	}
	
	private void runScenario(Scenario s) {
		RenderingIntermediary intermediary = new RenderingIntermediary();
        PerspectiveRenderer r = new PerspectiveRenderer(intermediary, null);
        TouchInputListener touchInput = new TouchInputListener(r);
        
        // TODO: Some class needs to provide this
        Map<String, Decorator<Representation>> decorum = new HashMap<String, Decorator<Representation>>();
        s.decorate(decorum, getResources());
               
		List<Engine> engines = new ArrayList<Engine>();
        
		engines.add(new RenderingEngine(intermediary));
		engines.add(new MotionEngine());
		engines.add(new RemovalEngine().addCriterion(RemovalEngine.outOfBounds(WORLD_MIN, WORLD_MAX)).addCriterion(RemovalEngine.LIVENESS));
		engines.add(new DecorationEngine<Representation>(Representation.class, decorum));
		engines.add(new InputEngine(Arrays.<InputElement>asList(new WeaponInput(new DefaultWeapon(), Arrays.<Weapon>asList(new LightningWeapon(), new FireWeapon(), new IceWeapon(), new BeeWeapon()), touchInput, 0.05f))));
		engines.add(new AnimationEngine());
		engines.add(new CollisionEngine());
		engines.add(new IntelligenceEngine());
		engines.add(new SpawnEngine());
		engines.add(new CompletionEngine(this, new DoesNotContain(Player.class, false), new DoesNotContain(VictorySentinel.class, true)));
		engines.add(new EventEngine(new EventManager(this)));
		
		activeScenario = new ScenarioRunner(s, new SequenceEngine(engines));
		new Thread(activeScenario).start();
		
        GLSurfaceView view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(r);
        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        view.setOnTouchListener(touchInput);

        setContentView(view);
	}

	@Override
	public void onScenarioComplete(Boolean success) {
		if (activeScenario != null) {
			activeScenario.stop();
		}
		setResult(success ? SCENARIO_SUCCESS : SCENARIO_FAILURE);
		finish();
	}
	
	private static class RenderingIntermediary implements RenderableProvider, RenderableReceiver {
		private List<Renderable> render = new ArrayList<Renderable>();
		
		@Override
		public void updateRender(List<Renderable> renderables) {
			this.render = renderables;
		}

		@Override
		public Iterable<Renderable> getOrderedRenderables(Camera camera) {
			return render;
		}
	}



}

