package net.rowf.sigilia.particle;

import net.rowf.sigilia.renderer.shader.Program;
import android.opengl.Matrix;
import android.os.SystemClock;


public class MovingParticle extends Particle {
	long birth;
	float x, y;
	
	public MovingParticle(Program p, float x, float y) {
		super(p);
		birth = SystemClock.uptimeMillis();
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(float[] viewMatrix) {
		float z = (float) (SystemClock.uptimeMillis() - birth) / 1000f;
		float[] mat = new float[16];
		float[] t   = new float[16];
		float[] r   = new float[16];
		Matrix.setIdentityM(t, 0);
		Matrix.setIdentityM(r, 0);
		Matrix.translateM(t, 0, x, y, z);
		float s = Math.max(0, 0.5f - (z/3f));
		Matrix.scaleM(t, 0, s,s,s);
		Matrix.rotateM(r, 0, z*90, 0f, 0f, 1f);
		Matrix.multiplyMM(mat, 0, t, 0, r, 0);
		Matrix.multiplyMM(mat, 0, viewMatrix, 0, mat, 0);
		

		super.draw(mat);
	}
	
	public boolean stale() {
		return SystemClock.uptimeMillis() > birth + 3000f;
	}
	

}
