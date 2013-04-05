package net.rowf.sigilia.game.collision;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;

public class Health implements Component {
	private float health;
	
	public Health(float health) {
		this.health = health;
	}
	
	public void damage(Entity source, Entity target, float amount) {
		health -= amount;
		if (health < 0) {
			Liveness liveness = target.getComponent(Liveness.class);
			liveness.kill(target);
		}
	}
}
