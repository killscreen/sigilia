package net.rowf.sigilia.renderer.decorator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.visual.Representation;

public class DecorationEngine implements Engine {
	private Map<String, Decorator> decorators;
	
	public DecorationEngine(Map<String, Decorator> decorators) {
		this.decorators = decorators;
	}
	
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (Entity entity : entities) {
			Representation r = entity.getComponent(Representation.class);
			if (r == null) {
				Name n = entity.getComponent(Name.class);
				if (n != null) {
					Decorator d = decorators.get(n.get());
					if (d != null) {
						entity.setComponent(Representation.class, d.getRepresentationFor(entity));
					}
				}
			}
		}
	}

}
