package net.rowf.sigilia.renderer.shader;

/**
 * Provides an interface for describing parameters used by shaders. Permits 
 * parameters of various types (FloatBuffer, ShortBuffer, etc.) to be handled 
 * uniformly in some circumstances
 * 
 * @author woeltjen
 *
 * @param <T> the Java type used when setting the value of the shader parameter
 */
public interface ShaderParameter<T> {
	public String  getName();
	public String  getFullDeclaration();
	boolean usedByFragment();
	boolean usedByVertex();
	void set(T object, int location);
	void unset(int location);
	int getLocationIn(int program);
}
