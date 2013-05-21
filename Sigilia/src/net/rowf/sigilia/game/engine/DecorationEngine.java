package net.rowf.sigilia.game.engine;

import java.util.List;
import java.util.Map;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Name;

/**
 * A DecorationEngine is useful for decorating (adding components of a 
 * specific type) to entities, by entity name. This allows 'core' 
 * attributes of entities to be defined first (those corresponding to 
 * gameplay), and accessory attributes (such as representation) to 
 * be attached later. 
 * 
 * @author woeltjen
 *
 * @param <T> the type of component that this engine is responsible for adding
 */
public class DecorationEngine<T extends Component> implements Engine {
	private Map<String, Decorator<T>> decorators;
	private Class<T> decorationClass;
	
	public DecorationEngine(Class<T> decorationClass, 
			Map<String, Decorator<T>> decorators) {
		this.decorationClass = decorationClass;
		this.decorators = decorators;
	}
	
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (Entity entity : entities) {
			T r = entity.getComponent(decorationClass);
			if (r == null) {
				Name n = entity.getComponent(Name.class);
				if (n != null) {
					Decorator<T> d = decorators.get(n.get());
					if (d != null) {
						entity.setComponent(decorationClass, d.getDecoration(entity));
					}
				}
			}
		}
	}

	public static interface Decorator<T extends Component> {
		public T getDecoration(Entity entity);
	}
}
