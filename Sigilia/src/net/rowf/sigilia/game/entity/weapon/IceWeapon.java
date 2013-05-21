package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.metadata.Lifetime;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.ModifiedHealth;
import net.rowf.sigilia.game.component.physical.Orientation;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.component.physical.Spinning;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.Player;
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
	
	private static final Size SIZE = new Size(2f, 2f, 0.5f);
	
	@Override
	protected void applyAdditional(Entity e) {
		Lifetime lifetime = new Lifetime(5f);
		e.setComponent(Size.class, SIZE);
		e.setComponent(Health.class, new ModifiedHealth(1f, 0f, 1f, PhysicalType.FIRE));
		e.setComponent(Liveness.class, lifetime);
		e.setComponent(Intellect.class, lifetime);
		e.setComponent(Animation.class, new PeriodicAnimation(1.5f, false));
		e.setComponent(Impact.class, IMPACT);
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Orientation.class, new Spinning());
	}

	@Override
	public Size getSize() {
		return SIZE;
	}

	@Override
	public float getDelay() {
		return 0.125f;
	}
	
	@Override
	protected float velocity() {
		return 1f;
	}

	@Override
	public DeltaSequence getSigil() {
		return ICE;
	}

	private static final Impact IMPACT = new ProjectileImpact(1f, Player.class, IceWeapon.class);
}
