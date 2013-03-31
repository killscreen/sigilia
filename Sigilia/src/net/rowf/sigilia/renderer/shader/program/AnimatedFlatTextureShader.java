package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;

public class AnimatedFlatTextureShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
		"  vCoord = vec2(abs("+ VectorParameter.TEXTURE_COORD.getName() + ".x)," +
		                "abs("+ VectorParameter.TEXTURE_COORD.getName() + ".y));" +
//		"  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
//                        VectorParameter.VERTEX.getName() + ";";
        "  vec4 a = " + VectorParameter.VERTEX.getName() + ";\n" +
        "  vec4 b = " + VectorParameter.SUBSEQUENT_VERTEX.getName() + ";\n" +
        "  float t = " + ScalarParameter.TRANSITION.getName() + ";\n" +
	    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
        "vec4(a.x*(1.0-t)+ t*b.x,a.y*(1.0-t) + t*b.y,a.z*(1.0-t) + t*b.z,a.w);";
//	                         "vec4(a.x * (1-t) + b.x * t,\n" +
//	                         "     a.y * (1-t) + b.y * t,\n" +
//	                         "     a.z * (1-t) + b.z * t,\n" +
//	                         "     a.w);\n";
	private static final String FRAGMENT_SHADER =
		    "  gl_FragColor = texture2D(" + SamplerParameter.TEXTURE.getName() +
            ", vCoord);";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VectorParameter.VERTEX, VectorParameter.TEXTURE_COORD,
		 VectorParameter.SUBSEQUENT_VERTEX, ScalarParameter.TRANSITION,
		 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public AnimatedFlatTextureShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
