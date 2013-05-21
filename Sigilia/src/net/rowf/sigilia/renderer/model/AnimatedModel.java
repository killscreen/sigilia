package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;

/**
 * A model with both a current and a subsequent set of vertexes, 
 * as used in interpolated key frame sequences.
 * 
 * @author woeltjen
 *
 */
public interface AnimatedModel extends Model {
	public FloatBuffer getNextVertexes();
}
