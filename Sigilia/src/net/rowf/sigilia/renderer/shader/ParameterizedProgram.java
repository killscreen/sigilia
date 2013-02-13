package net.rowf.sigilia.renderer.shader;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
