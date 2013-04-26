package net.rowf.sigilia.scenario;

import java.util.List;
import java.util.Map;

import net.rowf.sigilia.R;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.CompositeRepresentation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.enemy.Fireball;
import net.rowf.sigilia.game.entity.enemy.IceShield;
import net.rowf.sigilia.game.entity.enemy.Wizard;
import net.rowf.sigilia.game.entity.environment.Column;
import net.rowf.sigilia.renderer.decorator.AnimatedRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.decorator.PeriodicRepresentation;
import net.rowf.sigilia.renderer.model.Billboard;
import net.rowf.sigilia.renderer.shader.program.ColorizedFlatTextureShader;
import net.rowf.sigilia.renderer.shader.program.ScrollingShader;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class WizardScenario extends BaseScenario {

	@Override
	public void populate(List<Entity> entities) {
		super.populate(entities);
		
		Prototype enemy = new Wizard();
		entities.add(spawn(enemy, 0, 8f));
		
		Prototype column = new Column();
		float columnX[] = { -4, -2, 2, 4};
		float columnZ[] = { 7,  11, 11, 7};
		for (int i = 0; i < 4; i++) {
			entities.add(spawn(column, columnX[i], 0, columnZ[i]));
		}
		
		for (Entity entity : entities) {
			if (entity.getComponent(Name.class) == BACKDROP_NAME) {
				entity.setComponent(Animation.class, new PeriodicAnimation(2f, false));
			}			
		}
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
		
		decorum.put(Fireball.class.getSimpleName(), new DeferredRepresentation( 
						ColorizedFlatTextureShader.deferredForm(1, 0.75, 0),
        				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.generic_particle)), 
        				Billboard.UNIT));

		decorum.put(IceShield.class.getSimpleName(), new DeferredRepresentation( 
				ColorizedFlatTextureShader.deferredForm(0, 1, 0.75),
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.generic_particle)), 
				new Billboard(3)));
		
		decorum.put(Column.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.cloud_column)), 
				new Billboard(4)));
		
		decorum.put(BACKDROP_NAME.get(), new CompositeRepresentation(
				new DeferredRepresentation(DEFERRED_FLAT_SHADER, 
						new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.cloud_background)),
						new Billboard(32)), 
				new PeriodicRepresentation(ScrollingShader.deferredForm(), 
						new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.cloud_cover)),
						new Billboard(32)))
				);
		//super.decorate
	}

}
