package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.Player;
import net.rowf.sigilia.input.gesture.DeltaSequence;
import net.rowf.sigilia.input.gesture.StaticDeltaSequence;

/**
 * A swarm of bees, unleashed by the player. (not fully implemented)
 * @author woeltjen
 *
 */
public class BeeWeapon extends Weapon {
	private static final Vector[] BEE_POINTS = {
		new Vector (0, 0, 0),
		new Vector (-1, -1, 0),
		new Vector (-2, -1, 0),
		new Vector (-3, 0, 0),
		new Vector (-2, 1, 0),		
		new Vector (-1, 1, 0),
		
		new Vector (0, 0, 0),
		new Vector (1, -1, 0),
		new Vector (2, -1, 0),
		new Vector (3, 0, 0),
		new Vector (2, 1, 0),
		new Vector (1, 1, 0),
		
		new Vector (0, 0, 0),
		new Vector (-1, -1.5f, 0),
		new Vector (-1, -3, 0),
		new Vector (0, -4, 0),
		new Vector (0, -6, 0),		
		
	};
	public static final DeltaSequence BEE = 
			new StaticDeltaSequence(BEE_POINTS); 
	
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
		return 3f;
	}

	@Override
	public DeltaSequence getSigil() {
		return BEE;
	}

	private static final Impact IMPACT = new ProjectileImpact(10f, Player.class);
}
