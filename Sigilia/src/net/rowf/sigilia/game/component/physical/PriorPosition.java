package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.component.Position;

public class PriorPosition extends Position {
	private float timestamp;
	private float timedelta;
	
	public PriorPosition(Position p, float timestamp, float timedelta) {
		super(p.getX(), p.getY(), p.getZ());
		this.timestamp = timestamp;
		this.timedelta = timedelta;
	}
	
	public void set(Position p, float timestamp, float timedelta) {
		elements[0] = p.getX();
		elements[1] = p.getY();
		elements[2] = p.getZ();
		this.timestamp = timestamp;
	}
	
	public float getTimestamp() {
		return timestamp;
	}
	
	public float getTimestep() {
		return timedelta;
	}
}
