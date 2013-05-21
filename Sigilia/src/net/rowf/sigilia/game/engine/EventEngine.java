package net.rowf.sigilia.game.engine;

import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Event;

/**
 * Detects and dispatches events raised by a running game. These "events" 
 * are typically things with a non-visual representation (such as 
 * player damage, which triggers vibration)
 * 
 * @author woeltjen
 *
 */
public class EventEngine implements Engine {
	private EventListener dispatcher;
	
	public EventEngine(EventListener dispatcher) {
		super();
		this.dispatcher = dispatcher;
	}


	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (Entity e : entities) {
			Event evt = e.getComponent(Event.class);
			if (evt != null) {
				e.setComponent(Event.class, null);
				dispatcher.dispatch(evt);
			}
		}
	}
	
	
	public interface EventListener {
		public void dispatch(Event e);
	}

}
