package net.rowf.sigilia.input;

import java.util.List;

public interface TapInput {
	public List<Tap> getPendingEvents();
	
	public static class Tap {
		public final float x;
		public final float y;
		public final float timeStamp;
		public Tap(float x, float y, float timeStamp) {
			super();
			this.x = x;
			this.y = y;
			this.timeStamp = timeStamp;
		}		
	}
}
