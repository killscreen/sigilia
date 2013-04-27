package net.rowf.sigilia.renderer;

import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.shader.VertexParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.texture.Texture;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class StandardRenderable implements Renderable {
	private ParameterizedProgram     program;
	private Model       model;
	private Texture     texture;
	private float[]     transform; // TODO: Define Transform class, with apply(float[])

	public StandardRenderable(ParameterizedProgram program, Model model, float[] transform, Texture texture) {
		this.program = program;
		this.model = model;
		this.transform = transform;
		this.texture = texture;
	}
	
	@Override
	public void render(float[] viewMatrix) {
		float[] matrix = new float[16]; // TODO: Remove these!
		Matrix.multiplyMM(matrix, 0, viewMatrix, 0, transform, 0);
		
		program.begin();

		program.set(MatrixParameter.TRANSFORMATION, matrix);
		program.set(SamplerParameter.TEXTURE, texture);
		program.set(VertexParameter.VERTEX, model.getVertexes());
		program.set(VertexParameter.TEXTURE_COORD, model.getTexCoords());

		// TODO: May as well move this to program?
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getTriangleCount() * 3, 
				              GLES20.GL_UNSIGNED_SHORT, model.getDrawingOrder());
		
		program.end();
	}
}