package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.physical.ConstantMotion;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.entity.NamedPrototype;
import net.rowf.sigilia.geometry.Vector;

public class DefaultWeapon extends NamedPrototype {
	private static final Motion MOTION = new ConstantMotion(new Vector(0,0,1.5f));
	
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Motion.class, MOTION);
	}
}
