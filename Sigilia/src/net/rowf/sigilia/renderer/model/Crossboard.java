package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


/**
 * A crossboard describes two intersecting quads (in a "cross" or "t" shape),
 * one on the XZ plane, the other on the YZ plane
 * 
 * Used as a model for arrows.
 * 
 * @author woeltjen
 *
 */
public class Crossboard implements Model {
	public static final Crossboard UNIT = new Crossboard(1f);
	
	private static final ShortBuffer order = 
			BufferUtil.toBuffer(new short[] { 0, 1, 3, 3, 2, 0,   4, 5, 7, 7, 6, 4 } );
	private static final FloatBuffer texCoords = 
			BufferUtil.toBuffer(new float[] { 
					.99f, 0f, .0f, 0f, .99f, .99f, 0f, .99f,  
					.99f, 0f, .0f, 0f, .99f, .99f, 0f, .99f,
					} );
	private FloatBuffer vertexes;
	
	
	public Crossboard(float width) {
		float w = width / 2;
		vertexes = BufferUtil.toBuffer(new float[] { 
				 -w,0,-w,  w,0,-w,  -w,0,w, w,0,w,
	 			  0,-w,-w,  0,w,-w,  0,-w,w, 0,w,w
		}); 				
	}
	
	@Override
	public FloatBuffer getVertexes() {
		return vertexes;
	}

	@Override
	public ShortBuffer getDrawingOrder() {
		return order;
	}

	@Override
	public FloatBuffer getTexCoords() {
		return texCoords;
	}

	@Override
	public int getTriangleCount() {
		return 4;
	}

}
