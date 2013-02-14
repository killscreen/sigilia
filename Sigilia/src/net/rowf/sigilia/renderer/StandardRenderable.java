package net.rowf.sigilia.renderer;

import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.model.Texture;
import net.rowf.sigilia.renderer.shader.VectorParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class StandardRenderable implements Renderable {
	private ParameterizedProgram     program;
	private Model       model;
	private Texture     texture;
	private float[]     transform;
	

	public StandardRenderable(ParameterizedProgram program, Model model, float[] transform, Texture texture) {
		this.program = program;
		this.model = model;
		this.transform = transform;
		this.texture = texture;
	}
	
	@Override
	public void render(float[] viewMatrix) {
		float[] matrix = new float[16];
		Matrix.multiplyMM(matrix, 0, viewMatrix, 0, transform, 0);
		
		program.begin();
		// TODO - convert to use a ParameterizedProgram?
		program.set(MatrixParameter.TRANSFORMATION, matrix);
		program.set(SamplerParameter.TEXTURE, texture);
		program.set(VectorParameter.VERTEX, model.getVertexes());
		program.set(VectorParameter.TEXTURE_COORD, model.getTexCoords());
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getTriangleCount(), 
				              GLES20.GL_UNSIGNED_SHORT, model.getDrawingOrder());
		// TODO: GLES20.glDisableVertexAttribArray(vPosition);
	}
}