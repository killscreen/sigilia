package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Boundary;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.ConstantMotion;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.entity.NamedPrototype;
import net.rowf.sigilia.geometry.Vector;
import net.rowf.sigilia.input.gesture.DeltaSequence;
import android.util.FloatMath;

public abstract class Weapon extends NamedPrototype {
	private static final Size DEFAULT_SIZE = new Size(0.5f);
	
	protected abstract float velocity();
	
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
		e.setComponent(Motion.class, new ConstantMotion(new Vector(x*v, y*v, z*v)));
		e.setComponent(Position.class, bound);
		e.setComponent(Boundary.class, bound);
	}
}
