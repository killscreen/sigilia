package net.rowf.sigilia.input.gesture;

import net.rowf.sigilia.input.gesture.DirectionSet.Direction;
import android.util.FloatMath;

/**
 * A DeltaSequence that is updated over time (for instance, to 
 * accumulate points as a player is swiping a sigil)
 * 
 * @author woeltjen
 *
 */
public class DynamicDeltaSequence extends DeltaSequence {
	private int count;
	private DirectionSet directionSet;

	private Delta[] deltas;
	
	private float x = Float.NaN;
	private float y = Float.NaN;
	private float totalMagnitude = 0;
	
	public DynamicDeltaSequence(int maximum, DirectionSet directions) {
		this.directionSet = directions;
				
		count = 0;
		deltas = new Delta[maximum];
		for (int i = 0; i < maximum; i++) deltas[i] = new Delta();
	}

	public void addPoint(float x, float y) {
		if (!Float.isNaN(this.x) && !Float.isNaN(this.y)) {
			addDelta(x - this.x, y - this.y);
		}
		this.x = x;
		this.y = y;
	}
	
	public void addDelta(float x, float y) {
		if (count >= deltas.length) {
			return;
		}
		float m = FloatMath.sqrt(x*x + y*y);
		Direction d = directionSet.get(x, y);

		if (count > 0 && deltas[count-1].d == d) {
			deltas[count-1].magnitude += m;
		} else {
			deltas[count].magnitude = m;
			deltas[count].d = d;
			count++;
		}		
		totalMagnitude += m;
	}
	
	public void reset() {
		count = 0;
		totalMagnitude = 0;
		this.x = this.y = Float.NaN;
	}
	
	@Override
	public Delta getTail(int index) {
		int i = count - index - 1;
		return deltas[i];
	}

	@Override
	public int count() {
		return count < deltas.length ? count : deltas.length;
	}

	@Override
	public Direction[] getSampledDelta(int count) {
		Direction[] samples = new Direction[count];
		int i = 0; // First delta to return
		int j = 0; // First delta to check
		
		float step = totalMagnitude / ((float) count);		
		float next = 0;//step / 2f;
		float m    = 0f; // counted magnitude
		
		while (i < count) {
			while (deltas[j].magnitude + m < next && j < (deltas.length-1)) {
				m += deltas[j++].magnitude;
			}
			samples[i++] = deltas[j].d;		
			next += step;
		}
		
		return samples;
	}
}
