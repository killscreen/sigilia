package net.rowf.sigilia.input.gesture;

import net.rowf.sigilia.geometry.Vector;

public class StaticDeltaSequence extends DynamicDeltaSequence {
	// TODO: Where do these belong?
	private static final Vector[] BOLT_POINTS = {
		new Vector (2, 6, 0),
		new Vector (4, 3, 0),
		new Vector (0, 3, 0),
		new Vector (2, 0, 0)
	};
	
	public static final DirectionSet DEFAULT = new DirectionSet(12);
	
	public static final DeltaSequence BOLT = new StaticDeltaSequence(BOLT_POINTS);
	
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
				addDelta(x, y);
			}
			last = p;
		}
	}
	
	
	
}
