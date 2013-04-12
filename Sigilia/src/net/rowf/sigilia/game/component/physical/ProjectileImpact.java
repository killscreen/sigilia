package net.rowf.sigilia.game.component.physical;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.entity.NamedPrototype;

public class ProjectileImpact implements Impact {
	private float damage;
	private String owner;
	
	public ProjectileImpact(float damage, Class<? extends NamedPrototype> owner) {
		this(damage, owner.getSimpleName());
	}
	
	public ProjectileImpact(float damage, String owner) {
		super();
		this.damage = damage;
		this.owner = owner;
	}

	@Override
	public void impact(Entity source, Entity other) {
		// Don't damage the owning type		
		Name name = other.getComponent(Name.class);
		if (name != null && owner.equals(name.get())) {
			return;
		}
		
		// Issue damage
		Health health = other.getComponent(Health.class);
		if (health != null) {
			health.damage(source, other, damage);
		}
		
		// Destroy self if we ran into a solid
		PhysicalType type = other.getComponent(PhysicalType.class);
		if (type != null && type == PhysicalType.SOLID) {
			Liveness ownLiveness = source.getComponent(Liveness.class);
			if (ownLiveness != null) {
				ownLiveness.kill(source);
			}
		}		
	}
	
	
	
	
}
