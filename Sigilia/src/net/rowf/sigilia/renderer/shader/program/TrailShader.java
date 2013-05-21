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

/**
 * Shader used for drawing fireballs; animated non-zero Z positions, 
 * fades to grey/transparent.
 * @author woeltjen
 *
 */
public class TrailShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
			"  vCoord = vec2(abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
			                "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
			"  vec4 vert = " + VertexParameter.VERTEX.getName() + ";\n" +
			"  vec3 delt = " + VectorParameter.DIRECTION.getName() + ";\n" +
			"  float t = " + ScalarParameter.TRANSITION.getName() +";\n" +
			"  float n = 0.0;\n" +
			"  if (vert.z > 0.0) { n = (vert.z - 1.0 + t) / 4.0;}\n" +
			"  vert = vec4(vert.x*(1.0-n*0.33) + delt.x * n, " +
			              "vert.y*(1.0-n*0.50) + delt.y * n, " +
			              "0.0    + delt.z * n, " +
			              "vert.w);\n" +
  			"  vTrail = n;\n" +			              
		    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * vert;\n";
		private static final String FRAGMENT_SHADER =
				"  float t = " + ScalarParameter.TRANSITION.getName() +";\n" +
		        "  float n = vTrail;\n" +
		        
				"  int  aFrame = int(t * 4.0);\n" +
				"  int  bFrame = aFrame + 1; if (bFrame > 4) { bFrame = bFrame - 4; } \n" +
				"  vec2 a = vec2(vCoord.x/2.0 + float(aFrame-(aFrame/2)*2) * 0.5,"    +
				                "vCoord.y/2.0 + float(aFrame/2) * 0.5);\n" +
				"  vec2 b = vec2(vCoord.x/2.0 + float(bFrame-(bFrame/2)*2) * 0.5,"    +
					            "vCoord.y/2.0 + float(bFrame/2) * 0.5);\n" +
                "  float ab = ((t * 4.0) - float(aFrame));\n" +

                "  vec4 aColor = texture2D(" + SamplerParameter.TEXTURE.getName() + ",a);\n" +
				"  vec4 bColor = texture2D(" + SamplerParameter.TEXTURE.getName() + ",b);\n" +
				"  vec4 abColor = vec4((aColor.x * (1.0-ab) + bColor.x * ab) * (1.0-n) + 0.66*n, " +
				                      "(aColor.y * (1.0-ab) + bColor.y * ab) * (1.0-n) + 0.66*n, " +
				                      "(aColor.z * (1.0-ab) + bColor.z * ab) * (1.0-n) + 0.66*n, " +
				                      "(aColor.w * (1.0-ab) + bColor.w * ab) * (1.0-n));\n" +
				"  gl_FragColor = abColor;\n";
		
	private static final ShaderParameter<?>[] SHADER_PARAMETERS =
			{MatrixParameter.TRANSFORMATION, 
		     SamplerParameter.TEXTURE, 
		     SamplerParameter.NEXT_TEXTURE,
		     VertexParameter.VERTEX, 
		     VertexParameter.TEXTURE_COORD,
		     VectorParameter.DIRECTION,
		     ScalarParameter.TRANSITION,
			 new InternalShaderParameter("varying lowp vec2", "vCoord"),
			 new InternalShaderParameter("varying lowp float", "vTrail")
			};
	
	public TrailShader() {	
		super( VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);		
	}
	
	public static DeferredProgram deferredForm() {
			return new DeferredProgram(VERTEX_SHADER, FRAGMENT_SHADER, SHADER_PARAMETERS);
	}
}
