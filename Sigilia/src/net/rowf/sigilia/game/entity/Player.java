package net.rowf.sigilia.game.entity;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.metadata.Event;
import net.rowf.sigilia.game.component.metadata.Lifetime;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.metadata.Spawn;
import net.rowf.sigilia.game.component.physical.Boundary;
import net.rowf.sigilia.game.component.physical.BoundingBox;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;

public class Player extends NamedPrototype {
	public static final Name DEATH_ANIMATION = new Name("DEATH_ANIMATION");
	private static final float DEATH_TIME = 3f;
	private static final BoundingBox DEATH_BOUND = new BoundingBox(0, 0, 1.01f, 2f);
	
	private static final float HEALTH_INITIAL = 100f;
	
	private BoundingBox BOUND = new BoundingBox(0, 0, 1, 1.05f);
	
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(Health.class, new Health(HEALTH_INITIAL) {
			@Override
			public void damage(Entity source, Entity target, float amount) {
				target.setComponent(Event.class, Event.PLAYER_HIT);
				super.damage(source, target, amount);
			}			
		});
		e.setComponent(Animation.class, new HealthAnimation(e.getComponent(Health.class)));
		e.setComponent(Boundary.class, BOUND);
		e.setComponent(Position.class, BOUND);
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Liveness.class, new PlayerLiveness());
	}

	private static class PlayerLiveness implements Liveness {
		@Override
		public boolean isAlive() {
			return true;
		}

		@Override
		public void kill(Entity e) {
			Lifetime lifetime = new Lifetime(DEATH_TIME);
			e.setComponent(Liveness.class, lifetime);
			e.setComponent(Intellect.class, lifetime);
			e.setComponent(Health.class, null);
			e.setComponent(Event.class, Event.PLAYER_KILLED);
			
			StandardEntity death = new StandardEntity();
			death.setComponent(Name.class, DEATH_ANIMATION);
			death.setComponent(Animation.class, new PeriodicAnimation(DEATH_TIME, false));
			death.setComponent(Position.class, DEATH_BOUND);
			death.setComponent(Boundary.class, DEATH_BOUND);
			e.setComponent(Spawn.class, new Spawn(death));
		}		
	}
	
	private class HealthAnimation extends Animation {
		private Health health;

		public HealthAnimation(Health health) {
			super();
			this.health = health;
		}

		@Override
		public float getProgress() {
			return health.getRatio();
		}
		
		
	}
}
