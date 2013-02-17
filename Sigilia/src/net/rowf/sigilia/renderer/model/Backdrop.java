package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import net.rowf.sigilia.util.BufferUtil;

public class Backdrop implements Model {
	private static final ShortBuffer order =
			BufferUtil.toBuffer(new short[] { 0,1,3, 3,2,0, 2,3,5, 5,4,2 });
	private static final FloatBuffer vertexes =
			BufferUtil.toBuffer(new float[] {
					-1,1,0,  1,1,0,
					-1,0,0,  1,0,0,
					-1,0,-1, 1,0,-1
			});
	private static final FloatBuffer texCoords =
			BufferUtil.toBuffer(new float[] {
					0,0,     0.99f,0,
					0,0.5f,  0.99f,0.5f,
					0,.99f,  0.99f,0.99f
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
		return 4;
	}

}
