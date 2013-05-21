package net.rowf.sigilia.game.entity.enemy;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.mental.Intellect;
import net.rowf.sigilia.game.component.metadata.Lifetime;
import net.rowf.sigilia.game.component.metadata.Liveness;
import net.rowf.sigilia.game.component.metadata.Name;
import net.rowf.sigilia.game.component.metadata.PhysicalType;
import net.rowf.sigilia.game.component.metadata.Spawn;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.game.component.physical.Position;
import net.rowf.sigilia.game.component.physical.Size;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.entity.NamedPrototype;
import net.rowf.sigilia.game.entity.StandardEntity;

/**
 * Serves as the superclass for various Enemy types encountered in  
 * different scenarios. Responsible for attaching components which are 
 * common among enemies, such as Liveness and solidity.
 * 
 * @author woeltjen
 *
 */
public abstract class Enemy extends NamedPrototype {
	private static final Size DEFAULT_SIZE = new Size(1,2,1);
	
	private static final float DEATH_TIME = 1f;
	
	public static final String DEATH_SUFFIX = "DEATH";
	
	@Override
	public void apply(Entity e) {
		// TODO Add normal collision stuffs
		e.setComponent(PhysicalType.class, PhysicalType.SOLID);
		e.setComponent(Health.class, new Health(3f));
		e.setComponent(Animation.class, new Animation());
		e.setComponent(Size.class, DEFAULT_SIZE);
		e.setComponent(Liveness.class, ENEMY_LIVENESS);
		super.apply(e);
	}


	private static final Liveness ENEMY_LIVENESS = new Liveness() {
		@Override
		public boolean isAlive() {
			return true;
		}

		@Override
		public void kill(Entity e) {
			Name n = e.getComponent(Name.class);
			Position p = e.getComponent(Position.class);
			Animation a = e.getComponent(Animation.class);
			
			if (p != null && n != null && a != null) {
				StandardEntity death = new StandardEntity();
				Lifetime lifetime = new Lifetime(DEATH_TIME );
				death.setComponent(Liveness.class, lifetime);
				death.setComponent(Intellect.class, lifetime);
				death.setComponent(Name.class, new Name(n.get() + DEATH_SUFFIX));
				death.setComponent(Position.class, p);
				death.setComponent(Animation.class, a);
				a.setNextFrame(a.getNextFrame(), DEATH_TIME);
				
				e.setComponent(Animation.class, null);
				e.setComponent(Position.class, null);
				
				e.setComponent(Spawn.class, new Spawn(death));
			}
			
			e.setComponent(Liveness.class, Liveness.DEAD);
		}
		
	};
}
