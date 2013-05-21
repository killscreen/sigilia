package net.rowf.sigilia.renderer.decorator;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PositionedRepresentation;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.renderer.AnimatedRenderable.DeferredAnimatedRenderable;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.model.AnimatedModel;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.texture.Texture;

/**
 * A representation which exhibits non-key frame animated behavior.
 * 
 * @author woeltjen
 *
 */
public class PeriodicRepresentation extends PositionedRepresentation implements Decorator<Representation> {
	private Deferred<ParameterizedProgram>     program;
	private Deferred<Texture>     texture;
	private AnimatedModel         model;
	
	public PeriodicRepresentation(Deferred<ParameterizedProgram> program,
			Deferred<Texture> texture, Model model) {
		super();
		this.program = program;
		this.texture = texture;
		this.model = new AnimatedModelWrapper(model);
	}

	@Override
	public Renderable makeRenderable(Entity e, float[] mat) {
		Animation anim = e.getComponent(Animation.class);
		if (anim != null) {
			return new DeferredAnimatedRenderable(program, model, mat, texture, anim.getProgress());
		} else {
			return null;
		}
	}

	@Override
	public Representation getDecoration(Entity entity) {
		return this;
	}
	
	private static class AnimatedModelWrapper implements AnimatedModel {
		private Model model;
		
		public AnimatedModelWrapper(Model model) {
			super();
			this.model = model;
		}

		

		public FloatBuffer getVertexes() {
			return model.getVertexes();
		}


		public ShortBuffer getDrawingOrder() {
			return model.getDrawingOrder();
		}


		public FloatBuffer getTexCoords() {
			return model.getTexCoords();
		}

		public int getTriangleCount() {
			return model.getTriangleCount();
		}

		@Override
		public FloatBuffer getNextVertexes() {
			return model.getVertexes();
		}
		
	}
}
