package net.rowf.sigilia.shader;

import android.opengl.GLES20;
import android.util.Log;

public abstract class Shader {
	public final int shader;
	public Shader(String sourceCode, int type) {
		shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, sourceCode);
		GLES20.glCompileShader(shader);
		if (!GLES20.glIsShader(shader)) Log.e("Shader", "Not a shader");
	}
	

	
	public static class VertexShader extends Shader {
		public VertexShader(String sourceCode) {
			super(sourceCode, GLES20.GL_VERTEX_SHADER);
		}		
	}
	
	public static class FragmentShader extends Shader {
		public FragmentShader(String sourceCode) {
			super(sourceCode, GLES20.GL_FRAGMENT_SHADER);
		}		
	}
}
