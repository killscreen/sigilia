package net.rowf.sigilia.game.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.geometry.Vector;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Camera;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.PerspectiveRenderer.RenderableProvider;
import android.util.Log;

public class RenderingEngine implements Engine, RenderableProvider {
	private static final float MAXIMUM_FPS = 120f; 
	
	private float lastTimestamp;
	private Iterable<Renderable> latest = Collections.emptyList();
	
	private static final Comparator<Entity> Z_POSITION_COMPARATOR = new Comparator<Entity>() {
		@Override
		public int compare(Entity lhs, Entity rhs) {
			Vector l = lhs.getComponent(Position.class);
			Vector r = rhs.getComponent(Position.class);
			
			/* First, omit cases where an entity has no position. */
			if (l == null && r == null) return 0;
			if (l == null && r != null) return -1;
			if (l != null && r == null) return 1;
			
			return (int) -Math.signum(l.getZ() - r.getZ());
		}
	};
	
	@Override
	public Iterable<Renderable> getOrderedRenderables(Camera camera) {
		return latest; //TODO: Use camera?
	}

	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		// Don't bother rendering more than the maximum FPS
		// Note that this is different from PeriodicEngine; frames may be skipped
		if (lastTimestamp + (1/MAXIMUM_FPS) > timeStamp) {
			return;
		}
		
		lastTimestamp = timeStamp;

		// TODO: Consider a double-buffer of these lists
		//       (new ArrayList may mean a lot of allocations)
		List<Renderable> newRender = new ArrayList<Renderable>(entities.size());

		Collections.sort(entities, Z_POSITION_COMPARATOR);
		for (Entity entity : entities) {
			Representation r = entity.getComponent(Representation.class);
			if (r != null) {
				newRender.add(r.makeRenderable(entity));
			}
		}
		
		latest = newRender;
	}
}
