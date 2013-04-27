package net.rowf.sigilia.renderer;

import java.nio.ShortBuffer;
import java.util.Collection;

import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.decorator.Deferred;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class GenericRenderable implements Renderable {
	private Deferred<ParameterizedProgram> program;
	private float[]     transform; // TODO: Define Transform class, with apply(float[])
	private ShortBuffer drawOrder;
	private int         triangleCount;
	private Collection<RenderingElement> elements;
	
	public GenericRenderable(Deferred<ParameterizedProgram> program,
			ShortBuffer drawOrder, int triangleCount, 
			Collection<RenderingElement> elements) {
		super();
		this.program = program;
		this.transform = new float[16];
		this.drawOrder = drawOrder;
		this.triangleCount = triangleCount;
		this.elements = elements;
		Matrix.setIdentityM(transform, 0);
	}

	@Override
	public void render(float[] viewMatrix) {
		float[] matrix = new float[16];
		Matrix.multiplyMM(matrix, 0, viewMatrix, 0, transform, 0);
		
		ParameterizedProgram program = this.program.get();
		program.begin();

		program.set(MatrixParameter.TRANSFORMATION, matrix);
		
		for (RenderingElement element : elements) {
			element.apply(program);
		}

		// TODO: May as well move this to program?
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, triangleCount * 3, 
				              GLES20.GL_UNSIGNED_SHORT, drawOrder);
		
		program.end();
	}
	
	public void translate(float x, float y, float z) {
		Matrix.translateM(transform, 0, x, y, z);
	}
	
	public void rotate(float x, float y, float z) {
		Matrix.rotateM(transform, 0, x, 1, 0, 0);
		Matrix.rotateM(transform, 0, y, 0, 1, 0);
	}
	
	public static abstract class RenderingElement {
		public abstract void apply(ParameterizedProgram p);
	}
	
	public static class CompositeRenderingElement extends RenderingElement {
		private RenderingElement[] elements;
		public CompositeRenderingElement(RenderingElement... elements) {
			this.elements = elements;
		}
		@Override
		public void apply(ParameterizedProgram p) {
			for (RenderingElement element : elements) {
				element.apply(p);
			}
		}		
	}
		
	public static abstract class RenderingItem<T> extends RenderingElement {
		public final ShaderParameter<T> parameter;
		
		private RenderingItem(ShaderParameter<T> parameter) {
			super();
			this.parameter = parameter;
		}

		public abstract T value();
		
		@Override
		public void apply(ParameterizedProgram p) {
			p.set(parameter, value());
		}
	}
	
	public static class DeferredElement<T> extends RenderingItem<T> {
		private final Deferred<T> deferred;
		
		public DeferredElement(ShaderParameter<T> param, Deferred<T> deferred) {
			super(param);
			this.deferred = deferred;
		}

		@Override
		public T value() {			
			return deferred.get();
		}		
	}
	
	public static class StaticElement<T> extends RenderingItem<T> {
		private final T value;
		
		public StaticElement(ShaderParameter<T> param, T value) {
			super(param);
			this.value = value;
		}

		@Override
		public T value() {			
			return value;
		}		
	}


}
