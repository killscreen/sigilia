package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.metadata.Lifetime;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Spawn;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.NamedPrototype;
import net.rowf.sigilia.game.entity.StandardEntity;

/**
 * Splats are shown whenever an enemy projectile impacts something 
 * (typically, the player)
 * 
 * @author woeltjen
 *
 */
public class Splat extends NamedPrototype {
	private static final float SPLAT_TIME = 0.33f;
	
	@Override
	protected void applyAdditional(Entity e)  {
		Lifetime lifetime = new Lifetime(SPLAT_TIME );
		e.setComponent(Liveness.class, lifetime);
		e.setComponent(Intellect.class, lifetime);
		e.setComponent(Animation.class, new PeriodicAnimation(SPLAT_TIME, false));
	}
	
	public Spawn spawnAt(Vector location) {
		Entity e = new StandardEntity();
		this.apply(e);
		float z = location.getZ();
		z = Math.max(1.01f, z);
		e.setComponent(Position.class, 
				new Position(location.getX(), location.getY(), z));
		return new Spawn(e);
	}

}
