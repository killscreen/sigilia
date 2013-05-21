package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.Player;
import net.rowf.sigilia.input.gesture.DeltaSequence;
import net.rowf.sigilia.input.gesture.StaticDeltaSequence;

/**
 * The player's 'fire' magic. Deals damage of the fire type, so it can be 
 * used to destroy trees and ice shields.
 * 
 * @author woeltjen
 *
 */
public class FireWeapon extends Weapon {
	
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Liveness.class, Liveness.ALIVE);
		e.setComponent(Animation.class, new PeriodicAnimation(getDelay()/2f));
		e.setComponent(Impact.class, IMPACT);
		e.setComponent(PhysicalType.class, PhysicalType.FIRE);
		e.setComponent(Animation.class, new PeriodicAnimation(3f, false));
	}

	@Override
	public float getDelay() {
		return 0.5f;
	}
	
	@Override
	protected float velocity() {
		return 2f;
	}

	@Override
	public DeltaSequence getSigil() {
		return StaticDeltaSequence.FIRE;
	}

	private static final Impact IMPACT = new ProjectileImpact(7.5f, Player.class);
}
