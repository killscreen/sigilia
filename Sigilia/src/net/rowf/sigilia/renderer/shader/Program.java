package net.rowf.sigilia.renderer.shader;

import net.rowf.sigilia.renderer.shader.Shader.FragmentShader;
import net.rowf.sigilia.renderer.shader.Shader.VertexShader;
import android.opengl.GLES20;
import android.util.Log;


public class Program {
	public final int program;
	
	public Program(String vertexCode, String fragmentCode) {
		this (new VertexShader(vertexCode), new FragmentShader(fragmentCode));
	}
	
	public Program(VertexShader vertexShader, FragmentShader fragmentShader) {
		program = GLES20.glCreateProgram();
		if (!GLES20.glIsShader(vertexShader.shader)) Log.e("Vertex", "Not a shader");
		if (!GLES20.glIsShader(fragmentShader.shader)) Log.e("Fragment", "Not a shader");
		GLES20.glAttachShader(program, vertexShader.shader);
		GLES20.glAttachShader(program, fragmentShader.shader);
		GLES20.glLinkProgram(program);
	}
}
