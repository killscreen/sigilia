package net.rowf.sigilia.input.gesture;

import android.util.FloatMath;
import net.rowf.sigilia.game.component.physical.Vector;

public class StaticDeltaSequence extends DynamicDeltaSequence {
	// TODO: Where do these belong?
	private static final Vector[] BOLT_POINTS = {
		new Vector (2, 6, 0),
		new Vector (4, 3, 0),
		new Vector (0, 3, 0),
		new Vector (2, 0, 0)
	};

	private static final Vector[] FIRE_POINTS = {
		new Vector (2, 0, 0),
		new Vector (0, 0, 0),
		new Vector (1, FloatMath.sqrt(3), 0),
		new Vector (2, 0, 0)
	};
	
	public static final DirectionSet DEFAULT = new DirectionSet(24);
	
	public static final DeltaSequence BOLT = new StaticDeltaSequence(BOLT_POINTS);
	public static final DeltaSequence FIRE = new StaticDeltaSequence(FIRE_POINTS);
	
	public StaticDeltaSequence(Vector... points) {
		this (DEFAULT, points);
	}
			
	public StaticDeltaSequence(DirectionSet directions, Vector... points) {
		super(100, directions);
	
		// Generate deltas based on indicated points
		Vector last = null;
		for (Vector p : points) {
			if (last != null) {
				float x = p.getX() - last.getX();
				float y = p.getY() - last.getY();
				super.addDelta(x, y);
			}
			last = p;
		}
	}
	
	
	
}
