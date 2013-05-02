package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.ModifiedHealth;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.physical.Size;
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
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Size.class, new Size(2f, 2f, 2f));
		e.setComponent(Health.class, new ModifiedHealth(1f, 0f, 1f, PhysicalType.FIRE));
		e.setComponent(Liveness.class, Liveness.ALIVE);

	}

	@Override
	public float getDelay() {
		return 0.125f;
	}
	
	@Override
	protected float velocity() {
		return 0.5f;
	}

	@Override
	public DeltaSequence getSigil() {
		return ICE;
	}

	private static final Impact IMPACT = new ProjectileImpact(10f, Player.class);
}
