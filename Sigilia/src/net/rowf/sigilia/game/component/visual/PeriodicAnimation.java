package net.rowf.sigilia.game.component.visual;

import android.util.FloatMath;

/**
 * Animates an entity in a loop (either sinusoidal or saw-tooth)
 * 
 * Note that this does not maintain the named keyframe element of 
 * most animations; it is typically used when shaders care about 
 * some cyclical or timed behavior, but do not care about transitioning 
 * between keyframes.
 * 
 * @author woeltjen
 *
 */
public class PeriodicAnimation extends Animation {
	private final float period;
	private boolean sinusoidal;

	private float lastTime = Float.MIN_VALUE;
	
	private float progress = 0f;
	
	public PeriodicAnimation(float period) {
		this(period, true);
	}
	
	public PeriodicAnimation(float period, boolean sinusoidal) {
		super();
		this.period = period;
		this.sinusoidal = sinusoidal;
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
		return sinusoidal ?
				((FloatMath.sin((float) Math.PI * 2f * progress / period) + 1f) / 2f) :
				((progress/period) - FloatMath.floor(progress/period));
	}

	@Override
	public boolean isAnimating() {
		return true;
	}
	
	
}
