package net.rowf.sigilia.game.component.visual;

import android.util.FloatMath;


public class PeriodicAnimation extends Animation {
	private final float period;

	private float lastTime = Float.MIN_VALUE;
	
	private float progress = 0f;
	
	public PeriodicAnimation(float period) {
		super();
		this.period = period;
	}

	@Override
	public void setCurrentTime(float time) {
		float delta = 0;
		if (lastTime > Float.MIN_VALUE) {
			delta = time - lastTime;
		}
		lastTime = time;
		progress += delta;
		
	}

	@Override
	public float getProgress() {
		return (FloatMath.sin((float) Math.PI * 2f * progress / period) + 1f) / 2f;
	}

	@Override
	public boolean isAnimating() {
		return true;
	}
	
	
}
