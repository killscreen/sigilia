package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.physical.Boundary;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.ConstantMotion;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.entity.NamedPrototype;
import net.rowf.sigilia.input.gesture.DeltaSequence;
import android.util.FloatMath;

/**
 * A 'weapon' describes some spell that can be cast by tapping the 
 * screen (as a prototype for the entities produced); also has information 
 * about the sigil shape used to cast the spell, if necessary. 
 * @author woeltjen
 *
 */
public abstract class Weapon extends NamedPrototype {
	public static final String SIGIL_SUFFIX = "Sigil";
	
	private static final Size DEFAULT_SIZE = new Size(0.5f);
	
	protected abstract float velocity();
	
	public final VisibleSigil visibleSigil = new VisibleSigil(getClass().getSimpleName() + SIGIL_SUFFIX);
	
	public float getLifetime() {
		return 5f; // Default to 5 secs
	}
	
	public float getDelay() {
		return 0.1f;
	}
	
	public Size getSize() {
		return DEFAULT_SIZE;
	}
	
	public abstract DeltaSequence getSigil();
	
	public void apply(Entity e, float x, float y) {
		apply(e, x, y, 1f);
	}
	
	public void apply(Entity e, float x, float y, float z) {
		apply(e);

		/* Scaling factor for velocity*/
		float v = velocity() / FloatMath.sqrt( x*x + y*y + z*z );
		
		BoundingBox bound = new BoundingBox(x,y,z, getSize().get());
		
		e.setComponent(Size.class, getSize());
		e.setComponent(Motion.class, getMotion(x*v, y*v, z*v));
		e.setComponent(Position.class, bound);
		e.setComponent(Boundary.class, bound);
	}
	
	protected Motion getMotion(float x, float y, float z) {
		return new ConstantMotion(new Vector(x, y, z));
	}
}
