package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.physical.ConstantMotion;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.geometry.Vector;
import net.rowf.sigilia.input.gesture.DeltaSequence;
import net.rowf.sigilia.input.gesture.StaticDeltaSequence;

public class LightningWeapon extends Weapon {
	private static final Motion MOTION = new ConstantMotion(new Vector(0,0,15f));
	
	@Override
	protected void applyAdditional(Entity e) {
		//e.setComponent(Motion.class, MOTION);
		e.setComponent(Animation.class, new PeriodicAnimation(getDelay()/2f));
	}

	@Override
	public float getDelay() {
		return 0.33f;
	}
	
	@Override
	protected float velocity() {
		return 32f;
	}

	@Override
	public DeltaSequence getSigil() {
		return StaticDeltaSequence.BOLT;
	}
}
