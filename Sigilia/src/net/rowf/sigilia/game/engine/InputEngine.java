package net.rowf.sigilia.game.engine;

import java.util.ArrayList;
import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;

/**
 * Allows input elements (such as the touch screen) to inject 
 * entities or modify existing entities into a running game.
 *  
 * @author woeltjen
 *
 */
public class InputEngine implements Engine {
	private List<InputRecord> inputRecords = new ArrayList<InputRecord>();

	public InputEngine(List<InputElement> elements) {
		for (InputElement element : elements) {
			inputRecords.add(new InputRecord(-1, element));
		}
	}
	
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (InputRecord r : inputRecords) {
			if (r.next < 0) r.next = timeStamp;
			if (r.next <= timeStamp) {
				r.next = timeStamp + r.element.apply(entities, timeStamp);
			}
		}
	}
	
	/**
	 * An InputElement performs some action upon the group of running 
	 * entities, typically as a response to some input.
	 * 
	 * TODO: This interface is redundant to Engine - should each input 
	 * device just have its own engine?
	 * 
	 * @author woeltjen
	 *
	 */
	public interface InputElement {
		/**
		 * Apply any behavior associated with this input element.
		 * @param entities
		 * @return the duration before this input should be checked again
		 */
		public float apply(List<Entity> entities, float timeStamp);
	}
	
	private static class InputRecord {
		public float        next;
		public InputElement element;
		public InputRecord(float next, InputElement element) {
			super();
			this.next = next;
			this.element = element;
		}
	}
}
