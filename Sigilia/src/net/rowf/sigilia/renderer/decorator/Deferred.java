package net.rowf.sigilia.renderer.decorator;

/**
 * Used to represent an object that needs to be lazily initialized. 
 * (For instance, shaders and textures need to be initialized after 
 * a GL context has been created, but scenario objects are created before 
 * this happens.) 
 * 
 * @author woeltjen
 *
 * @param <T> the type of object produced lazily
 */
public abstract class Deferred<T>  {
	private Getter getter = new Getter();
	
	protected abstract T create();
	
	public T get() {
		return getter.get(); // Maybe an if-null approach would be faster?
	}

	private class Getter {
		public T get() {
			final T created = create();
			getter = new Getter() {
				public T get() {
					return created;
				}
			};
			return created;
		}
	}
}
