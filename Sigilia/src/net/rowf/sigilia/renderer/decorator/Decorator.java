package net.rowf.sigilia.renderer.decorator;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Representation;

public interface Decorator {
	public Representation getRepresentationFor(Entity entity);
}
