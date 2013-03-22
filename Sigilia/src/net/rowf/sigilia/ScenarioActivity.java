package net.rowf.sigilia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.engine.InputEngine;
import net.rowf.sigilia.game.engine.InputEngine.InputElement;
import net.rowf.sigilia.game.engine.MotionEngine;
import net.rowf.sigilia.game.engine.RemovalEngine;
import net.rowf.sigilia.game.engine.RenderingEngine;
import net.rowf.sigilia.game.engine.RenderingEngine.RenderableReceiver;
import net.rowf.sigilia.game.engine.SequenceEngine;
import net.rowf.sigilia.game.entity.weapon.DefaultWeapon;
import net.rowf.sigilia.input.TouchInputListener;
import net.rowf.sigilia.input.WeaponInput;
import net.rowf.sigilia.renderer.PerspectiveRenderer;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Camera;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.PerspectiveRenderer.RenderableProvider;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.model.Billboard;
import net.rowf.sigilia.renderer.shader.program.FlatTextureShader;
import net.rowf.sigilia.scenario.Scenario;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

public class ScenarioActivity extends FullscreenActivity {
	public static final String SCENARIO_KEY = ScenarioActivity.class.getPackage().getName() + ".scenario_class";
	
	@Override
	public void onResume() {
		super.onResume();
		int z=2;
		String scenarioClass = getIntent().getExtras().getString(SCENARIO_KEY);
		if (scenarioClass != null) {
			try {
				runScenario((Scenario) (Class.forName(scenarioClass).newInstance()));
			} catch (Exception e) {
				Log.e(ScenarioActivity.class.getName(), "Failed to load scenario " + scenarioClass);
			}
		} else {
			// Log?
		}
	}
	
	private void runScenario(Scenario s) {
		RenderingIntermediary intermediary = new RenderingIntermediary();
        PerspectiveRenderer r = new PerspectiveRenderer(intermediary, null);
        TouchInputListener touchInput = new TouchInputListener(r);
        
        // TODO: Some class needs to provide this
        Map<String, Decorator<Representation>> decorum = new HashMap<String, Decorator<Representation>>();
        s.decorate(decorum, getResources());
        
        Decorator<Representation> particleRepresentation = 
        		new DeferredRepresentation( FlatTextureShader.deferredForm(),
        				new DeferredTexture(BitmapFactory.decodeResource(getResources(), R.drawable.generic_particle)), 
        				Billboard.UNIT);
        decorum.put(DefaultWeapon.class.getSimpleName(), particleRepresentation);
        
		List<Engine> engines = new ArrayList<Engine>();
       
		engines.add(new RenderingEngine(intermediary));
		engines.add(new MotionEngine());
		engines.add(new RemovalEngine().addCriterion(RemovalEngine.fartherThan(12f)));
		engines.add(new DecorationEngine<Representation>(Representation.class, decorum));
		engines.add(new InputEngine(Arrays.<InputElement>asList(new WeaponInput(new DefaultWeapon(), touchInput, 0.05f))));

		new Thread(new ScenarioRunner(s, new SequenceEngine(engines))).start();
		
        GLSurfaceView view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(r);
        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        view.setOnTouchListener(touchInput);

        setContentView(view);
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
