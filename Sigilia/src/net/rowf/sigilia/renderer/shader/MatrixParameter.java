package net.rowf.sigilia.renderer.shader;

import android.opengl.GLES20;

/**
 * Describes parameters communicated to shader programs as matrixes
 * (for instance, transformation)
 * 
 * @author woeltjen
 *
 */
public enum MatrixParameter implements ShaderParameter<float[]> {
	TRANSFORMATION ("uniform mat4", "uTransform", true, false);
	;
	/* Note: This is mostly boilerplate for the 
	 * various sorts of specific parameters. Any 
	 * way to consolidate this? */
	private String   name;
	private String   decl;
	private boolean  frag;
	private boolean  vert;
	
	private MatrixParameter(String decl, String name, boolean vert, boolean frag) {
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

	/* These elements are unique to MatrixParameter */
	
	@Override
	public void set(float[] matrix, int location) {		
		GLES20.glUniformMatrix4fv(location, 1, false, matrix, 0);
	}

	@Override
	public void unset(int location) {
	}

	@Override
	public int getLocationIn(int program) {
		return GLES20.glGetUniformLocation(program,name);
	}
}
