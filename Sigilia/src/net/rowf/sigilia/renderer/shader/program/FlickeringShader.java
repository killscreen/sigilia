package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VertexParameter;

public class FlickeringShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
			"  vCoord = vec2(abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
			                "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
			"  float x = " + VertexParameter.VERTEX.getName() + ".x;\n" +		
			"  float y = " + VertexParameter.VERTEX.getName() + ".y;\n" +
	        "  float z = " + VertexParameter.VERTEX.getName() + ".z;\n" +
	        "  float w = " + VertexParameter.VERTEX.getName() + ".w;\n" +
	        "  vec4  p = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
                           "vec4(x,y,z,w);" +
	        "  if (z < 0.0) { p = vec4(x,1,0,w); }\n" +
		    "  gl_Position = p;\n";
		private static final String FRAGMENT_SHADER =
				"  vec4 a = texture2D(" +SamplerParameter.TEXTURE.getName() +
	                                  ", vCoord);\n" +
	            "  vec4 b = texture2D(" +SamplerParameter.TEXTURE.getName() +
	                                  ", vec2(0.9999 - vCoord.x, vCoord.y));\n" +
	            "  float t = " + ScalarParameter.TRANSITION.getName() +";\n" +
	            "  gl_FragColor = vec4(a.x*(1.0-t)+ t*b.x,a.y*(1.0-t) + t*b.y,a.z*(1.0-t) + t*b.z,a.w);";
			    //"  gl_FragColor = vec4(a.x*aS+b.x*bS, a.y*aS+b.y*bS, a.z*aS+b.z*bS, a.w*aS+b.w*bS );\n";
		
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
			{MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VertexParameter.VERTEX, VertexParameter.TEXTURE_COORD,
		     ScalarParameter.TRANSITION,
			 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public FlickeringShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
