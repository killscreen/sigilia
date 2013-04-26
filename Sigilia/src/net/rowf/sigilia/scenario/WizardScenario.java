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
import net.rowf.sigilia.game.entity.enemy.Wizard;
import net.rowf.sigilia.game.entity.environment.Tree;
import net.rowf.sigilia.renderer.decorator.AnimatedRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.model.Backdrop;
import net.rowf.sigilia.renderer.model.Billboard;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class WizardScenario extends BaseScenario {

	@Override
	public void populate(List<Entity> entities) {
		super.populate(entities);
		
		Prototype enemy = new Wizard();
		entities.add(spawn(enemy, 0, 8f));
		

		
	}

	@Override
	public void decorate(Map<String, Decorator<Representation>> decorum, Resources res) {
		super.decorate(decorum, res);
		decorum.put(Wizard.class.getSimpleName(), new AnimatedRepresentation(DEFERRED_ANIM_SHADER, 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.wizard_patch)),
				loadKeyframeSequence(res, R.raw.wizard_animation, 2f, true)));	

//		decorum.put(Goblin.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
//				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.archer)), 
//				new Billboard(2)));
		
		decorum.put(Rock.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
        				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.rock_particle)), 
        				Billboard.UNIT));
	
		decorum.put(BACKDROP_NAME.get(), new DeferredRepresentation(DEFERRED_FLAT_SHADER, 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.cloud_background)),
				new Billboard(32)));
		//super.decorate
	}

}
