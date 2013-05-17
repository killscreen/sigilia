package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VertexParameter;

public class AnimatedDeathShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
		"  vCoord = vec2(abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
		                "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
//		"  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
//                        VectorParameter.VERTEX.getName() + ";";
        "  vec4 a = " + VertexParameter.VERTEX.getName() + ";\n" +
        "  vec4 b = " + VertexParameter.SUBSEQUENT_VERTEX.getName() + ";\n" +
        "  float t = " + ScalarParameter.TRANSITION.getName() + ";\n" +
	    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
        "vec4(a.x*(1.0-t)+ t*b.x, (a.y*(1.0-t) + t*b.y) * (1.0-t) - t, (a.z*(1.0-t) + t*b.z) * t ,a.w);";
//	                         "vec4(a.x * (1-t) + b.x * t,\n" +
//	                         "     a.y * (1-t) + b.y * t,\n" +
//	                         "     a.z * (1-t) + b.z * t,\n" +
//	                         "     a.w);\n";
	private static final String FRAGMENT_SHADER =
			"  float t = " + ScalarParameter.TRANSITION.getName() + ";\n" +
		    "  vec4 color = texture2D(" + SamplerParameter.TEXTURE.getName() +
            ", vCoord);\n" +
		    "  gl_FragColor = vec4(color.x, color.y, color.z, color.w * (1.0-t));\n";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VertexParameter.VERTEX, VertexParameter.TEXTURE_COORD,
		 VertexParameter.SUBSEQUENT_VERTEX, ScalarParameter.TRANSITION,
		 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public AnimatedDeathShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
