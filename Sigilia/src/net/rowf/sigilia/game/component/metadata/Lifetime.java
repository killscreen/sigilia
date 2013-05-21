package net.rowf.sigilia.game.component.metadata;

import java.util.List;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.mental.Intellect;

/**
 * A component used to indicates, as a Liveness that an entity is alive, 
 * but only for a finite period of time, as counted down by its Intellect. 
 * 
 * @author woeltjen
 *
 */
public class Lifetime implements Liveness, Intellect {
	private float lifetime;
	private float timeOfDeath = Float.NaN;	
	
	public Lifetime(float lifetime) {
		super();
		this.lifetime = lifetime * 0.95f; // Always go a little shorter...
	}

	@Override
	public boolean isAlive() {
		return true;
	}

	@Override
	public void kill(Entity e) {
		e.setComponent(Liveness.class, Liveness.DEAD);
	}

	@Override
	public void think(Entity e, float timeStamp, List<Entity> world) {
		if (Float.isNaN(timeOfDeath)) {
			timeOfDeath = timeStamp + lifetime;
		}
		if (timeStamp > timeOfDeath) {
			kill(e);
		}
	}
}
