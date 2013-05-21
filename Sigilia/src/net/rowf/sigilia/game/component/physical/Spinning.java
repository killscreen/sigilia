package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Animation;

public class Spinning implements Orientation {
	private static final Vector NO_SPIN = new Vector(0,0,0);
	
	@Override
	public Vector getRotation(Entity e) {
		Animation a = e.getComponent(Animation.class);
		if (a != null) {
			return new Vector(0, 0, 360f * a.getProgress());
		}
		return NO_SPIN;
	}

}
