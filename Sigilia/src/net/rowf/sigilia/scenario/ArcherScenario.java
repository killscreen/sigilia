package net.rowf.sigilia.scenario;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.rowf.sigilia.R;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.component.visual.GenericRepresentation;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.enemy.Archer;
import net.rowf.sigilia.game.entity.enemy.Arrow;
import net.rowf.sigilia.game.entity.environment.Tree;
import net.rowf.sigilia.renderer.GenericRenderable.DeferredElement;
import net.rowf.sigilia.renderer.GenericRenderable.RenderingElement;
import net.rowf.sigilia.renderer.GenericRenderable.StaticElement;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.model.Backdrop;
import net.rowf.sigilia.renderer.model.Billboard;
import net.rowf.sigilia.renderer.model.Crossboard;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;
import net.rowf.sigilia.renderer.shader.program.BurnoutShader;
import net.rowf.sigilia.renderer.texture.Texture;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.FloatMath;

/**
 * A scenario in which a number of archers hide behind trees, 
 * firing arrows at the player.
 * 
 * @author woeltjen
 *
 */
public class ArcherScenario extends BaseScenario {

	@Override
	public void populate(List<Entity> entities) {
		super.populate(entities);
		
		Random random = new Random();
		
		Prototype enemy = new Archer();
		Prototype tree  = new Tree();
		for (int i = 0; i < 8; i++) {
			float r = random.nextFloat() * (float) Math.PI / 6f;
			if (i % 2 == 0) {
				r *= -1f;
			}
			float x = FloatMath.sin(r);
			float z = FloatMath.cos(r);
			float d = 4f + ((float) i) * 1.25f;
			entities.add(spawn(enemy, x * d, z * d));
			entities.add(spawn(tree,  x * (d-1f),  z * (d-1f)));			
		}
		
	}

	@Override
	public void decorate(Map<String, Decorator<Representation>> decorum, Resources res) {
		super.decorate(decorum, res);
		decorateForEnemy(decorum, Archer.class, 
				BitmapFactory.decodeResource(res, R.drawable.archer), 
				loadKeyframeSequence(res, R.raw.archer_animation, 2f, true));
		
		decorum.put(Arrow.class.getSimpleName(), new DeferredRepresentation( DEFERRED_FLAT_SHADER,
        				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.arrow_particle)), 
        				Crossboard.UNIT));

		DeferredTexture treeTexture = 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.tree));
		decorum.put(Tree.class.getSimpleName(), new DeferredRepresentation( 
				DEFERRED_FLAT_SHADER, treeTexture, new Billboard(6.5f)));

		decorum.put(Tree.TREE_DEATH_NAME.get(), 
				new GenericRepresentation( BurnoutShader.deferredForm(),
						new Billboard(6.5f),
						Arrays.<RenderingElement>asList(
								new StaticElement<Vector> (
									VectorParameter.COLOR,
		        					new Vector(1f,0.66f,0.125f)
								),
								new DeferredElement<Texture> (
										SamplerParameter.TEXTURE,
				        				treeTexture
								)
						), 
				GenericRepresentation.TRANSITION_ELEMENT));

		
		decorum.put(BACKDROP_NAME.get(), new DeferredRepresentation(DEFERRED_FLAT_SHADER, 
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.forest_background)),
				new Backdrop()));
		//super.decorate
	}

}
