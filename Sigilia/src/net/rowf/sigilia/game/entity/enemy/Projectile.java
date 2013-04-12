package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Component;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Boundary;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.metadata.Spawn;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.entity.NamedPrototype;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.geometry.Vector;

public abstract class Projectile extends NamedPrototype implements Component {
	public void spawnProjectile(Entity e, float x, float y, float z, Vector target) {
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
	
	
}
