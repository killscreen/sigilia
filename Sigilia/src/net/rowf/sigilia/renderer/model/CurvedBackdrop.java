package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import net.rowf.sigilia.util.BufferUtil;

public class CurvedBackdrop implements Model {
	private final ShortBuffer order =
			BufferUtil.toBuffer(new short[] { 
					3,9,10,    10,4,3,
					4,10,11,   11,5,4,
					5,11,8,    8,2,5,
					2,8,7,     7,1,2,
					1,7,6,     6,0,1,
					3,4,1,     1,0,3,
					4,5,2,     2,1,4,
					11,10,7,   7,8,11,
					10,9,6,    6,7,10
					});
	private final FloatBuffer vertexes;	
	private final FloatBuffer texCoords;
	
	public CurvedBackdrop(float width, float height, float depth, float soft) {
		float w = width/2;
		float h = height/2;
		float d = depth;
		float s = soft;
		
		float[] v = new float[3 * 12];
		float[] t = new float[2 * 12];
		int i = 0;
		int j = 0;
		for (int x = -1; x < 2; x += 2) {
			for (int y = -1; y < 2; y += 2) {
				for (int z = 0; z < 3; z++) {
					v[i++] =  x * (w - (z < 2 ? 0 : s));
					v[i++] = -y * (h - (z < 2 ? 0 : s));
					v[i++] = z == 1 ? (d-s) : (d * z/2);
					
					float scale = (1 - (z * 0.25f)) * 0.48f; 
					
					t[j++] = 0.49f + ((float) x) * scale;
					t[j++] = 0.49f + ((float) y) * scale;
				}
			}
		}

		
		vertexes  = BufferUtil.toBuffer(v);
		texCoords = BufferUtil.toBuffer(t);
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
		return 18;
	}

}
