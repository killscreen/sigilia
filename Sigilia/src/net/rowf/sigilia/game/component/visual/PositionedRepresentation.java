package net.rowf.sigilia.game.component.visual;

import android.opengl.Matrix;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;

public abstract class PositionedRepresentation implements Representation {

	@Override
	public Renderable makeRenderable(Entity e) {
		float[] mat = new float[16];
		Matrix.setIdentityM(mat, 0);
		Position p = e.getComponent(Position.class);
		if (p != null) {
			Matrix.translateM(mat, 0, p.getX(), p.getY(), p.getZ());
		}
		return makeRenderable(e, mat);
	}
	
	public abstract Renderable makeRenderable(Entity e, float[] transform);

}
