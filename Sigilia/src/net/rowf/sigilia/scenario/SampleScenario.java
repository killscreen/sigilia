package net.rowf.sigilia.scenario;

import java.util.List;
import java.util.Map;

import net.rowf.sigilia.R;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.game.entity.enemy.Goblin;
import net.rowf.sigilia.renderer.decorator.AnimatedRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.model.Backdrop;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class SampleScenario extends BaseScenario {

	@Override
	public void populate(List<Entity> entities) {
		Prototype enemy = new Goblin();
		for (float x = -3; x < 3; x += 1f) {
			entities.add(spawn(enemy, x, -0.5f, 6f + 3 * (float) Math.sin(x)));
		}
		
		Entity backdrop = new StandardEntity();
		backdrop.setComponent(Name.class, new Name("BACKDROP"));
		backdrop.setComponent(Position.class, new Position(0,0,12));
		entities.add(backdrop);
	}

	@Override
	public void decorate(Map<String, Decorator<Representation>> decorum, Resources res) {	
		decorum.put(Goblin.class.getSimpleName(), new AnimatedRepresentation(DEFERRED_ANIM_SHADER, 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.goblin_patch)),
				loadKeyframeSequence(res, R.raw.goblin_animation, 2f)));	
		
		decorum.put("BACKDROP", new DeferredRepresentation(DEFERRED_FLAT_SHADER, 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.cave_background)),
				new Backdrop()));
		//super.decorate
	}

}
