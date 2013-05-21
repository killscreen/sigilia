package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VertexParameter;

/**
 * Simple shader that draws a model as a flat color (used for test purposes)
 * @author woeltjen
 *
 */
public class FlatColorShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
		"  vCoord = vec2(abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
		                "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
	    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
	                         VertexParameter.VERTEX.getName() + ";";
	private static final String FRAGMENT_SHADER =
		    "  gl_FragColor = vec4(1,1,1,0)";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VertexParameter.VERTEX, VertexParameter.TEXTURE_COORD, 
		 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public FlatColorShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
