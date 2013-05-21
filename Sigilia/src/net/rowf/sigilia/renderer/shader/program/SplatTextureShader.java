package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VertexParameter;

/**
 * Texture used to animate "splat" effects (scaling up while fading out)
 * @author woeltjen
 *
 */
public class SplatTextureShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER =
		"  float t = " + ScalarParameter.TRANSITION.getName() + ";\n" +
		"  vCoord = vec2(abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
		                "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
		"  vec4 pos = " + VertexParameter.VERTEX.getName() + ";" +
		"  pos = vec4(pos.x * t, pos.y * t, pos.z, pos.w);\n" +
	    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * pos;";
	private static final String FRAGMENT_SHADER =
		    "  vec4 color = texture2D(" + SamplerParameter.TEXTURE.getName() +
            ", vCoord);" +
		    "  float t = 1.0 - " + ScalarParameter.TRANSITION.getName() + ";\n" +
            "  gl_FragColor = vec4(color.x, color.y, color.z, color.w * t);";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VertexParameter.VERTEX, VertexParameter.TEXTURE_COORD,
		 ScalarParameter.TRANSITION,
		 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public SplatTextureShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
