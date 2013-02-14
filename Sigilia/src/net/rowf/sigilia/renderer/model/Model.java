package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public interface Model {
	public FloatBuffer getVertexes();
	public ShortBuffer getDrawingOrder();
	public FloatBuffer getTexCoords();
	public int         getTriangleCount();
}
