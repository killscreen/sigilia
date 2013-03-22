package net.rowf.sigilia.scenario;

import java.util.List;
import java.util.Map;

import net.rowf.sigilia.R;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.enemy.Goblin;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.model.Billboard;
import net.rowf.sigilia.renderer.shader.program.FlatTextureShader;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class SampleScenario extends BaseScenario {

	@Override
	public void populate(List<Entity> entities) {
		Prototype enemy = new Goblin();
		for (float x = -3; x < 3; x += 1f) {
			entities.add(spawn(enemy, x, 0.5f, 3f));
		}
	}

	@Override
	public void decorate(Map<String, Decorator<Representation>> decorum, Resources res) {
		decorum.put(Goblin.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
        				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.monster)), 
        				Billboard.UNIT));		
		//super.decorate
	}

}
