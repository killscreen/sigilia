package net.rowf.sigilia.renderer.decorator;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.PositionedRepresentation;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.texture.Texture;

public class DeferredRepresentation extends PositionedRepresentation implements Decorator<Representation> {
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
	public Renderable makeRenderable(Entity e, float[] mat) {		
		return new DeferredRenderable(program, texture, model, mat);
	}

	@Override
	public Representation getDecoration(Entity entity) {
		return this;
	}
}
