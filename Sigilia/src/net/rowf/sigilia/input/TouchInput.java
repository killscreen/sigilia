package net.rowf.sigilia.input;

import java.util.List;

public interface TouchInput {
	public List<Touch> getPendingEvents();
	
	public static class Touch {
		public final float x;
		public final float y;
		public final float timeStamp;
		public Touch(float x, float y, float timeStamp) {
			super();
			this.x = x;
			this.y = y;
			this.timeStamp = timeStamp;
		}		
	}
	
	public static final Touch RELEASE = new Touch(0,0,0);
}
