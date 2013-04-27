package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import net.rowf.sigilia.util.BufferUtil;

/**
 * For simplicity, it is assumed that all billboard are parallel to the 
 * XY-plane, and facing in the negative-Z direction (effectively, camera 
 * is assumed to face positive-Z) 
 * 
 * @author woeltjen
 *
 */
public class Billboard implements Model {
	public static final Billboard UNIT = new Billboard(1f);
	
	private static final ShortBuffer order = 
			BufferUtil.toBuffer(new short[] { 0, 1, 3, 3, 2, 0 } );
	private static final FloatBuffer texCoords = 
			BufferUtil.toBuffer(new float[] { .99f, 0f, .0f, 0f, .99f, .99f, 0f, .99f } );
	private FloatBuffer vertexes;
	
	public Billboard(float scale) {
		this(scale, scale);
	}
	
	public Billboard(float width, float height) {
		float w = width / 2;
		float h = height / 2;
		vertexes = BufferUtil.toBuffer(new float[] { 
				-w,h,0, w,h,0, -w,-h,0, w,-h,0 
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
		return 2;
	}

}
