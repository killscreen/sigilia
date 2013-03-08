package net.rowf.sigilia.renderer.decorator;

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
