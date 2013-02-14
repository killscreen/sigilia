package net.rowf.sigilia.renderer.shader;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.rowf.sigilia.renderer.model.Texture;
import android.opengl.GLES20;

public class ParameterizedProgram extends Program {
	private Set<ShaderParameter> parameters = new HashSet<ShaderParameter>();
	
	public ParameterizedProgram(String vertexSnippet, String fragmentSnippet, ShaderParameter... parameters) {
		super(createCode(vertexSnippet, true, parameters), createCode(fragmentSnippet, false, parameters));
		Collections.addAll(this.parameters, parameters);
	}

	public boolean set(MatrixParameter p, float[] matrix) {
		if (parameters.contains(p)) {
			int location = GLES20.glGetUniformLocation(program, p.getName());
			GLES20.glUniformMatrix4fv(location, 1, false, matrix, 0);
			return true;
		} else {
			return false;
		}
	}

	public boolean set(FloatVectorParameter p, FloatBuffer buffer, int dimensions) {
		if (parameters.contains(p)) {
			int location = GLES20.glGetUniformLocation(program, p.getName());
			GLES20.glVertexAttribPointer(location, dimensions, GLES20.GL_FLOAT, false, dimensions * 4, buffer);
			return true;			
		} else {
			return false;
		}
	}
	
	public boolean set(SamplerParameter p, Texture texture) {
		// Note: Use ordinal position to avoid sampler overlap.
		//       In principle this could cause problem if a lot of 
		//       SamplerParameters were defined (max textures varies by device)
		if (parameters.contains(p)) {
			int location = GLES20.glGetUniformLocation(program, p.getName());
		    GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + p.ordinal());
		    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.handle);
			GLES20.glUniform1i(location, p.ordinal());
			return true;
		} else {
			return false;
		}
	}
	
	private static String createCode(String snippet, boolean isVertex, ShaderParameter... parameters) {
		StringBuffer buf = new StringBuffer();
		for (ShaderParameter param : parameters) {
			if ( isVertex && param.usedByVertex  ()) buf.append(param.getFullDeclaration());
			if (!isVertex && param.usedByFragment()) buf.append(param.getFullDeclaration());
		}
		buf.append("void main() {");
		buf.append(snippet);
		buf.append("}");
		return buf.toString();
	}
}
