package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.NewtonianMotion;
import net.rowf.sigilia.game.component.physical.Orientation;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.component.physical.Velocity;
import net.rowf.sigilia.game.entity.environment.Tree;
import android.util.FloatMath;

/**
 * Describes an arrow (weapon used by Archers, enemies in the 
 * second scenario)
 * @author woeltjen
 *
 */
public class Arrow extends Projectile {
	private static final float SPEED = 5f;
	@Override
	protected Motion getMotion(float x, float y, float z, Vector target) {
		float grav = NewtonianMotion.GRAVITY / 2;
		float dx = target.getX() - x;
		float dy = target.getY() - y;
		float dz = target.getZ() - z;
		
		float dist = FloatMath.sqrt(dx*dx + dz*dz);
		float t    = dist / SPEED;
		float vy = dy/t - 0.5f * grav * t; // Compensate for gravity
		// 0 = overthrow - grav * time * time
		
		return new NewtonianMotion(dx/t, vy, dz/t, grav);
	}

	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Impact.class, IMPACT);	
		e.setComponent(Orientation.class, new ArrowOrientation());
	}

	private class ArrowOrientation implements Orientation {

		@Override
		public Vector getRotation(Entity e) {
			Motion m = e.getComponent(Motion.class);
			if (m != null && m instanceof Velocity) {
				Velocity v = (Velocity) m;
				return new Vector((float) Math.atan2(v.getY(), -v.getZ()), 
						(float) Math.atan2(-v.getX(), -v.getZ()), 0);
			}
			return new Vector(0,0,0);
		}
		
	}
	
	private static final Impact IMPACT = new ProjectileImpact(5f, Archer.class, Tree.class); 	

}
