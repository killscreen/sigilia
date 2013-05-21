package net.rowf.sigilia.game.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.entity.Prototype;

/**
 * Detects whether or not a scenario has reached a completion state 
 * (such as success or failure) and, if so, delivers a corresponding 
 * result to some callback.
 * 
 * @author woeltjen
 *
 * @param <T> the type used to describe the scenario result
 */
public class CompletionEngine<T> implements Engine {
	private CompletionCallback<T> callback;
	private List<CompletionCriterion<T>> criteria = new ArrayList<CompletionCriterion<T>>();
	
	public CompletionEngine(CompletionCallback<T> callback, CompletionCriterion<T>... criteria) {
		super();
		this.callback = callback;
		Collections.<CompletionCriterion<T>>addAll(this.criteria, criteria);
	}

	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (CompletionCriterion<T> criterion : criteria) {
			if (criterion.satisfied(entities)) {
				callback.onScenarioComplete(criterion.result);
				return;
			}
		}
	}
	
	public static class DoesNotContain extends CompletionCriterion<Boolean> {
		private Class<? extends Prototype> prototype;
		
		public DoesNotContain(Class<? extends Prototype> prototype, Boolean result) {
			super(result);
			this.prototype = prototype;
		}
		
		@Override
		public boolean satisfied(List<Entity> entities) {
			for (Entity entity : entities) {
				Prototype p = entity.getComponent(Prototype.class);
				if (p != null && prototype.isAssignableFrom(p.getClass())) {
					return false;
				}
			}
			return true;
		}
		
	}
		
	public static abstract class CompletionCriterion<T> {
		public final T result;
		
		public CompletionCriterion(T result) {
			super();
			this.result = result;
		}

		public abstract boolean satisfied(List<Entity> entities);
	}
	
	public static interface CompletionCallback<T> {
		public void onScenarioComplete(T success);
	}
}
