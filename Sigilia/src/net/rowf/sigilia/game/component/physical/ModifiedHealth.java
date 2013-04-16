package net.rowf.sigilia.game.component.physical;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.PhysicalType;

public class ModifiedHealth extends Health {
	private Set<PhysicalType> types = new HashSet<PhysicalType>();
	private float inFactor;
	private float outFactor;
	
	/**
	 * Change damage by a constant factor for certain types (or when the source 
	 * of damage does not match certain types)
	 * @param health
	 * @param outOfSetFactor factor to use if source type is not a match
	 * @param inSetFactor factor to use when source type is a match
	 * @param types
	 */
	public ModifiedHealth(float health, float outOfSetFactor, float inSetFactor, PhysicalType... types) {
		super(health);
		Collections.addAll(this.types, types);
		this.inFactor = inSetFactor;
		this.outFactor = outOfSetFactor;
	}

	@Override
	public void damage(Entity source, Entity target, float amount) {
		PhysicalType sourceType = source.getComponent(PhysicalType.class);
		boolean b = types.contains(sourceType);
		amount *= (sourceType != null && types.contains(sourceType)) ?
				inFactor : outFactor;
		super.damage(source, target, amount);
	}

	
	
}
