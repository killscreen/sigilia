package net.rowf.sigilia.game.entity.weapon;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.metadata.Lifetime;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;

public class VisibleSigil implements Prototype {
	public static final float SIGIL_VISIBLE_DURATION = 0.75f;
	private Name name;
	
	public VisibleSigil(String name) {
		this.name = new Name(name);
	}

	@Override
	public void apply(Entity e) {
		Lifetime lifetime = new Lifetime(SIGIL_VISIBLE_DURATION);
		e.setComponent(Name.class, name);
		e.setComponent(Animation.class, new PeriodicAnimation(SIGIL_VISIBLE_DURATION, false));
		e.setComponent(Liveness.class, lifetime);
		e.setComponent(Intellect.class, lifetime);
	}
	
	public Entity spawn(float x, float y, float z) {
		StandardEntity e = new StandardEntity();
		apply(e);
		e.setComponent(Position.class, new Position(x,y,z));
		return e;
	}
}
