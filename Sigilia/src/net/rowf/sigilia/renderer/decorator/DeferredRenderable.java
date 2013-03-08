package net.rowf.sigilia.renderer.decorator;

import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;
import net.rowf.sigilia.renderer.texture.Texture;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class DeferredRenderable implements Renderable {
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
		float[] matrix = new float[16]; // TODO: Remove these!
		Matrix.multiplyMM(matrix, 0, viewMatrix, 0, transform, 0);
		ParameterizedProgram p = program.get();
		p.begin();

		p.set(MatrixParameter.TRANSFORMATION, matrix);
		p.set(SamplerParameter.TEXTURE, texture.get());
		p.set(VectorParameter.VERTEX, model.getVertexes());
		p.set(VectorParameter.TEXTURE_COORD, model.getTexCoords());

		// TODO: May as well move this to program?
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getTriangleCount() * 3, 
				              GLES20.GL_UNSIGNED_SHORT, model.getDrawingOrder());
		
		p.end();
	}
}