package net.rowf.sigilia.game.component.visual;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;

/**
 * A Representation object describes the appearance of an entity. It 
 * produces Renderable objects that can be drawn by the actual renderer. 
 * Because the renderer happens on a separate thread from game logic, 
 * these should be immutable "snapshots" of the entity as it appeared 
 * when its Representation was observed.
 * 
 * @author woeltjen
 *
 */
public interface Representation extends Component {
	public Renderable makeRenderable(Entity e);
}
