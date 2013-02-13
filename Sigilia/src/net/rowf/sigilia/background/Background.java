package net.rowf.sigilia.background;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import net.rowf.sigilia.renderer.shader.Program;
import net.rowf.sigilia.util.BufferUtil;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

public class Background {
	private Program program;
	private FloatBuffer vertexBuffer;
	private ShortBuffer drawBuffer;
	private FloatBuffer texCoordBuffer;
	private int vertexCount;
	private int[] texHandle = new int[1];
	
	private float[] coords = {
		-10, 7, 10,
		 10, 7, 10,
		-10, -3, 10,
		 10, -3, 10,
		-10, -3, 0,
		 10, -3, 0 
	};
	private short[] order  =
	{  0, 1, 3,
	   3, 2, 0,
	   2, 3, 4,
	   5, 4, 3
    };
	
	private float[] texCoords = {
		0, 0,
		1, 0,
		0, 0.5f,
		1, 0.5f,
		1, 1,
		0, 1
	};
	
	public Background (Program p, Bitmap b) {
		program = p;

		GLES20.glGenTextures(1, this.texHandle, 0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texHandle[0]);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, b, 0);
		
		vertexBuffer = BufferUtil.toBuffer(coords);
		vertexCount = order.length;
		
		drawBuffer = BufferUtil.toBuffer(order);;
		texCoordBuffer = BufferUtil.toBuffer(texCoords);
	}
	
	
	public void draw (float[] viewMatrix) {
		//if (!GLES20.glIsProgram(program.program)) Log.e("Program", "Not a program!");
		GLES20.glUseProgram(program.program);
		int vPosition = GLES20.glGetAttribLocation(program.program, "vPosition");
		int vTexCoord = GLES20.glGetAttribLocation(program.program, "vTexCoord");
		int uTexture  = GLES20.glGetAttribLocation(program.program, "uTexture");
		int uMatrix   = GLES20.glGetUniformLocation(program.program, "uMatrix");
		GLES20.glEnableVertexAttribArray(vPosition);
		GLES20.glEnableVertexAttribArray(vTexCoord);
	    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texHandle[0]);
		GLES20.glUniform1i(uTexture, 0);
		GLES20.glUniformMatrix4fv(uMatrix, 1, false, viewMatrix, 0);
		GLES20.glVertexAttribPointer(vPosition, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
		GLES20.glVertexAttribPointer(vTexCoord, 2, GLES20.GL_FLOAT, false, 8 , texCoordBuffer);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, vertexCount, 
				              GLES20.GL_UNSIGNED_SHORT, drawBuffer);
		GLES20.glDisableVertexAttribArray(vPosition);
		GLES20.glDisableVertexAttribArray(vTexCoord);
	}

	
	public boolean stale() {
		return false;
	}
}
