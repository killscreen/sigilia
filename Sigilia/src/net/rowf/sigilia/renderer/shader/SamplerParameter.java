package net.rowf.sigilia.renderer.shader;

import net.rowf.sigilia.renderer.texture.Texture;
import android.opengl.GLES20;

/**
 * Describes a parameter that will become a sampler; typically, 
 * this means a texture.
 * @author woeltjen
 *
 */
public enum SamplerParameter implements ShaderParameter<Texture> {
	TEXTURE ("uniform sampler2D", "uTexture", false, true),
	
	/**
	 * The subsequent texture in an animation
	 */
	NEXT_TEXTURE ("uniform sampler2D", "uSubsequentTex", false, true),
	
	/**
	 * Texture used as a special effect
	 */
	EFFECT ("uniform sampler2D", "uEffect", false, true);
	
	
	;
	/* Note: This is mostly boilerplate for the 
	 * various sorts of specific parameters. Any 
	 * way to consolidate this? */
	private String   name;
	private String   decl;
	private boolean  frag;
	private boolean  vert;
	private int      texNum;
	
	private SamplerParameter(String decl, String name, boolean vert, boolean frag) {
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
		GLES20.glUniform1i(location, ordinal() );
	}

	@Override
	public void unset(int location) {
	}

	@Override
	public int getLocationIn(int program) {		
		return GLES20.glGetAttribLocation(program, name);
	}

}
