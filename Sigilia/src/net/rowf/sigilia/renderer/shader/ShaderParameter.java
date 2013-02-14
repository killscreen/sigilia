package net.rowf.sigilia.renderer.shader;

public interface ShaderParameter<T> {
	public String  getName();
	public String  getFullDeclaration();
	boolean usedByFragment();
	boolean usedByVertex();
	void set(T object, int location);
	void unset(int location);
	int getLocationIn(int program);
}
