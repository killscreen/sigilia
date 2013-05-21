package net.rowf.sigilia.input.gesture;

import net.rowf.sigilia.input.gesture.DirectionSet.Direction;

/**
 * A sequence of orientations in a shape, along with their relative 
 * magnitude. Used to describe both pre-defined sigils and shapes that 
 * are being actively drawn in a scale-invariant manner. 
 * 
 * @author woeltjen
 *
 */
public abstract class DeltaSequence {
	public abstract Direction[] getSampledDelta(int count);
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
		if (c < 1 || count() < 1) return false;
		
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
	
	
	public float getSimilarity(DeltaSequence d, int samples) {
		if (count() == 0) {
			return d.count() == 0 ? 1f : 0f;
		}
		
		float similarity = 0;
		
		Direction[] self  =   getSampledDelta(samples);
		Direction[] other = d.getSampledDelta(samples);
		for (int i = 0; i < samples; i ++) {
			float dot = self[i].getX() * other[i].getX() +
			            self[i].getY() * other[i].getY();
			float scaled = (dot + 1f) / 2f;
			similarity += scaled * scaled;
			
		}
		return (float) similarity / (float) samples;
	}

		
	static class Delta {
		public Direction d;
		public float magnitude;
	}
}
