package net.rowf.sigilia.game.entity.environment;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.metadata.Lifetime;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.metadata.Spawn;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.physical.ModifiedHealth;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.PeriodicAnimation;
import net.rowf.sigilia.game.entity.NamedPrototype;
import net.rowf.sigilia.game.entity.StandardEntity;

public class Tree extends NamedPrototype {
	public  static final Name  TREE_DEATH_NAME = new Name(Tree.class.getSimpleName() + "DEATH");
	private static final float DEATH_TIME = 1.5f;
	
	@Override
	protected void applyAdditional(Entity e) {
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Size.class, new Size(2f, 6f, 1f));
		e.setComponent(Health.class, new ModifiedHealth(1f, 0f, 1f, PhysicalType.FIRE));
		e.setComponent(Liveness.class, TREE_LIVENESS);
	}
	
	private static final Liveness TREE_LIVENESS = new Liveness() {
		@Override
		public boolean isAlive() {
			return true;
		}

		@Override
		public void kill(Entity e) {
			Position p = e.getComponent(Position.class);

			if (p != null) {
				StandardEntity death = new StandardEntity();
				Lifetime lifetime = new Lifetime(DEATH_TIME );
				death.setComponent(Liveness.class, lifetime);
				death.setComponent(Intellect.class, lifetime);
				death.setComponent(Animation.class, new PeriodicAnimation(DEATH_TIME, false));
				death.setComponent(Name.class, TREE_DEATH_NAME);
				death.setComponent(Position.class, p);
				
				e.setComponent(Spawn.class, new Spawn(death));
			}
			
			e.setComponent(Liveness.class, Liveness.DEAD);
		}
		
	};

}
