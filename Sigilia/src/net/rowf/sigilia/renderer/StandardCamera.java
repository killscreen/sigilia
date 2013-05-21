package net.rowf.sigilia.renderer;

import net.rowf.sigilia.renderer.PerspectiveRenderer.Camera;

/**
 * Standard implementation of a camera, looking from the 
 * origin toward positive Z. 
 * 
 * Note that this orientation is assumed implicitly by other 
 * parts of the engine. Non-standard cameras will likely result 
 * in incorrect rendering. 
 * 
 * @author woeltjen
 *
 */
public class StandardCamera implements Camera {
	private static final float[] EYE     = { 0, 0,   0 };
	private static final float[] LOOK_AT = { 0, 0, 100 };
	private static final float[] UP      = { 0, 1, 0 };
	
	@Override
	public float[] getEye() {
		return EYE;
	}

	@Override
	public float[] getLookAt() {
		return LOOK_AT;
	}

	@Override
	public float[] getUp() {
		return UP;
	}

}
