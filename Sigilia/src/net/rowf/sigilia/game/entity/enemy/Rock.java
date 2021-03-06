package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.ModifiedHealth;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.NewtonianMotion;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.physical.Vector;
import android.util.FloatMath;

/**
 * Describes a thrown rock (weapon used by Goblins, enemies in the 
 * first scenario)
 * @author woeltjen
 *
 */
public class Rock extends Projectile {
	private static final float SPEED = 7.5f;
	@Override
	protected Motion getMotion(float x, float y, float z, Vector target) {
		float dx = target.getX() - x;
		float dy = target.getY() - y;
		float dz = target.getZ() - z;
		
		float dist = FloatMath.sqrt(dx*dx + dz*dz);
		float t    = dist / SPEED;
		float vy = dy/t - 0.5f * NewtonianMotion.GRAVITY * t; // Compensate for gravity
		// 0 = overthrow - grav * time * time
		
		return new NewtonianMotion(dx/t, vy, dz/t);
	}

	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Impact.class, IMPACT);
		e.setComponent(Health.class, new ModifiedHealth(5f, 0f, 1f, PhysicalType.ELECTRICITY));
	}
	
	private static final Impact IMPACT = new ProjectileImpact(3f, Goblin.class); 	

}
