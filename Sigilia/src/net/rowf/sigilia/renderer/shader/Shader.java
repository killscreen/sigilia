package net.rowf.sigilia.renderer.shader;

import android.opengl.GLES20;
import android.util.Log;

public abstract class Shader {
	public final int shader;
	public Shader(String sourceCode, int type) {	
		if (sourceCode.contains("???")) {
			Log.e("???", "???" );
		}
		shader = GLES20.glCreateShader(type);
		//Log.e("SHADER", "shader=" + shader);
		GLES20.glShaderSource(shader, sourceCode);
		checkError("ShaderSource");
		GLES20.glCompileShader(shader);
		checkError("CompileShader");
		if (!GLES20.glIsShader(shader)) Log.e("Shader", "Not a shader");
	}
	
	boolean logged = false;
	private boolean checkError(String location) {
		if (!logged)
		for (int err = GLES20.glGetError(); err != GLES20.GL_NO_ERROR; err = GLES20.glGetError()) {
			Log.e("inf", "Error at " + location + ": " + err);
			logged = true;
		}
		return logged;
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
