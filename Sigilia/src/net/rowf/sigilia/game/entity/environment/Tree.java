package net.rowf.sigilia.game.entity.environment;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.entity.NamedPrototype;

public class Tree extends NamedPrototype {
	@Override
	protected void applyAdditional(Entity e) {
		// TODO: Add health, fire
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Size.class, new Size(1f, 6f, 1f));
	}
}
