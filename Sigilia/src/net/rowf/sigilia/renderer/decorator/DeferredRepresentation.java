package net.rowf.sigilia.renderer.decorator;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.texture.Texture;
import android.opengl.Matrix;

public class DeferredRepresentation implements Representation {
	private Deferred<ParameterizedProgram>     program;
	private Deferred<Texture>     texture;
	private Model       model;	
	
	public DeferredRepresentation(Deferred<ParameterizedProgram> program,
			Deferred<Texture> texture, Model model) {
		super();
		this.program = program;
		this.texture = texture;
		this.model = model;
	}

	@Override
	public Renderable makeRenderable(Entity e) {		
		float[] mat = new float[16];
		Matrix.setIdentityM(mat, 0);
		Position p = e.getComponent(Position.class);
		if (p != null) {
			Matrix.translateM(mat, 0, p.getX(), p.getY() + .5f, p.getZ());
		}
		return new DeferredRenderable(program, texture, model, mat);
	}

}
