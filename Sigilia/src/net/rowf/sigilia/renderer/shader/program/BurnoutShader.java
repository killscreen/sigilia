package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;
import net.rowf.sigilia.renderer.shader.VertexParameter;

/**
 * Handles "burn out" effect, as used by trees when destroyed by fire.
 * @author woeltjen
 *
 */
public class BurnoutShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
			"  vCoord = vec2(abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
			                "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
		    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
		                         VertexParameter.VERTEX.getName() + ";";
	private static final String FRAGMENT_SHADER =
			"  float t = " + ScalarParameter.TRANSITION.getName() + ";\n" +
		    "  vec4 color = texture2D(" + SamplerParameter.TEXTURE.getName() +
            	", vCoord);\n" +
		    "  gl_FragColor = vec4(" +
		    					VectorParameter.COLOR.getName() + ".x, " +
		    					VectorParameter.COLOR.getName() + ".y, " +
		    					VectorParameter.COLOR.getName() + ".z, " +
		    					"color.w * (1.0-t));\n";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{MatrixParameter.TRANSFORMATION, 
		 SamplerParameter.TEXTURE, 
		 VertexParameter.TEXTURE_COORD,
		 VectorParameter.COLOR,
		 VertexParameter.VERTEX, ScalarParameter.TRANSITION,
		 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public BurnoutShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
