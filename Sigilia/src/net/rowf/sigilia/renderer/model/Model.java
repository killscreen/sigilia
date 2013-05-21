package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Describes the geometry of an object in three-dimensional space, in 
 * a format appropriate for usage in an OpenGL ES context
 * 
 * @author woeltjen
 *
 */
public interface Model {
	public FloatBuffer getVertexes();
	public ShortBuffer getDrawingOrder();
	public FloatBuffer getTexCoords();
	public int         getTriangleCount();
}
