package net.rowf.sigilia.scenario;

import java.util.List;
import java.util.Map;

import net.rowf.sigilia.R;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.enemy.Archer;
import net.rowf.sigilia.game.entity.enemy.Rock;
import net.rowf.sigilia.game.entity.environment.Tree;
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
		Prototype tree  = new Tree();
		for (float x = -3; x < 3.99f; x += 1f) {
			entities.add(spawn(enemy, x, 9f + 3 * (float) Math.sin(x)));
			entities.add(spawn(tree,  x,  8f + 3 * (float) Math.sin(x)));
		}
		
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

		decorum.put(Tree.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.tree)), 
				new Billboard(6.5f)));
		
		decorum.put(BACKDROP_NAME.get(), new DeferredRepresentation(DEFERRED_FLAT_SHADER, 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.forest_background)),
				new Backdrop()));
		//super.decorate
	}

}
