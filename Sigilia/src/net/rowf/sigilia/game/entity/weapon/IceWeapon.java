package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.Player;
import net.rowf.sigilia.geometry.Vector;
import net.rowf.sigilia.input.gesture.DeltaSequence;
import net.rowf.sigilia.input.gesture.StaticDeltaSequence;

public class IceWeapon extends Weapon {
	private static final Vector[] ICE_POINTS = {
		new Vector (0, 0, 0),
		new Vector (-2, -2, 0),
		new Vector (-4, 0, 0),
		new Vector (-2, -6, 0),
		new Vector (0, 0, 0)
	};
	private static final DeltaSequence ICE = 
			new StaticDeltaSequence(ICE_POINTS); 
	
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Liveness.class, Liveness.ALIVE);
		e.setComponent(Animation.class, new PeriodicAnimation(getDelay()/2f));
		e.setComponent(Impact.class, IMPACT);
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
	}

	@Override
	public float getDelay() {
		return 0.5f;
	}
	
	@Override
	protected float velocity() {
		return 0.25f;
	}

	@Override
	public DeltaSequence getSigil() {
		return ICE;
	}

	private static final Impact IMPACT = new ProjectileImpact(10f, Player.class);
}
