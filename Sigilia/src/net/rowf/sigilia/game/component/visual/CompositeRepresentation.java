package net.rowf.sigilia.game.component.visual;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;

public class CompositeRepresentation implements Representation, Decorator<Representation> {
	private Representation[] representations;
	
	public CompositeRepresentation(Representation... representations) {
		this.representations = representations; 
	}

	@Override
	public Renderable makeRenderable(Entity e) {
		Renderable[] renderables = new Renderable[representations.length];
		for (int i = 0; i < representations.length; i++) {
			renderables[i] = representations[i].makeRenderable(e);
		}
		return new CompositeRenderable(renderables);
	}
	
	private static class CompositeRenderable implements Renderable {
		private Renderable[] renderables;
		public CompositeRenderable(Renderable[] renderables) {
			super();
			this.renderables = renderables;
		}		@Override
		public void render(float[] viewMatrix) {
			for (Renderable r : renderables) {
				r.render(viewMatrix);
			}
		}
	}

	@Override
	public Representation getDecoration(Entity entity) {
		return this;
	}
	
}
