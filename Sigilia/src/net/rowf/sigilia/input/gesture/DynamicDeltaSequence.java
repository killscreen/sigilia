package net.rowf.sigilia.input.gesture;

import net.rowf.sigilia.input.gesture.DirectionSet.Direction;

public class DynamicDeltaSequence extends DeltaSequence {
	private int count;
	private DirectionSet directionSet;

	private Delta[] deltas;
	
	public DynamicDeltaSequence(int maximum, DirectionSet directions) {
		this.directionSet = directions;
				
		count = 0;
		deltas = new Delta[maximum];
		for (int i = 0; i < maximum; i++) deltas[i] = new Delta();
	}

	public void addDelta(float x, float y) {
		float m = (float) Math.sqrt(x*x + y*y);
		Direction d = directionSet.get(x, y);
		int i = count % deltas.length;
		if (count > 0 && deltas[i].d == d) {
			deltas[i].magnitude += m;
		} else {
			deltas[i].magnitude = m;
			deltas[i].d = d;
			count++;
		}		
	}
	
	public void reset() {
		count = 0;
	}
	
	@Override
	public Delta getTail(int index) {
		int i = count - index;
		while (i < 0) i += deltas.length;
		return deltas[i % deltas.length];
	}

	@Override
	public int count() {
		return count < deltas.length ? count : deltas.length;
	}
}
