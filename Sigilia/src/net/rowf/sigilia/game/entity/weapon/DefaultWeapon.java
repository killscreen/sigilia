package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.ConstantMotion;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.Player;
import net.rowf.sigilia.geometry.Vector;
import net.rowf.sigilia.input.gesture.DeltaSequence;

public class DefaultWeapon extends Weapon {
	private static final Motion MOTION = new ConstantMotion(new Vector(0,0,15f));
	
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Liveness.class, Liveness.ALIVE);
		e.setComponent(Impact.class, IMPACT);
		e.setComponent(PhysicalType.class, PhysicalType.ENERGY);
		e.setComponent(Animation.class, new PeriodicAnimation(3f, false));
	}

	@Override
	protected float velocity() {
		return 3f;
	}

	@Override
	public DeltaSequence getSigil() {
		return null;
	}
	
	private static final Impact IMPACT = new ProjectileImpact(1f, Player.class);
}
