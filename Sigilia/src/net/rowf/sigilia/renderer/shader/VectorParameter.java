package net.rowf.sigilia.renderer.shader;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

public enum VectorParameter implements ShaderParameter<FloatBuffer> {
	VERTEX ("attribute vec4", "vPosition", true, false, 3),
	NORMAL ("attribute vec4", "vNormal", true, false, 3),
	TEXTURE_COORD ("attribute vec2", "vTexCoord", true, false, 2)	
	
	;
	/* Note: This is mostly boilerplate for the 
	 * various sorts of specific parameters. Any 
	 * way to consolidate this? */
	private String   name;
	private String   decl;
	private boolean  frag;
	private boolean  vert;
	private int      count;
	
	private VectorParameter(String decl, String name, boolean vert, boolean frag, int count) {
		this.decl = decl;
		this.name = name;
		this.frag = frag;
		this.vert = vert;
		this.count = count;
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getFullDeclaration() {
		return decl + " " + name + ";";
	}
	@Override
	public boolean usedByFragment() {
		return frag;
	}
	@Override
	public boolean usedByVertex() {
		return vert;
	}
	

	@Override
	public void set(FloatBuffer object, int location) {
		GLES20.glEnableVertexAttribArray(location);
		GLES20.glVertexAttribPointer(location, count, GLES20.GL_FLOAT, false, count * 4, object);
	}

	@Override
	public void unset(int location) {
		GLES20.glDisableVertexAttribArray(location);
	}

	@Override
	public int getLocationIn(int program) {		
		return GLES20.glGetAttribLocation(program, name);
	}
}
