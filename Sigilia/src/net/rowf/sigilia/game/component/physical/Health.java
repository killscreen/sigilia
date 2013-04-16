package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;

public class Health implements Component {
	private float initialHealth;
	private float health;
	
	public Health(float health) {
		this.initialHealth = health;
		this.health = health;
	}
	
	public void damage(Entity source, Entity target, float amount) {
		health -= amount;
		if (health < 0) {
			Liveness liveness = target.getComponent(Liveness.class);
			if (liveness != null) {
				liveness.kill(target);
			}
		}
	}
	
	public float getRatio() {
		return Math.max(0, health / initialHealth);
	}
}
