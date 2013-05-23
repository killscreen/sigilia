package net.rowf.sigilia.renderer.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Utility class to convert arrays to appropriate buffer types, 
 * as used commonly in the GLES20 API
 * 
 * @author woeltjen
 *
 */
public class BufferUtil {
	public static ShortBuffer toBuffer(short[] array) {
		ByteBuffer b = ByteBuffer.allocateDirect(2 * array.length);
		b.order(ByteOrder.nativeOrder());
		ShortBuffer sb = b.asShortBuffer();
		sb.put(array);
		sb.position(0);
		return sb;
	}
	
	public static FloatBuffer toBuffer(float[] array) {
		ByteBuffer b = ByteBuffer.allocateDirect(4 * array.length);
		b.order(ByteOrder.nativeOrder());
		FloatBuffer sb = b.asFloatBuffer();
		sb.put(array);
		sb.position(0);
		return sb;
	}
}
