package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.ShaderParameter;
import net.rowf.sigilia.renderer.shader.VertexParameter;

public class RotatingSparkleShader extends ParameterizedProgram {
	private static final InternalShaderParameter UP    = new InternalShaderParameter("varying lowp vec2", "vUp");
	private static final InternalShaderParameter RIGHT = new InternalShaderParameter("varying lowp vec2", "vRight");
	
	private static final InternalShaderParameter VARYING_COORD = 
			new InternalShaderParameter("varying lowp vec2", "vCoord");
	
	private static final String VERTEX_SHADER = 
		"  " + VARYING_COORD.getName() + 
		     " = vec2(abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
		             "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
		"  float r = (" + ScalarParameter.SECONDS.getName() + " / " 
		                + ScalarParameter.SPEED.getName() + ") * 2 * 3.14159265" +
		"  mat4 rot = mat4(" +
		                   " cos(r) , -sin(r) , 0 , 0," +
		                   " sin    , cos(2)  , 0 , 0," +
		                   " 0      , 0       , 1 , 0," +
		                   " 0      , 0       , 0 , 1 " +
		"                 );" +
		   UP.getName()    + " = (rot * vec4(0, 1, 0, 1)).xy;" +
		   RIGHT.getName() + " = (rot * vec4(1, 0, 0, 1)).xy;" + 
	    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
						     "(rot * " +
	                         VertexParameter.VERTEX.getName() + ");";
	private static final String FRAGMENT_SHADER =
			"  vec4 fx = " + generateFragmentShaderSummation(SamplerParameter.EFFECT, VARYING_COORD, UP, RIGHT) + ";" +					               
		    "  gl_FragColor = vec4(0,0,0,0);";
	
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
		{ MatrixParameter.TRANSFORMATION, 
		  SamplerParameter.TEXTURE, 
		  VertexParameter.VERTEX, 
		  VertexParameter.TEXTURE_COORD,
		  ScalarParameter.SECONDS,
		  ScalarParameter.SPEED,
		 };
	
	public RotatingSparkleShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
	
	/**
	 * Generate GLSL code for a 5-by-5 kernel blur filter, biased toward 
	 * pixels along UP and RIGHT vectors
	 *          2
	 *       1  4  1
	 *    2  4 16  4  2
	 *       1  4  1
	 *          2
	 *          
	 * This is used to turn "dots" from a source texture (usually EFFECT) into "stars"
	 * 
	 * @return
	 */
	private static String generateFragmentShaderSummation(String sampler, String coord, String up, String right) {
		float[][] scale = {
				{0, 0, 2, 0, 0 },
				{0, 1, 4, 1, 0 },
				{2, 4,16, 4, 2 },
				{0, 1, 4, 1, 0 },
				{0, 0, 2, 0, 0 }
		};
		
		float epsilon = 1 / 255f;
		float total = 0f; 
		
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				total += scale[x][y]; 
			}
		}
		
		String pattern = "texture2D( %s , %s + (%s * %d) + (%s * %d)) * %d";
		String[] terms = new String[25];
		int tCount = 0;
		
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				float s = scale[x][y] / total;
				if (s < epsilon) {
					continue;
				}
				float u = ((float)(x-2)) * .01f;
				float v = ((float)(y-2)) * .01f;
				terms[tCount++] = String.format(pattern, sampler, coord, up, u, right, v, s);
			}
		}
		
		// Assemble code into a big summation string
		String code = "";
		for (int i = 0; i < tCount; i++) {
			code += "\t" + (code.equals("") ? "" : " + \n") + terms[i];
		}
		
		return code;
	}
	
	private static String generateFragmentShaderSummation(SamplerParameter sampler, 
			ShaderParameter<?> coord, ShaderParameter<?> up, ShaderParameter<?> right) {
		return generateFragmentShaderSummation(sampler.getName(), coord.getName(), up.getName(), right.getName());
	}
	
}
