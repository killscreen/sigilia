package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;
import android.util.Log;

public class HealthBarShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER =
			"  float x = 0.9 + 0.05 * " + VectorParameter.VERTEX.getName() + ".x;\n" +
			"  float y = 1.5 * " + VectorParameter.VERTEX.getName() + ".y;\n" +
			"  float z = " + VectorParameter.VERTEX.getName() + ".z;\n" +
			"  vCoord = vec2(/*???*/abs("+ VectorParameter.TEXTURE_COORD.getName() + ".x)," +
			                "abs("+ VectorParameter.TEXTURE_COORD.getName() + ".y));" +
		    "  gl_Position = vec4(x,y,z,1.0);";
//	private static final String FRAGMENT_SHADER =
//			"  vec4 color = vec4(1,0,1,0.5);\n" +
//	        //"  if (yCoord > " + ScalarParameter.TRANSITION.getName() + ") color.w = 0.25;\n" +
//		    "  gl_FragColor = color";
//	
	
	private static final String FRAGMENT_SHADER =
		    "  vec4 color = texture2D(" + SamplerParameter.TEXTURE.getName() +
            ", vCoord);" +
	        "  color.y = 0.5;" +
	        "  gl_FragColor = vec4(1.0,0.0,0.0,0.95);" ;//color;\n";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VectorParameter.VERTEX, VectorParameter.TEXTURE_COORD, 
		 new InternalShaderParameter("varying lowp vec2", "vCoord")};
	
	public HealthBarShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
		Log.e("HEALTH", VERTEX_SHADER);
		Log.e("HEALTH", FRAGMENT_SHADER);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
