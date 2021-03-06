package net.rowf.sigilia.renderer.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/**
 * Describes an OpenGL texture, based on a supplied Bitmap object.
 * Performs OpenGL initialization in the constructor, so these objects 
 * should not be made unless there is an OpenGL context available.
 * 
 * @author woeltjen
 *
 */
public class Texture {
	public final int handle;
	
	public Texture(Bitmap bitmap) {
		int temp[] = new int[1];
		GLES20.glGenTextures(1, temp, 0);
		handle = temp[0];
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, handle);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
	}
}
