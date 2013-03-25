package net.rowf.sigilia.game.component.metadata;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

public class Liveness implements Component {
	public static final Liveness ALIVE = new Liveness(true);
	public static final Liveness DEAD  = new Liveness(false);
	
	private boolean liveness;
	
	private Liveness(boolean liveness) {
		this.liveness = liveness;
	}
	
	public boolean isAlive() {
		return liveness;
	}
	
	public void kill(Entity e) {
		e.setComponent(Liveness.class, DEAD);
	}
}
