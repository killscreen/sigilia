package net.rowf.sigilia.game.component.metadata;

import net.rowf.sigilia.game.Component;

/**
 * Describes a meaningful event in the game that may require some 
 * special external representation (beyond normal rendering), such as 
 * vibration. Events will be issued to EventListeners by an EventEngine,
 * if one is running. * 
 * 
 * @see net.rowf.sigilia.game.engine.EventEngine
 * 
 * @author woeltjen
 *
 */
public enum Event implements Component {
	PLAYER_HIT,
	PLAYER_KILLED
}
