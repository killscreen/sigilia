package net.rowf.sigilia.renderer.model;

import java.nio.FloatBuffer;

public interface AnimatedModel extends Model {
	public FloatBuffer getNextVertexes();
}
