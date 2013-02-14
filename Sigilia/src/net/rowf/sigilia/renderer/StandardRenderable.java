package net.rowf.sigilia.renderer;

import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.model.Texture;
import net.rowf.sigilia.renderer.shader.Program;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class StandardRenderable implements Renderable {
	private Program     program;
	private Model       model;
	private Texture     texture;
	private float[]     transform;
	

	public StandardRenderable(Program program, Model model, float[] transform, Texture texture) {
		this.program = program;
		this.model = model;
		this.transform = transform;
		this.texture = texture;
	}
	
	@Override
	public void render(float[] viewMatrix) {
		float[] mat = new float[16];
		Matrix.multiplyMM(mat, 0, viewMatrix, 0, transform, 0);
		
		GLES20.glUseProgram(program.program);
		// TODO - convert to use a ParameterizedProgram?
		int vPosition = GLES20.glGetAttribLocation(program.program, "vPosition");
		int uMatrix   = GLES20.glGetUniformLocation(program.program, "uMatrix");
		int vTexCoord = GLES20.glGetAttribLocation(program.program, "vTexCoord");
		int uTexture  = GLES20.glGetAttribLocation(program.program, "uTexture");
		GLES20.glEnableVertexAttribArray(vPosition);
		GLES20.glEnableVertexAttribArray(vTexCoord);
	    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.handle);
		GLES20.glUniform1i(uTexture, 0);
		GLES20.glUniformMatrix4fv(uMatrix, 1, false, mat, 0);
		GLES20.glVertexAttribPointer(vPosition, 3, GLES20.GL_FLOAT, false, 12, model.getVertexes());
		GLES20.glVertexAttribPointer(vTexCoord, 2, GLES20.GL_FLOAT, false, 8 , model.getTexCoords());
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getTriangleCount(), 
				              GLES20.GL_UNSIGNED_SHORT, model.getDrawingOrder());
		GLES20.glDisableVertexAttribArray(vPosition);
	}
}