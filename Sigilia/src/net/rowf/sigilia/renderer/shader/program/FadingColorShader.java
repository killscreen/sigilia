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

public class FadingColorShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
	    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
	                         VertexParameter.VERTEX.getName() + ";";
	private static final String FRAGMENT_SHADER =
			"  float t = " + ScalarParameter.TRANSITION.getName() +";\n" +			
			"  gl_FragColor = vec4( " +
		                        VectorParameter.COLOR.getName() + ".x, " +
		                        VectorParameter.COLOR.getName() + ".y, " +
		                        VectorParameter.COLOR.getName() + ".z, " +
		                        "t);\n";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{MatrixParameter.TRANSFORMATION, 
		 VertexParameter.VERTEX,
		 VectorParameter.COLOR,
		 ScalarParameter.TRANSITION
		 //VertexParameter.TEXTURE_COORD, 
		 //new InternalShaderParameter("varying lowp vec2", "vCoord")
		 };
	
	public FadingColorShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
