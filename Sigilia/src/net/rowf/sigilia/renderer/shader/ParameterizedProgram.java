package net.rowf.sigilia.renderer.shader;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.opengl.GLES20;

/**
 * A shader program that accepts certain well defined parameters. Provides 
 * methods to allow users of the program to invoke simple "set" methods, 
 * without having to know the specific text name or integer location 
 * of the parameter being communicated.
 * 
 * Note that this also permits shaders to be defined by providing only 
 * the inner part of the shader code - boilerplate such as "main" and 
 * definitions for parameters are handled automatically.
 * 
 * @author woeltjen
 *
 */
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
		buf.append("precision mediump float;\n");
		for (ShaderParameter<?> param : parameters) {
			if ( isVertex && param.usedByVertex  ()) buf.append(param.getFullDeclaration()).append('\n');
			if (!isVertex && param.usedByFragment()) buf.append(param.getFullDeclaration()).append('\n');
		}
		buf.append("void main() {");
		buf.append(snippet);
		buf.append("}\n");
		String code = buf.toString();
		//Log.w("shader", code);
		return code;
	}
}
