package net.rowf.sigilia.game.entity.environment;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.entity.NamedPrototype;

public class Column extends NamedPrototype {
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Size.class, new Size(2f, 4f, 2f));
	}
}
