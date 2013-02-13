package net.rowf.sigilia.renderer.shader;

public interface ShaderParameter {
	public String  getName();
	public String  getFullDeclaration();
	boolean usedByFragment();
	boolean usedByVertex();
}
