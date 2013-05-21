package net.rowf.sigilia.game.component.visual;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.physical.Orientation;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import android.opengl.Matrix;

public abstract class PositionedRepresentation implements Representation {

	@Override
	public Renderable makeRenderable(Entity e) {
		float[] mat = new float[16];
		Matrix.setIdentityM(mat, 0);
				
		
		Position p = e.getComponent(Position.class);
		if (p != null) {
			Matrix.translateM(mat, 0, p.getX(), p.getY(), p.getZ());
		}
		
		Orientation o = e.getComponent(Orientation.class);
		if (o != null) {
			Vector v = o.getRotation(e);
			Matrix.rotateM(mat, 0, 180f * v.getY() / 3.14159f, 0, 1, 0);
			Matrix.rotateM(mat, 0, v.getX() * 180f / 3.14159f, 1, 0, 0);
		}

		
		return makeRenderable(e, mat);
	}
	
	public abstract Renderable makeRenderable(Entity e, float[] transform);

}
