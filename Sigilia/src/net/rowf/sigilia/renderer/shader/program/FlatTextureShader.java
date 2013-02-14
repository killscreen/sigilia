package net.rowf.sigilia.renderer.shader.program;

import net.rowf.sigilia.renderer.shader.InternalShaderParameter;
import net.rowf.sigilia.renderer.shader.MatrixParameter;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.SamplerParameter;
import net.rowf.sigilia.renderer.shader.VectorParameter;

public class FlatTextureShader extends ParameterizedProgram {
	public FlatTextureShader() {
		super(  // VERTEX SHADER
			    "  vCoord = vec2(abs("+ VectorParameter.TEXTURE_COORD.getName() + ".x)," +
				                "abs("+ VectorParameter.TEXTURE_COORD.getName() + ".y));" +
			    "  gl_Position = " + MatrixParameter.TRANSFORMATION.getName() + " * " +
			                         VectorParameter.VERTEX.getName() + ";",
			    
			    // FRAGMENT SHADER
			    "  gl_FragColor = texture2D(" + SamplerParameter.TEXTURE.getName() +
			                                ", vCoord);",
			 MatrixParameter.TRANSFORMATION, SamplerParameter.TEXTURE, VectorParameter.VERTEX, VectorParameter.TEXTURE_COORD, 
			 new InternalShaderParameter("varying lowp vec2", "vCoord"));
	}
}
