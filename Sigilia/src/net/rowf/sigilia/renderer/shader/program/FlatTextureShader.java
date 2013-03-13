package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;

public class FlatTextureShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
		"  vCoord = vec2(abs("+ VectorParameter.TEXTURE_COORD.getName() + ".x)," +
		                "abs("+ VectorParameter.TEXTURE_COORD.getName() + ".y));" +
	    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
	                         VectorParameter.VERTEX.getName() + ";";
	private static final String FRAGMENT_SHADER =
		    "  gl_FragColor = texture2D(" + SamplerParameter.TEXTURE.getName() +
            ", vCoord);";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VectorParameter.VERTEX, VectorParameter.TEXTURE_COORD, 
		 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public FlatTextureShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static final DeferredProgram DEFERRED_FORM =
			new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
}
