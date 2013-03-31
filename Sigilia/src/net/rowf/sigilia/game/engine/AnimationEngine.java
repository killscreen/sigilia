package net.rowf.sigilia.game.engine;

import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.visual.Animation;
import net.rowf.sigilia.game.component.visual.Animator;

public class AnimationEngine implements Engine {

	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		for (Entity e : entities) {
			Animation anim = e.getComponent(Animation.class);
			if (anim != null) {
				anim.setCurrentTime(timeStamp);
				
				// If we finished a transition, trigger Animator
				if (!anim.isAnimating()) {
					Animator animator = e.getComponent(Animator.class);
					if (animator != null) {
						animator.animate(e, anim);
					}					
				}
			}			
		}
	}

}
