package net.rowf.sigilia.game.entity.enemy;

import android.util.FloatMath;
import net.rowf.sigilia.game.Entity;
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
		
		return new NewtonianMotion(speed*dx/dist, speed*dy/dist + 1f, speed*dz/dist);
	}

	@Override
	protected void applyAdditional(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
