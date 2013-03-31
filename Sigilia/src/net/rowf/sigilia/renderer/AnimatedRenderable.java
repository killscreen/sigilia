package net.rowf.sigilia.renderer;

import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.decorator.Deferred;
import net.rowf.sigilia.renderer.model.AnimatedModel;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;
import net.rowf.sigilia.renderer.texture.Texture;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class AnimatedRenderable implements Renderable {
	private ParameterizedProgram     program;
	private AnimatedModel       model;
	private Texture     texture;
	private float[]     transform; // TODO: Define Transform class, with apply(float[])
	private float       animation;
	

	public AnimatedRenderable(ParameterizedProgram program, AnimatedModel model, float[] transform, Texture texture, float animation) {
		this.program = program;
		this.model = model;
		this.transform = transform;
		this.texture = texture;
		this.animation = animation;
	}
	
	@Override
	public void render(float[] viewMatrix) {
		float[] matrix = new float[16]; // TODO: Remove these!
		Matrix.multiplyMM(matrix, 0, viewMatrix, 0, transform, 0);
		
		program.begin();
		
		program.set(MatrixParameter.TRANSFORMATION, matrix);
		program.set(SamplerParameter.TEXTURE, texture);
		program.set(VectorParameter.VERTEX, model.getVertexes());
		program.set(VectorParameter.SUBSEQUENT_VERTEX, model.getNextVertexes());
		program.set(VectorParameter.TEXTURE_COORD, model.getTexCoords());
		program.set(ScalarParameter.TRANSITION, animation);

		// TODO: May as well move this to program?
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getTriangleCount() * 3, 
				              GLES20.GL_UNSIGNED_SHORT, model.getDrawingOrder());
		
		program.end();
	}
	
	public static class DeferredAnimatedRenderable extends Deferred<Renderable> implements Renderable {
		private Deferred<ParameterizedProgram>     program;
		private AnimatedModel       model;
		private Deferred<Texture>     texture;
		private float[]     transform; // TODO: Define Transform class, with apply(float[])
		private float       animation;
		
		
		
		public DeferredAnimatedRenderable(
				Deferred<ParameterizedProgram> program, AnimatedModel model,
				 float[] transform, Deferred<Texture> texture, float animation) {
			super();
			this.program = program;
			this.model = model;
			this.texture = texture;
			this.transform = transform;
			this.animation = animation;
		}

		@Override
		public void render(float[] viewMatrix) {
			get().render(viewMatrix);
		}

		@Override
		protected Renderable create() {
			return new AnimatedRenderable(program.get(), model, transform, texture.get(), animation);
		}
		
	}
	
}