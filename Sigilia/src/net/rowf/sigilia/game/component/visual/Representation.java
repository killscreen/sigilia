package net.rowf.sigilia.game.component.visual;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;

public interface Representation extends Component {
	public Renderable makeRenderable(Entity e);
}
