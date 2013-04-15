package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import net.rowf.sigilia.util.BufferUtil;

public class Backdrop implements Model {
	public static final float SIZE = 15f;
	
	private static final ShortBuffer order =
			BufferUtil.toBuffer(new short[] { 
					0,1,3, 3,2,0, 
					2,3,5, 5,4,2, 
					4,5,7, 7,6,4 });
	private static final FloatBuffer vertexes =
			BufferUtil.toBuffer(new float[] {
					-SIZE,SIZE,  1,   SIZE,SIZE,1,
					-SIZE, 0,  1,   SIZE, 0,1,
					-SIZE,-1,  0,   SIZE,-1,0,
					-SIZE,-2,-SIZE,   SIZE,-2,-SIZE
//					-16,-3,-32,   16,-3,-32
			});
	private static final FloatBuffer texCoords =
			BufferUtil.toBuffer(new float[] {
					0,0,     0.98f,0,
					0,0.45f,  0.98f,0.45f,
					0,0.55f,  0.98f,0.55f,
					0,.99f,  0.98f,0.99f
			});
	
	
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
		// TODO Auto-generated method stub
		return 6;
	}

}
