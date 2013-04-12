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
import net.rowf.sigilia.game.entity.enemy.Archer;
import net.rowf.sigilia.game.entity.enemy.Goblin;
import net.rowf.sigilia.game.entity.enemy.Rock;
import net.rowf.sigilia.renderer.decorator.AnimatedRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.model.Backdrop;
import net.rowf.sigilia.renderer.model.Billboard;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class ArcherScenario extends BaseScenario {

	@Override
	public void populate(List<Entity> entities) {
		super.populate(entities);
		
		Prototype enemy = new Archer();
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
		super.decorate(decorum, res);
		decorum.put(Archer.class.getSimpleName(), new AnimatedRepresentation(DEFERRED_ANIM_SHADER, 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.archer)),
				loadKeyframeSequence(res, R.raw.archer_animation, 2f)));	

//		decorum.put(Goblin.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
//				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.archer)), 
//				new Billboard(2)));
		
		decorum.put(Rock.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
        				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.rock_particle)), 
        				Billboard.UNIT));
		
		decorum.put("BACKDROP", new DeferredRepresentation(DEFERRED_FLAT_SHADER, 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.cave_background)),
				new Backdrop()));
		//super.decorate
	}

}
