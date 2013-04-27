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

public class SigilShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
			"  vCoord = vec2(abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
			                "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
		    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
		                         VertexParameter.VERTEX.getName() + ";";
		private static final String FRAGMENT_SHADER =
				"  float t = " + ScalarParameter.TRANSITION.getName() +";\n" +			
		        "  vec4  sample = texture2D(" +SamplerParameter.TEXTURE.getName() +
	                                  ", vCoord);\n" +
				"  float a = sample.w;\n" +
	            "  if (sample.x > t+t) { a = a * t * t; }\n" +
				"  if (t+t > 1.0) { a = a * (2.0-t-t); }\n" +
				"  gl_FragColor = vec4( " +
	                        VectorParameter.COLOR.getName() + ".x, " +
	                        VectorParameter.COLOR.getName() + ".y, " +
	                        VectorParameter.COLOR.getName() + ".z, " +
	                        "a);\n";
			    //"  gl_FragColor = vec4(a.x*aS+b.x*bS, a.y*aS+b.y*bS, a.z*aS+b.z*bS, a.w*aS+b.w*bS );\n";
		
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
			{MatrixParameter.TRANSFORMATION, 
			 SamplerParameter.TEXTURE, 
		     VertexParameter.VERTEX, 
		     VertexParameter.TEXTURE_COORD,
		     VectorParameter.COLOR,
		     ScalarParameter.TRANSITION,
			 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public SigilShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
