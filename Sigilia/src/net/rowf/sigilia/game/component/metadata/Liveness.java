package net.rowf.sigilia.game.component.metadata;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;

public interface Liveness extends Component {
	public boolean isAlive();
	public void    kill(Entity e);
	
	public static final Liveness ALIVE = new Liveness() {

		@Override
		public boolean isAlive() {
			return true;
		}

		@Override
		public void kill(Entity e) {
			e.setComponent(Liveness.class, Liveness.DEAD);
		}
		
	};
	
	public static final Liveness DEAD  = new Liveness() {

		@Override
		public boolean isAlive() {
			return false;
		}

		@Override
		public void kill(Entity e) {
		}		
	};	
}
