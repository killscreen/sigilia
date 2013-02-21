package net.rowf.sigilia.scenario;

import java.util.List;

import net.rowf.sigilia.game.Entity;

public interface Scenario {
	/**
	 * Populate a list with entities for this scenario.
	 * @param entities
	 */
	public void populate(List<Entity> entities);
}
