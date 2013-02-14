package net.rowf.sigilia.renderer.shader;

import java.nio.FloatBuffer;

import net.rowf.sigilia.renderer.model.Texture;
import android.opengl.GLES20;

public enum SamplerParameter implements ShaderParameter<Texture> {
	TEXTURE ("uniform sampler2D", "uTexture", false, true);
	;
	/* Note: This is mostly boilerplate for the 
	 * various sorts of specific parameters. Any 
	 * way to consolidate this? */
	private String   name;
	private String   decl;
	private boolean  frag;
	private boolean  vert;
	
	private SamplerParameter(String decl, String name, boolean frag, boolean vert) {
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
	public void set(Texture texture, int location) {
		// TODO: Using ordinal is risky if heavily multi-textured
	    GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + ordinal());
	    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.handle);
		GLES20.glUniform1i(location, ordinal());
	}

	@Override
	public void unset(int location) {
	}

	@Override
	public int getLocationIn(int program) {		
		return GLES20.glGetUniformLocation(program, name);
	}

}
