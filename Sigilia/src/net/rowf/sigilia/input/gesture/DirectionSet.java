package net.rowf.sigilia.input.gesture;

import net.rowf.sigilia.input.gesture.DeltaSequence.Delta;

public class DirectionSet {
	private Direction[] deltas;
	
	public DirectionSet(int count) {
		deltas = new Direction[count];
		for (int i = 0; i < count; i++) {
			deltas[i] = new NearestDirection( 
					((float) i / (float) count) * 2f * (float) Math.PI);
		}
	}
	
	public Direction get(float x, float y) {
		float r = (float) Math.atan2(y, x);
		if (r < 0) r = (float) (2*Math.PI) - r;
		r *= deltas.length / (2*Math.PI);
		return deltas[((int) (r+0.5f)) % deltas.length];
	}
	
	public static interface Direction {
		public float getX();
		public float getY();
	}
	
	private static class NearestDirection implements Direction {
		private float x, y;
		
		public NearestDirection(float radians) {
			x = (float) Math.cos(radians);
			y = (float) Math.sin(radians);
		}

		@Override
		public float getX() {
			return x;
		}

		@Override
		public float getY() {
			return y;
		}
		
	}
}
