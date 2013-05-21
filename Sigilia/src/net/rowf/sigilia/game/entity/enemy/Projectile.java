package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Spawn;
import net.rowf.sigilia.game.component.physical.Boundary;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.component.physical.Vector;
import net.rowf.sigilia.game.entity.NamedPrototype;
import net.rowf.sigilia.game.entity.StandardEntity;

public abstract class Projectile extends NamedPrototype implements Component {
	private static final Splat SPLAT = new Splat();
	
	public void spawnProjectile(Entity e, float x, float y, float z, Vector target) {
		e.setComponent(Liveness.class, PROJECTILE_LIVENESS);
		
		apply(e);
		
		// TODO: Need to find a good common place for this
		Size sz = e.getComponent(Size.class);
		if (sz != null) {
			BoundingBox bound = new BoundingBox(x, y, z,sz.get());
			e.setComponent(Position.class, bound);
			e.setComponent(Boundary.class, bound);
		} else {
			e.setComponent(Position.class, new Position(x,y,z));
		}
		
		e.setComponent(Motion.class, getMotion(x,y,z,target));
	}
	
	public Spawn spawnProjectile(float x, float y, float z, Vector target) {
		Entity e = new StandardEntity();
		spawnProjectile(e, x, y, z, target);
		return new Spawn(e);
	}
	
	protected abstract Motion getMotion(float x, float y, float z, Vector target);
	
	private static final Liveness PROJECTILE_LIVENESS = new Liveness() {

		@Override
		public boolean isAlive() {
			return true;
		}

		@Override
		public void kill(Entity e) {
			Position p = e.getComponent(Position.class);
			if (p != null) {
				// TODO: Move to player?
				e.setComponent(Spawn.class, SPLAT.spawnAt(p));
			}
			e.setComponent(Liveness.class, Liveness.DEAD);
		}
		
	};
}
