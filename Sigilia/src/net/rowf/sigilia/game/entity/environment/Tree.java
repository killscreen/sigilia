package net.rowf.sigilia.game.entity.environment;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.physical.ModifiedHealth;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.entity.NamedPrototype;

public class Tree extends NamedPrototype {
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Size.class, new Size(2f, 6f, 1f));
		e.setComponent(Health.class, new ModifiedHealth(1f, 0f, 1f, PhysicalType.FIRE));
		e.setComponent(Liveness.class, Liveness.ALIVE);
	}
}
