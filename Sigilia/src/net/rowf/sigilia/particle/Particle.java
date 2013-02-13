package net.rowf.sigilia.particle;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import net.rowf.sigilia.renderer.shader.Program;
import net.rowf.sigilia.util.BufferUtil;
import android.opengl.GLES20;
import android.util.Log;

public class Particle {
	private Program     program;
	private FloatBuffer vertexBuffer;
	private int         vertexCount;
	private ShortBuffer drawBuffer;
	private float[] coords ={ -1,  0,  0,
            1,  0,  0,
            0, -2,  0,
            0,  2,  0,
            0,  0,  1,
            0,  0, -1 };
	private short[] order  =
	{  0, 5, 3,
	   5, 1, 3,
	   1, 4, 3,
	   4, 0, 3,
	   5, 0, 2,
	   1, 5, 2,
	   4, 1, 2,
	   0, 4, 2
    };
	
	public Particle(Program p) {
		program = p;

		
		vertexBuffer = BufferUtil.toBuffer(coords);
		vertexCount = order.length;
		
		drawBuffer = BufferUtil.toBuffer(order);;
	}
	
	public void draw (float[] viewMatrix) {
		//if (!GLES20.glIsProgram(program.program)) Log.e("Program", "Not a program!");
		GLES20.glUseProgram(program.program);
		int vPosition = GLES20.glGetAttribLocation(program.program, "vPosition");
		int uMatrix   = GLES20.glGetUniformLocation(program.program, "uMatrix");
		GLES20.glEnableVertexAttribArray(vPosition);
		GLES20.glUniformMatrix4fv(uMatrix, 1, false, viewMatrix, 0);
		GLES20.glVertexAttribPointer(vPosition, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, vertexCount, 
				              GLES20.GL_UNSIGNED_SHORT, drawBuffer);
		GLES20.glDisableVertexAttribArray(vPosition);
	}

	
	public boolean stale() {
		return false;
	}
}
