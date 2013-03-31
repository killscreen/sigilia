package net.rowf.sigilia.renderer.decorator;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PositionedRepresentation;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.renderer.AnimatedRenderable.DeferredAnimatedRenderable;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.model.animation.KeyframeSequence;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.texture.Texture;

public class AnimatedRepresentation extends PositionedRepresentation implements Decorator<Representation> {
	private Deferred<ParameterizedProgram>     program;
	private Deferred<Texture>     texture;
	private KeyframeSequence      sequence;	
	
	public AnimatedRepresentation(Deferred<ParameterizedProgram> program,
			Deferred<Texture> texture, KeyframeSequence sequence) {
		super();
		this.program = program;
		this.texture = texture;
		this.sequence = sequence;
	}

	@Override
	public Renderable makeRenderable(Entity e, float[] mat) {
		Animation anim = e.getComponent(Animation.class);
		if (anim != null) {
			String start = anim.getCurrentFrame();
			String end   = anim.getNextFrame();
			float  prog  = anim.getProgress();
			return new DeferredAnimatedRenderable(program, sequence.getTransition(start, end), mat, texture, prog);
		} else {
			return null;
		}
	}

	@Override
	public Representation getDecoration(Entity entity) {
		return this;
	}
}
