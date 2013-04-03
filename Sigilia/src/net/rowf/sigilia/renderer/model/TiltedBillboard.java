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
public class TiltedBillboard implements Model {
	public static final TiltedBillboard UNIT = new TiltedBillboard(1f, 1f, 1.5f);
	
	private static final ShortBuffer order = 
			BufferUtil.toBuffer(new short[] { 0, 1, 3, 3, 2, 0 } );
	private static final FloatBuffer texCoords = 
			BufferUtil.toBuffer(new float[] { 0f, 0f, .49f, 0f, 0f, .49f, .49f, .49f } );
	private FloatBuffer vertexes;
	
	public TiltedBillboard(float scale) {
		this(scale, scale, scale);
	}
	
	public TiltedBillboard(float width, float height, float tilt) {
		float w = width / 2;
		float h = height / 2;
		float t = tilt;
		vertexes = BufferUtil.toBuffer(new float[] { 
				-w,h,0.1f, w,h,0.1f, -w,-h,-t, w,-h,-t 
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
