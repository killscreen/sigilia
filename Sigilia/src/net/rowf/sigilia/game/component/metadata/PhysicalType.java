package net.rowf.sigilia.game.component.metadata;

import net.rowf.sigilia.game.Component;

/**
 * Describes physical types of objects in game. Used primarily 
 * to determine whether or not one entity damages another (for 
 * instance, dead trees are only effected by FIRE) 
 * 
 * @author woeltjen
 *
 */
public enum PhysicalType implements Component {
	SOLID,
	ENERGY,
	ELECTRICITY,
	FIRE,
	ICE
}
