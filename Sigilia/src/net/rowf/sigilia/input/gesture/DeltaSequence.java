package net.rowf.sigilia.input.gesture;

import net.rowf.sigilia.input.gesture.DirectionSet.Direction;

public abstract class DeltaSequence {
	public abstract Delta getTail(int index);
	public abstract int count();
	
	/**
	 * 
	 * @param q
	 * @param margin expressed as a ratio of magnitudes
	 * @return
	 */
	public boolean endsWith (DeltaSequence q, float margin) {
		int c = q.count();
		if (c > count()) return false;
		
		float minq = Float.MAX_VALUE;
		float mins = Float.MAX_VALUE; // min self
		
		for (int i = 0 ; i < c ; i++) {
			Delta ds =   getTail(i);
			Delta dq = q.getTail(i);
			if (!(ds.d.equals(dq.d))) {
				return false; // Must have same direction (& DirectionSet, likely)
			}
			minq = Math.min(minq, dq.magnitude);
			mins = Math.min(mins, dq.magnitude);
		}
		
		float norm = mins / minq;
		float plat = 1f + margin;
		
		for (int i = 0 ; i < c ; i++) {
			Delta ds =   getTail(i);
			Delta dq = q.getTail(i);
			float m = dq.magnitude * norm;
			if (m / ds.magnitude > plat || ds.magnitude / m > plat) {
				return false;
			}
		}
		
		return true;
	}
		
	static class Delta {
		public Direction d;
		public float magnitude;
	}
}
