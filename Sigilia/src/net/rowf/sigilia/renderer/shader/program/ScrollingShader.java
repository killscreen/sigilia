package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;

public class ScrollingShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
			"  vCoord = vec2(abs("+ VectorParameter.TEXTURE_COORD.getName() + ".x)," +
			                "abs("+ VectorParameter.TEXTURE_COORD.getName() + ".y));" +
		    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
		                         VectorParameter.VERTEX.getName() + ";";
		private static final String FRAGMENT_SHADER =
				"  float t = " + ScalarParameter.TRANSITION.getName() +";\n" +
				"  float x = vCoord.x + t;\n" +
		        "  if (x > 1.0) { x = x - 1.0; }\n" +
				"  gl_FragColor = texture2D(" +SamplerParameter.TEXTURE.getName() +
	                                  ", vec2(x, vCoord.y));\n";
			    //"  gl_FragColor = vec4(a.x*aS+b.x*bS, a.y*aS+b.y*bS, a.z*aS+b.z*bS, a.w*aS+b.w*bS );\n";
		
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
			{MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VectorParameter.VERTEX, VectorParameter.TEXTURE_COORD,
		     ScalarParameter.TRANSITION,
			 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public ScrollingShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
