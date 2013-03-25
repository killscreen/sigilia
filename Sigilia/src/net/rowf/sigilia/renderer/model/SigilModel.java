package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.util.FloatMath;

import net.rowf.sigilia.input.gesture.DeltaSequence;
import net.rowf.sigilia.input.gesture.DirectionSet.Direction;
import net.rowf.sigilia.util.BufferUtil;

public class SigilModel implements Model {
	private final ShortBuffer order;
	private final FloatBuffer vertexes;	
	private final FloatBuffer texCoords;
	private final int count;
	
	public SigilModel(DeltaSequence sequence, int samples) {
		Direction[] d = sequence.getSampledDelta(samples);
		
		this.count = (samples - 1) * 2;

		short[] o = new short[count * 3];		
				
		float x = 0;
		float y = 0;
		float[] v = new float[samples * 2];
		
		for (short i = 0; i < d.length; i++) {
			float scale = FloatMath.sin((float) (Math.PI) * (float) i / (float) d.length);
			
			v[i*4+0] = x - d[i].getY() * scale;
			v[i*4+1] = y + d[i].getX() * scale;
			v[i*4+2] = x + d[i].getY() * scale;
			v[i*4+3] = y - d[i].getX() * scale;
			
			if (i < d.length - 1) {
				o[i*6+0] = (short) (i * 2 + 0);
				o[i*6+1] = (short) (i * 2 + 1);
				o[i*6+2] = (short) (i * 2 + 2);
				o[i*6+3] = (short) (i * 2 + 1);
				o[i*6+4] = (short) (i * 2 + 3);
				o[i*6+5] = (short) (i * 2 + 2);
			}
			
			// TODO: Normalize?
			
			
			x += d[i].getX();
			y += d[i].getY();
		}
		
		normalize(v);
		
		vertexes = BufferUtil.toBuffer(v);
		order = BufferUtil.toBuffer(o);

		
		texCoords = null; // Not used!
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
		return count;
	}

	// Fit the list of vecs in -1,-1 to 1,1
	private static void normalize(float[] vec) {
		float minX = 0;
		float maxX = 0;
		float minY = 0;
		float maxY = 0;
		for (int i = 0; i < vec.length; i+=2) {
			minX = Math.min(minX, vec[i]);
			maxX = Math.max(maxX, vec[i]);
			minY = Math.min(minY, vec[i+1]);
			maxY = Math.max(maxY, vec[i+1]);
		}
		float sz = Math.max(maxY - minY, maxX - minX) * 0.5f; 
		
		for (int i = 0; i < vec.length; i+=2) {
			vec[i  ] = (vec[i  ] - minX) / sz - 1f;
			vec[i+1] = (vec[i+1] - minY) / sz - 1f;
		}
	}
}
