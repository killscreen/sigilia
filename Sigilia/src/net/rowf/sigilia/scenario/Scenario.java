package net.rowf.sigilia.scenario;

import java.util.List;
import java.util.Map;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Representation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import android.content.res.Resources;

/**
 * Describes a specific in-game scenario, in terms of its starting conditions 
 * and the representations used to display objects in the scenario.
 * 
 * @author woeltjen
 *
 */
public interface Scenario {
	/**
	 * Populate a list with entities for this scenario.
	 * @param entities
	 */
	public void populate(List<Entity> entities);

	/**
	 * Populate a decorum map with appropriate representations for 
	 * relevant entities.
	 * @param decorum
	 */
	public void decorate(Map<String, Decorator<Representation>> decorum, Resources res);

}
