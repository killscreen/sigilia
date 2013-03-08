package net.rowf.sigilia.renderer.decorator;

import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.ShaderParameter;

/**
 * A DeferredProgram waits to create an actual program object until one is 
 * demanded. This allows the object to be created on the Engine thread, 
 * while the resource itself is initialized on the UI thread (after the GL
 * context has been created) 
 * 
 * @author woeltjen
 *
 */
public class DeferredProgram extends Deferred<ParameterizedProgram> {
	private String vertexCode;
	private String fragmentCode;
	private ShaderParameter<?>[] parameters;
	
	public DeferredProgram(String vertexCode, String fragmentCode,
			ShaderParameter<?>... parameters) {
		super();
		this.vertexCode = vertexCode;
		this.fragmentCode = fragmentCode;
		this.parameters = parameters;
	}

	@Override
	protected ParameterizedProgram create() {
		return new ParameterizedProgram(vertexCode, fragmentCode, parameters);
	}
}
