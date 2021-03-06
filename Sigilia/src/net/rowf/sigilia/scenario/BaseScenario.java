package net.rowf.sigilia.scenario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.rowf.sigilia.R;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.physical.Boundary;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.component.visual.GenericRepresentation;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.game.entity.Player;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.game.entity.enemy.Archer;
import net.rowf.sigilia.game.entity.enemy.Enemy;
import net.rowf.sigilia.game.entity.enemy.Splat;
import net.rowf.sigilia.game.entity.enemy.VictorySentinel;
import net.rowf.sigilia.game.entity.weapon.BeeWeapon;
import net.rowf.sigilia.game.entity.weapon.DefaultWeapon;
import net.rowf.sigilia.game.entity.weapon.FireWeapon;
import net.rowf.sigilia.game.entity.weapon.IceWeapon;
import net.rowf.sigilia.game.entity.weapon.LightningWeapon;
import net.rowf.sigilia.game.entity.weapon.Weapon;
import net.rowf.sigilia.renderer.GenericRenderable.DeferredElement;
import net.rowf.sigilia.renderer.GenericRenderable.RenderingElement;
import net.rowf.sigilia.renderer.GenericRenderable.StaticElement;
import net.rowf.sigilia.renderer.decorator.AnimatedRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.decorator.DeferredRepresentation;
import net.rowf.sigilia.renderer.decorator.DeferredTexture;
import net.rowf.sigilia.renderer.decorator.PeriodicRepresentation;
import net.rowf.sigilia.renderer.model.Backdrop;
import net.rowf.sigilia.renderer.model.Billboard;
import net.rowf.sigilia.renderer.model.KeyframeSequence;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.model.TiltedBillboard;
import net.rowf.sigilia.renderer.model.Trailboard;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;
import net.rowf.sigilia.renderer.shader.program.AnimatedDeathShader;
import net.rowf.sigilia.renderer.shader.program.AnimatedFlatTextureShader;
import net.rowf.sigilia.renderer.shader.program.ColorizedFlatTextureShader;
import net.rowf.sigilia.renderer.shader.program.FadingColorShader;
import net.rowf.sigilia.renderer.shader.program.FlatTextureShader;
import net.rowf.sigilia.renderer.shader.program.FlickeringShader;
import net.rowf.sigilia.renderer.shader.program.HealthBarShader;
import net.rowf.sigilia.renderer.shader.program.SigilShader;
import net.rowf.sigilia.renderer.shader.program.SplatTextureShader;
import net.rowf.sigilia.renderer.shader.program.TrailShader;
import net.rowf.sigilia.renderer.texture.Texture;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Abstract super-class for all in-game scenarios; handles common 
 * entities (such as the player) and common graphical representations 
 * (such as sigils and weapons), while subclasses provide scenario-specific
 * details.
 * 
 * @author woeltjen
 *
 */
public abstract class BaseScenario implements Scenario {
	
	/*
	 * We get new versions of these common elements exactly once per 
	 * Scenario instantiation. This permits the reuse of a shader through 
	 * multiple objects in scene, but still makes new ones every time 
	 * the GL context is recreated
	 */
	public final DeferredProgram DEFERRED_FLAT_SHADER = FlatTextureShader.deferredForm();
	public final DeferredProgram DEFERRED_ANIM_SHADER = AnimatedFlatTextureShader.deferredForm();
	public final DeferredProgram DEFERRED_DEATH_SHADER = AnimatedDeathShader.deferredForm();
	
	protected static final Name BACKDROP_NAME = new Name("Backdrop");
	
	@Override
	public void populate(List<Entity> entities) {
		StandardEntity e = new StandardEntity();
		new Player().apply(e);
		entities.add(e);
		
		e = new StandardEntity();
		e.setComponent(Position.class, new Position(0,0,Backdrop.SIZE));
		e.setComponent(Name.class, BACKDROP_NAME);
		entities.add(e);
		
		e = new StandardEntity();
		new VictorySentinel().apply(e);
		entities.add(e);
	}
	
	

	@Override
	public void decorate(Map<String, Decorator<Representation>> decorum,
			Resources res) {
        // TODO: Move to BaseScenario?
        Decorator<Representation> particleRepresentation =
        		makeTrailRepresentation(Billboard.UNIT,
        				BitmapFactory.decodeResource(res, R.drawable.default_particle));
        Decorator<Representation> playerRepresentation = 
        		new PeriodicRepresentation( HealthBarShader.deferredForm(),
        				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.generic_particle)), 
        				Billboard.UNIT);
        Decorator<Representation> boltRep = 
        		new PeriodicRepresentation( FlickeringShader.deferredForm(),
        				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.bolt_particle)), 
        				TiltedBillboard.UNIT);
        Decorator<Representation> fireRep = 
        		makeTrailRepresentation(Trailboard.UNIT,
        				BitmapFactory.decodeResource(res, R.drawable.fire_particle));
        Decorator<Representation> deathRepresentation = 
        		new GenericRepresentation( FadingColorShader.deferredForm(), 
        				new Billboard(4), 
        				Arrays.<RenderingElement>asList(new StaticElement<Vector> (
        								VectorParameter.COLOR,
        		        				new Vector(0.5f,0f,0f)
        								)        						
        						), 
        				GenericRepresentation.TRANSITION_ELEMENT);
        Decorator<Representation> victoryRepresentation = 
        		new GenericRepresentation( FadingColorShader.deferredForm(), 
        				new Billboard(4), 
        				Arrays.<RenderingElement>asList(new StaticElement<Vector> (
        								VectorParameter.COLOR,
        		        				new Vector(1f,1f,1f)
        								)        						
        						), 
        				GenericRepresentation.TRANSITION_ELEMENT);
        
        
        decorum.put(IceWeapon.class.getSimpleName(), new GenericRepresentation( 
				DEFERRED_FLAT_SHADER, Billboard.UNIT,
				Arrays.<RenderingElement>asList(new DeferredElement<Texture> (
						SamplerParameter.TEXTURE,
						new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.ice_particle))
						)        						
				)));
        
        decorum.put(BeeWeapon.class.getSimpleName(), new DeferredRepresentation( 
				ColorizedFlatTextureShader.deferredForm(1, 1, 0),
				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.generic_particle)), 
				new Billboard(2)));
        
        decorum.put(DefaultWeapon.class.getSimpleName(), particleRepresentation);
        decorum.put(LightningWeapon.class.getSimpleName(), boltRep);        
        decorum.put(FireWeapon.class.getSimpleName(), fireRep);
        
        decorum.put(LightningWeapon.class.getSimpleName() + Weapon.SIGIL_SUFFIX, 
        		makeSigilRepresentation(BitmapFactory.decodeResource(res, R.drawable.bolt_sigil), 1,1,0));
        decorum.put(FireWeapon.class.getSimpleName() + Weapon.SIGIL_SUFFIX, 
        		makeSigilRepresentation(BitmapFactory.decodeResource(res, R.drawable.fire_sigil), 1,0,0));
        decorum.put(IceWeapon.class.getSimpleName() + Weapon.SIGIL_SUFFIX, 
        		makeSigilRepresentation(BitmapFactory.decodeResource(res, R.drawable.ice_sigil), 0,1,1));
        decorum.put(BeeWeapon.class.getSimpleName() + Weapon.SIGIL_SUFFIX, 
        		makeSigilRepresentation(BitmapFactory.decodeResource(res, R.drawable.bee_sigil), 1,0.75f,0));

        decorum.put(Splat.class.getSimpleName(), new GenericRepresentation(SplatTextureShader.deferredForm(), 
        		Billboard.UNIT, Arrays.<RenderingElement>asList(new DeferredElement<Texture> (
						SamplerParameter.TEXTURE,
        				new DeferredTexture(BitmapFactory.decodeResource(res, R.drawable.splat))
						)), GenericRepresentation.TRANSITION_ELEMENT ));

        
	    decorum.put(Player.class.getSimpleName(), playerRepresentation);
	    decorum.put(Player.DEATH_ANIMATION.get(), deathRepresentation);
	    decorum.put(VictorySentinel.VICTORY_ANIMATION.get(), victoryRepresentation);
	}

	/**
	 * Spawns an entity at ground level
	 * @param p
	 * @param x
	 * @param z
	 * @return
	 */
	protected Entity spawn(Prototype p, float x, float z) {
		Entity temp = new StandardEntity();
		p.apply(temp);
		float y = -1f;
		if (temp.getComponent(Size.class) != null) {
			y += temp.getComponent(Size.class).get().getY() / 2f;
		}
		return spawn(p, x, y, z);
	}
	
	protected void decorateForEnemy(Map<String, Decorator<Representation>> decorum, 
			Class<? extends Enemy> enemyClass, 
			Bitmap bmp, KeyframeSequence seq) {
		
		DeferredTexture tex = new DeferredTexture(bmp);
		decorum.put(enemyClass.getSimpleName(), 
				new AnimatedRepresentation(DEFERRED_ANIM_SHADER, tex, seq));	
		decorum.put(enemyClass.getSimpleName() + Enemy.DEATH_SUFFIX, 
				new AnimatedRepresentation(DEFERRED_DEATH_SHADER, tex, seq));	
		
	}
	
	private Decorator<Representation> makeSigilRepresentation(Bitmap bitmap, float r, float g, float b) {
		return new GenericRepresentation( SigilShader.deferredForm(), 
				Billboard.UNIT, 
				Arrays.<RenderingElement>asList(
						new DeferredElement<Texture> (
								SamplerParameter.TEXTURE,
		        				new DeferredTexture(bitmap)
								),
						new StaticElement<Vector> (
								VectorParameter.COLOR,
		        				new Vector(r,g,b)
								)        						
						), 
				GenericRepresentation.TRANSITION_ELEMENT);
	}
	
	private Decorator<Representation> makeTrailRepresentation(Model m, Bitmap a) {
		return new GenericRepresentation( TrailShader.deferredForm(),
				m,
				Arrays.<RenderingElement>asList(
						new DeferredElement<Texture> (
								SamplerParameter.TEXTURE,
		        				new DeferredTexture(a)
								),
//						new DeferredElement<Texture> (
//								SamplerParameter.NEXT_TEXTURE,
//				        		new DeferredTexture(b)
//								),
						new StaticElement<Vector> (
								VectorParameter.DIRECTION,
								new Vector(0,0.66f,-0.25f)
						)
						),
				GenericRepresentation.TRANSITION_ELEMENT
				);
		
	
	}
	

	protected Entity spawn(Prototype p, float x, float y, float z) {
		Entity entity = new StandardEntity();
		p.apply(entity);
		Size sz = entity.getComponent(Size.class);
		if (sz == null) {
			entity.setComponent(Position.class, new Position(x,y,z));
		} else {
			BoundingBox bound = new BoundingBox(x,y,z,sz.get());
			entity.setComponent(Position.class, bound);
			entity.setComponent(Boundary.class, bound);
		}
		return entity;
	}

	//TODO: Move to Util?
	protected KeyframeSequence loadKeyframeSequence (Resources res, int id, float scale, boolean adjustBottom) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(res.openRawResource(id)));
			
			String[] texLine = reader.readLine().split(",");
			String[] drawingLine = reader.readLine().split(",");
			float[] texCoords = new float[texLine.length];
			short[] drawingOrder = new short[drawingLine.length];
			float   standardBottom = Float.MAX_VALUE;
			
			// Load texture coordinates. These need a little bit of 
			// conversion, as they are in object space, not UV space
			for (int i = 0; i < texLine.length; i++) {
				texCoords[i] = Float.parseFloat(texLine[i]) + 0.5f;
				if (i % 2 == 1) { // Y values get flipped, and may change minimum
					if (texCoords[i] < standardBottom) {
						standardBottom = texCoords[i];
					}
					texCoords[i] = 0.999f - texCoords[i];
				}
			}
			
			for (int i = 0; i < drawingLine.length; i++) {
				drawingOrder[i] = Short.parseShort(drawingLine[i]);
			}
			
			KeyframeSequence seq = new KeyframeSequence(texCoords, drawingOrder);
			
			// Load keyframes from remaining lines
			String line;
			while ((line = reader.readLine()) != null) {
				String[] split    = line.split("\t");
				String   name     = split[0];
				String[] vertsStr = split[1].split(",");
				float[]  verts    = new float[vertsStr.length + vertsStr.length/2];
				float    frameBottom = Float.MAX_VALUE;
				int j = 0;
				for (int i = 0; i < vertsStr.length; i++) {
					float v = Float.parseFloat(vertsStr[i]);
					verts[j++] = v * scale;
					if (i % 2 == 1) {
						if (v < frameBottom) {
							frameBottom = v;
						}
						verts[j++] = 0; // Fill in z values
					}
				}
				if (adjustBottom) {
					for (int i = 1; i < verts.length; i += 3) {
						verts[i] -= (frameBottom - standardBottom + 0.5f) * scale;
					}
				}
				seq.addKeyframe(name, verts);
			}
			
			return seq;
		} catch (Exception e) {
			Log.e("BaseScenario", "Could not read resource for keyframe sequence: " + e.getMessage());
			Log.e("BaseScenario", e.getMessage());
			throw new RuntimeException(e.getMessage());
			//return null;
		}
	}
}
