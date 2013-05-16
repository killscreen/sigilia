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
public class Trailboard implements Model {
	public static final Trailboard UNIT = new Trailboard(1f);
	
	private static final ShortBuffer order = 
			BufferUtil.toBuffer(new short[] { 
					0,  1, 3, 3, 2, 0, 
					4,  5, 7, 7, 6, 4,
					8,  9,11,11,10, 8,
					12,13,15,15,14,12,
					16,17,19,19,18,16
			} );
	private static final FloatBuffer texCoords = 
			BufferUtil.toBuffer(new float[] {
					.99f, 0f, .0f, 0f, .99f, .99f, 0f, .99f,
					.99f, 0f, .0f, 0f, .99f, .99f, 0f, .99f,
					.99f, 0f, .0f, 0f, .99f, .99f, 0f, .99f,
					.99f, 0f, .0f, 0f, .99f, .99f, 0f, .99f,
					.99f, 0f, .0f, 0f, .99f, .99f, 0f, .99f					
					} );
	private FloatBuffer vertexes;
	
	public Trailboard(float scale) {
		this(scale, scale);
	}
	
	public Trailboard(float width, float height) {
		float w = width / 2;
		float h = height / 2;
		vertexes = BufferUtil.toBuffer(new float[] { 
				-w,h,0, w,h,0, -w,-h,0, w,-h,0,
				-w,h,1, w,h,1, -w,-h,1, w,-h,1,
				-w,h,2, w,h,2, -w,-h,2, w,-h,2,
				-w,h,3, w,h,3, -w,-h,3, w,-h,3,
				-w,h,4, w,h,4, -w,-h,4, w,-h,4
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
		return 10;
	}

}
