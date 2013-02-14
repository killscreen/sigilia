package net.rowf.sigilia.renderer.shader;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.opengl.GLES20;
import android.util.Log;

public class ParameterizedProgram extends Program {
	private Set<ShaderParameter<?>> parameters = new HashSet<ShaderParameter<?>>();
	private Map<ShaderParameter<?>, Integer> activeParameters = 
			new HashMap<ShaderParameter<?>, Integer>();
	
	public ParameterizedProgram(String vertexSnippet, String fragmentSnippet, ShaderParameter<?>... parameters) {
		super(createCode(vertexSnippet, true, parameters), createCode(fragmentSnippet, false, parameters));
		Collections.addAll(this.parameters, parameters);
	}
	
	public void begin() {
		activeParameters.clear();
		GLES20.glUseProgram(program);		
	}
	
	public void end() {
		for (Entry<ShaderParameter<?>, Integer> active : activeParameters.entrySet()) {
			active.getKey().unset(active.getValue());
		}
		activeParameters.clear();
	}
	
	public <T> boolean set(ShaderParameter<T> p, T object) {
		if (parameters.contains(p)) {
			int location = p.getLocationIn(program);
			p.set(object, location);
			activeParameters.put(p, location);
			return true;
		} else {
			return false;
		}
	}
	
	
	private static String createCode(String snippet, boolean isVertex, ShaderParameter<?>... parameters) {
		StringBuffer buf = new StringBuffer();
		for (ShaderParameter<?> param : parameters) {
			if ( isVertex && param.usedByVertex  ()) buf.append(param.getFullDeclaration());
			if (!isVertex && param.usedByFragment()) buf.append(param.getFullDeclaration());
		}
		buf.append("void main() {");
		buf.append(snippet);
		buf.append("}");
		return buf.toString();
	}
}
