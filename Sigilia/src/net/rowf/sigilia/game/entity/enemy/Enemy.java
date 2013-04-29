package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.entity.NamedPrototype;

public abstract class Enemy extends NamedPrototype {
	private static final Size DEFAULT_SIZE = new Size(1,2,1);
	
	@Override
	public void apply(Entity e) {
		// TODO Add normal collision stuffs
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Health.class, new Health(3f));
		e.setComponent(Animation.class, new Animation());
		e.setComponent(Size.class, DEFAULT_SIZE);
		e.setComponent(Liveness.class, Liveness.ALIVE);
		super.apply(e);
	}
	
}
