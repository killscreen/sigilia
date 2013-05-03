package net.rowf.sigilia.game.component.visual;


public class Animation implements Transition {
	private String DEFAULT_FRAME_NAME = "base";
	
	private String currentFrame = DEFAULT_FRAME_NAME;
	private String nextFrame = DEFAULT_FRAME_NAME;
	
	private float startTime;
	private float endTime;
	
	private float currentTime;
	
	public void setCurrentTime(float time) {
		this.currentTime = time;
		if (currentTime >= endTime) {
			currentFrame = nextFrame;
		}
	}
	
	public void setNextFrame(String name, float duration) {
		startTime = currentTime;
		nextFrame = name;
		endTime   = currentTime + duration;
	}
	
	public String getCurrentFrame() {
		return currentFrame;
	}
	
	public String getNextFrame() {
		return nextFrame;
	}
	
	public float getProgress() {
		if (currentTime <= startTime) {
			return 0f;
		}
		if (currentTime >= endTime) {
			return 1f;
		}
		return (currentTime - startTime) / (endTime - startTime);
	}
	
	public boolean isAnimating() {
		return currentTime < endTime;
	}
}
