package net.rowf.sigilia.game.entity.enemy;

import java.util.List;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Boundary;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.metadata.Event;
import net.rowf.sigilia.game.component.metadata.Lifetime;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.metadata.Spawn;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;

public class VictorySentinel implements Prototype, Intellect, Liveness {
	public static final Name VICTORY_ANIMATION = new Name("VICTORY_ANIMATION");
	private static final float VICTORY_TIME = 3f;
	private static final int VICTORY_PERIODS = 3;
	private static final BoundingBox VICTORY_BOUND = new BoundingBox(0, 0, 1.01f, 2f);
	
	@Override
	public void apply(Entity e) {
		e.setComponent(Prototype.class, this);
		e.setComponent(Intellect.class, this);
		e.setComponent(Liveness.class, this);
	}

	@Override
	public void think(Entity e, float timeStamp, List<Entity> world) {
		for (Entity other : world) {
			Prototype p = other.getComponent(Prototype.class);
			if (p != null && Enemy.class.isAssignableFrom(p.getClass())) {
				return;
			}
		}
		// If control makes it this far, there are no more enemies
		// So, start victory animation
		kill(e);
	}

	@Override
	public boolean isAlive() {
		return true;
	}

	@Override
	public void kill(Entity e) {
		Lifetime lifetime = new Lifetime(VICTORY_TIME);
		e.setComponent(Liveness.class, lifetime);
		e.setComponent(Intellect.class, lifetime);
		
		StandardEntity victory = new StandardEntity();
		victory.setComponent(Name.class, VICTORY_ANIMATION);
		victory.setComponent(Animation.class, new PeriodicAnimation(VICTORY_TIME / VICTORY_PERIODS, false));
		victory.setComponent(Position.class, VICTORY_BOUND);
		victory.setComponent(Boundary.class, VICTORY_BOUND);
		e.setComponent(Spawn.class, new Spawn(victory));

	}

}
