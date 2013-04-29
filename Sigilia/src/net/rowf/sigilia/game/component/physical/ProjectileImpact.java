package net.rowf.sigilia.game.component.physical;

import java.util.HashSet;
import java.util.Set;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.entity.NamedPrototype;

public class ProjectileImpact implements Impact {
	private float damage;
	private Set<String> ignore;
	
	public ProjectileImpact(float damage, Class<? extends NamedPrototype>... ignore) {
		this(damage, getSimpleNames(ignore));
	}
	
	public ProjectileImpact(float damage, String... ignore) {
		super();
		this.damage = damage;
		this.ignore = new HashSet<String>();
		for (String o : ignore) {
			this.ignore.add(o);
		}
	}

	@Override
	public void impact(Entity source, Entity other) {
		// Don't damage the owning type		
		Name name = other.getComponent(Name.class);
		if (name != null && ignore.contains(name.get())) {
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
	
	
	private static String[] getSimpleNames(Class<?>... classes) {
		String[] names = new String[classes.length];
		for (int i = 0 ; i < names.length ; i++) {
			names[i] = classes[i].getSimpleName();
		}
		return names;
	}
	
}
