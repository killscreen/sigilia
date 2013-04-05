package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.collision.Health;
import net.rowf.sigilia.game.collision.Impact;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.physical.ConstantMotion;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.geometry.Vector;
import net.rowf.sigilia.input.gesture.DeltaSequence;

public class DefaultWeapon extends Weapon {
	private static final Motion MOTION = new ConstantMotion(new Vector(0,0,15f));
	
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Liveness.class, Liveness.ALIVE);
		e.setComponent(Impact.class, IMPACT);
		//e.setComponent(Motion.class, MOTION);
	}

	@Override
	protected float velocity() {
		return 2f;
	}

	@Override
	public DeltaSequence getSigil() {
		return null;
	}
	
	private static final Impact IMPACT = new Impact() {
		@Override
		public void impact(Entity source, Entity other) {
			Health health = other.getComponent(Health.class);
			if (health != null) {
				health.damage(source, other, 1f);
				// Only issue damage once!
				Liveness ownLiveness = source.getComponent(Liveness.class);
				if (ownLiveness != null) {
					ownLiveness.kill(source);
				}
			}
		}
	};
}
