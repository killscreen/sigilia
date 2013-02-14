package net.rowf.sigilia.renderer.shader;

public enum SamplerParameter implements ShaderParameter {
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
}
