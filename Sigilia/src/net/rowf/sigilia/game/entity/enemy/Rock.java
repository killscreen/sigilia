package net.rowf.sigilia.game.entity.enemy;

import android.util.FloatMath;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.NewtonianMotion;
import net.rowf.sigilia.geometry.Vector;

public class Rock extends Projectile {
	private static final float speed = 0.25f;
	@Override
	protected Motion getMotion(float x, float y, float z, Vector target) {
		float dx = target.getX() - x;
		float dy = target.getY() - y;
		float dz = target.getZ() - z;
		
		float dist = FloatMath.sqrt(dx*dx + dy*dy + dz*dz);
		
		// 0 = overthrow - grav * time * time
		
		return new NewtonianMotion(speed*dx/dist, speed*dy/dist + 0.125f, speed*dz/dist);
	}

	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Liveness.class, Liveness.ALIVE);
		e.setComponent(Impact.class, IMPACT);
	}
	
	private static final Impact IMPACT = new Impact() {
		@Override
		public void impact(Entity source, Entity other) {
			Health health = other.getComponent(Health.class);
			if (health != null) {
				health.damage(source, other, 1f);
				// Only issue damage once!
				Liveness ownLiveness = source.getComponent(Liveness.class);
				if (ownLiveness != null) {
					ownLiveness.kill(source);
				}
			}
		}
	};

}
