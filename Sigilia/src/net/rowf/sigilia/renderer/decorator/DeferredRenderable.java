package net.rowf.sigilia.renderer.decorator;

import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.StandardRenderable;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.texture.Texture;

/**
 * A DeferredRenderable uses Deferred elements when drawing. This 
 * allows for elements like shader programs and textures to be defined 
 * ahead of time, but initialized (in the OpenGL context) only when first 
 * used. 
 * 
 * @author woeltjen
 *
 */
public class DeferredRenderable extends Deferred<Renderable> implements Renderable {
	private Deferred<ParameterizedProgram>     program;
	private Deferred<Texture>     texture;
	
	private Model       model;	
	private float[]     transform; // TODO: Define Transform class, with apply(float[])
	
	public DeferredRenderable(Deferred<ParameterizedProgram> program,
			Deferred<Texture> texture, Model model, float[] transform) {
		super();
		this.program = program;
		this.texture = texture;
		this.model = model;
		this.transform = transform;
	}

	@Override
	public void render(float[] viewMatrix) {
		get().render(viewMatrix);
	}

	@Override
	protected Renderable create() {		
		return new StandardRenderable(program.get(), model, transform, texture.get());
	}
}