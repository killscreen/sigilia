package net.rowf.sigilia.game.entity;

import java.util.HashMap;
import java.util.Map;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

public class StandardEntity implements Entity {

	private Map<Class, Object> components = new HashMap<Class, Object>();
	
	@Override
	public <T extends Component> T getComponent(Class<T> componentClass) {
		return components.containsKey(componentClass) ?
				(T) components.get(componentClass) : null;
	}

	@Override
	public <T extends Component> void setComponent(Class<T> componentClass,
			T component) {
		components.put(componentClass, component);
	}

}
