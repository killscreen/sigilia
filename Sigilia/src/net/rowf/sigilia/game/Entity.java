package net.rowf.sigilia.game;

/**
 * An entity is any meaningful "thing" within the context of a running 
 * game session. In practice, an entity is simply a big bucket of 
 * Components (identified by type) which contain data and behavior 
 * accessed and invoked by Engines and other Components.  
 * 
 * @author woeltjen
 *
 */
public interface Entity {
	/**
	 * Get a component of this entity. Components are specified by 
	 * class only. The result may be null (for instance, if there is no such 
	 * component within this entity) 
	 * @param componentClass
	 * @return
	 */
	public <T extends Component> T getComponent(Class<T> componentClass);
	
	/**
	 * Set a component on this entity. Note that this will replace 
	 * any previous component on this entity.
	 * @param componentClass
	 * @param component
	 */
	public <T extends Component> void setComponent(Class<T> componentClass, T component);
}
