package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VertexParameter;

public class ColorizedFlatTextureShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
		"  vCoord = vec2(/*???*/abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
		                "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
	    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
	                         VertexParameter.VERTEX.getName() + ";";
	private static final String FRAGMENT_SHADER =
		    "  gl_FragColor = texture2D(" + SamplerParameter.TEXTURE.getName() +
            ", vCoord);";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VertexParameter.VERTEX, VertexParameter.TEXTURE_COORD, 
		 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	private ColorizedFlatTextureShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm(double r, double g, double b) {
			return new DeferredProgram(VERTEX_SHADER,
				    "  vec4 color = texture2D(" + SamplerParameter.TEXTURE.getName() +
		            ", vCoord);\n" +
				    "  gl_FragColor = vec4(color.x * " + r + 
				    ", color.y * " + g + ", color.z * " + b + 
				    ", color.w);",
				    SHADER_PARAMETERS);
	}
}
