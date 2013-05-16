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

public class TrailShader extends ParameterizedProgram {
	private static final String VERTEX_SHADER = 
			"  vCoord = vec2(abs("+ VertexParameter.TEXTURE_COORD.getName() + ".x)," +
			                "abs("+ VertexParameter.TEXTURE_COORD.getName() + ".y));" +
			"  vec4 vert = " + VertexParameter.VERTEX.getName() + ";\n" +
			"  vec3 delt = " + VectorParameter.DIRECTION.getName() + ";\n" +
			"  float t = " + ScalarParameter.TRANSITION.getName() +";\n" +
			"  float n = vert.z / 4.0;\n" +
			"  if (n > 0.0) { n = n + t; }\n" +
			"  if (n > 1.0) { n = n - 1.0; }\n" +
			"  vert = vec4(vert.x + delt.x * n, " +
			              "vert.y + delt.y * n, " +
			              "0.0    + delt.z * n, " +
			              "vert.w);\n" +
			"  vTrail = n;\n" +
		    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * vert;\n";
		private static final String FRAGMENT_SHADER =
				"  float t = " + ScalarParameter.TRANSITION.getName() +";\n" +
		        "  float n = vTrail;\n" +
		        "  vec2 a = vec2(vCoord.x / 2.0, vCoord.y / 2.0);\n" +
				"  vec2 b = a; vec2 c = a; vec2 d = a;\n" +
		        
				"  float ab = t;\n" +
				"  while (ab > 0.25) { ab = ab - 0.25; }\n" +
		        "  ab = (1.0 - (ab * 4.0));\n" +
		        
		        "  float cd = n;\n" +
				"  while (cd > 0.25) { cd = cd - 0.25; }\n" +
				"  cd = cd * 4.0;\n" +
				
				"  if ( (t >= 0.25 && t < 0.50) || (t >= 0.75)) " + 
				       "{ a = vec2(a.x + 0.5, a.y); }\n" +
		        "  if ( t >= 0.50 ) " + 
					       "{ a = vec2(a.x, a.y + 0.50); }\n" +
		        "  float k = t + 0.25;\n" +
		        "  if (k > 1.0) { k = k - 1.0; }\n" +
		        "  if ( (k >= 0.25 && k < 0.50) || (k >= 0.75)) " + 
						       "{ b = vec2(b.x + 0.5, b.y); }\n" +
		        "  if ( k >= 0.50 ) " + 
						       "{ b = vec2(b.x, b.y + 0.50); }\n" +

				"  if ( (t >= 0.25 && t < 0.50) || (t >= 0.75)) " + 
			            "{ c = vec2(c.x + 0.5, c.y); }\n" +
   	            "  if ( t >= 0.50 ) " + 
				       "{ c = vec2(c.x, c.y + 0.50); }\n" +
	            "  k = t + 0.25;\n" +
				"  if (k > 1.0) { k = k - 1.0; }\n" +
	            "  if ( (k >= 0.25 && k < 0.50) || (k >= 0.75)) " + 
					       "{ d = vec2(d.x + 0.5, d.y); }\n" +
	            "  if ( k >= 0.50 ) " + 
					       "{ d = vec2(d.x, d.y + 0.50); }\n" +
				
				"  vec4 aColor = texture2D(" + SamplerParameter.TEXTURE.getName() + ",a);\n" +
				"  vec4 bColor = texture2D(" + SamplerParameter.TEXTURE.getName() + ",b);\n" +
//				"  vec4 cColor = texture2D(" + SamplerParameter.TEXTURE.getName() + ",c);\n" +
//				"  vec4 dColor = texture2D(" + SamplerParameter.TEXTURE.getName() + ",d);\n" +
				"  vec4 abColor = vec4(aColor.x * (1.0-ab) + bColor.x * ab, " +
				                      "aColor.y * (1.0-ab) + bColor.y * ab, " +
				                      "aColor.z * (1.0-ab) + bColor.z * ab, " +
				                      "aColor.w * (1.0-ab) + bColor.w * ab);\n" +
//				"  vec4 cdColor = vec4(cColor.x * (1.0-cd) + dColor.x * cd, " +
//				                      "cColor.y * (1.0-cd) + dColor.y * cd, " +
//				                      "cColor.z * (1.0-cd) + dColor.z * cd, " +
//				                      "cColor.w * (1.0-cd) + dColor.w * cd);\n" +
//				"  vec4 abcdColor = vec4(abColor.x * (1.0-n) + cdColor.x * n, " +
//				                        "abColor.y * (1.0-n) + cdColor.y * n, " +
//				                        "abColor.z * (1.0-n) + cdColor.z * n, " +
//				                        "(abColor.w * (1.0-n) + cdColor.w * n) * (1.0-n));\n" +				                      				                      
				"  gl_FragColor = abColor;\n";
				//"  gl_FragColor = vec4(n,1.0,1.0,1.0-n);\n";//abcdColor;\n";
			    //"  gl_FragColor = vec4(a.x*aS+b.x*bS, a.y*aS+b.y*bS, a.z*aS+b.z*bS, a.w*aS+b.w*bS );\n";
		
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
