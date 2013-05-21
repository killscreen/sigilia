package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.physical.ConstantMotion;
import net.rowf.sigilia.game.component.physical.Impact;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.ProjectileImpact;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import android.util.FloatMath;

/**
 * Describes a ball of energy, similar to the player's default weapon. 
 * Used by the Wizard in the third scenario. 
 * 
 * @author woeltjen
 *
 */
public class Energyball extends Projectile {
	private static final float SPEED = 7.5f;

	@Override
	protected Motion getMotion(float x, float y, float z, Vector target) {
		float dx = target.getX() - x;
		float dy = target.getY() - y;
		float dz = target.getZ() - z;

		float dist = FloatMath.sqrt(dx * dx + dz * dz);
		float scale = SPEED / dist;

		return new ConstantMotion(new Vector(dx * scale, dy * scale, dz * scale));
	}

	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(PhysicalType.class, PhysicalType.ENERGY);
		e.setComponent(Impact.class, IMPACT);
		e.setComponent(Animation.class, new PeriodicAnimation(1.5f, false));
	}

	private static final Impact IMPACT = new ProjectileImpact(2.5f, Wizard.class, IceShield.class);

}
