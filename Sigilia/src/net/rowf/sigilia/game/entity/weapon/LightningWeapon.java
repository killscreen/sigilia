package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.Player;
import net.rowf.sigilia.input.gesture.DeltaSequence;
import net.rowf.sigilia.input.gesture.StaticDeltaSequence;

public class LightningWeapon extends Weapon {
	private static final Size SIZE = new Size(1,1,3);
	
	@Override
	protected void applyAdditional(Entity e) {
		//e.setComponent(Motion.class, MOTION);
		e.setComponent(Animation.class, new PeriodicAnimation(getDelay()/2f));
		e.setComponent(Liveness.class, Liveness.ALIVE);
		e.setComponent(Impact.class, IMPACT);
		e.setComponent(PhysicalType.class, PhysicalType.ELECTRICITY);
	}

	@Override
	public float getDelay() {
		return 0.66f;
	}
	
	@Override
	protected float velocity() {
		return 8f;
	}

	@Override
	public DeltaSequence getSigil() {
		return StaticDeltaSequence.BOLT;
	}
	
	@Override
	public Size getSize() {
		return SIZE;
	}

	private static final Impact IMPACT = new ProjectileImpact(10f, Player.class);
}
