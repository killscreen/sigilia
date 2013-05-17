package net.rowf.sigilia.scenario;

import java.util.List;
import java.util.Map;

import net.rowf.sigilia.R;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.enemy.Goblin;
import net.rowf.sigilia.game.entity.enemy.Rock;
import net.rowf.sigilia.game.entity.environment.Stone;
import net.rowf.sigilia.renderer.decorator.AnimatedRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.model.Backdrop;
import net.rowf.sigilia.renderer.model.Billboard;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class SampleScenario extends BaseScenario {

	@Override
	public void populate(List<Entity> entities) {
		super.populate(entities);
		
		Prototype enemy = new Goblin();
		Prototype stone = new Stone();
		for (float x = -5; x < 5.5; x += 1f) {
			entities.add(spawn(enemy, x, 6f + 3 * (float) Math.sin(x)));
			entities.add(spawn(stone, x, 5f + 3 * (float) Math.sin(x)));
			entities.add(spawn(stone, 5f + 3 * (float) Math.sin(x), x));
		}
	}

	@Override
	public void decorate(Map<String, Decorator<Representation>> decorum, Resources res) {
		super.decorate(decorum, res);
//		decorum.put(Goblin.class.getSimpleName(), new AnimatedRepresentation(DEFERRED_ANIM_SHADER, 
//				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.goblin_patch)),
//				loadKeyframeSequence(res, R.raw.goblin_animation, 2f, false)));	
		
		decorateForEnemy(decorum, Goblin.class, 
				BitmapFactory.decodeResource(res, R.drawable.goblin_patch), 
				loadKeyframeSequence(res, R.raw.goblin_animation, 2f, false));

		decorum.put(Rock.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
        				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.rock_particle)), 
        				Billboard.UNIT));

		decorum.put(Stone.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.cave_rock)), 
				new Billboard(0.5f)));
		
		decorum.put(BACKDROP_NAME.get(), new DeferredRepresentation(DEFERRED_FLAT_SHADER, 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.cave_background)),
				new Backdrop()));
		//super.decorate
	}

}
