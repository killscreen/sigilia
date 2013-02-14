package net.rowf.sigilia.renderer.shader;

public enum FloatVectorParameter implements ShaderParameter {
	POSITION ("attribute vec4", "vPosition", true, false),
	NORMAL ("attribute vec4", "vNormal", true, false),
	TEXTURE_COORD ("attribute vec2", "vTexCoord", true, false)	
	
	;
	/* Note: This is mostly boilerplate for the 
	 * various sorts of specific parameters. Any 
	 * way to consolidate this? */
	private String   name;
	private String   decl;
	private boolean  frag;
	private boolean  vert;
	
	private FloatVectorParameter(String decl, String name, boolean frag, boolean vert) {
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
