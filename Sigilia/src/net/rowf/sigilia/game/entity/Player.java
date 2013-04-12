package net.rowf.sigilia.game.entity;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Boundary;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.visual.HealthBarRepresentation;
import net.rowf.sigilia.game.component.visual.Representation;

public class Player extends NamedPrototype {
	private static final BoundingBox BOUND = new BoundingBox(0, 0, 1, 2);
	
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Health.class, new Health(100000f));
		e.setComponent(Boundary.class, BOUND);
		e.setComponent(Position.class, BOUND);
	}

}
