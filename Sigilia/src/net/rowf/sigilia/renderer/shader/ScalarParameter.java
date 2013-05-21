package net.rowf.sigilia.renderer.shader;

import android.opengl.GLES20;

/**
 * Describes parameters that are represented as a single scalar 
 * value (that is, as a float)
 * 
 * @author woeltjen
 *
 */
public enum ScalarParameter implements ShaderParameter<Float> {
	SECONDS ("uSeconds"),
	
	/**
	 * How far along (0-1) we are in an animated transition
	 */
	TRANSITION ("uTransition"),
	
	SPEED ("uSpeed")
	
	;

	/* Note: This is mostly boilerplate for the 
	 * various sorts of specific parameters. Any 
	 * way to consolidate this? */
	private String   name;
	private String   decl;
	private boolean  frag;
	private boolean  vert;
	
	private ScalarParameter(String name) {
		this(name, true, true);
	}
	
	private ScalarParameter(String name, boolean vert, boolean frag) {
		this("uniform mediump float", name, vert, frag);
	}
	
	private ScalarParameter(String decl, String name, boolean vert, boolean frag) {
		this.decl = decl;
		this.name = name;
		this.frag = frag;
		this.vert = vert;
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
	public void set(Float object, int location) {
		GLES20.glUniform1f(location, object.floatValue());
	}

	@Override
	public void unset(int location) {		
	}

	@Override
	public int getLocationIn(int program) {
		return GLES20.glGetUniformLocation(program,name);
	}

}
