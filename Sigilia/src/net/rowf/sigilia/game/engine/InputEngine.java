package net.rowf.sigilia.game.engine;

import java.util.List;

import net.rowf.sigilia.game.Engine;
import net.rowf.sigilia.game.Entity;

public class InputEngine implements Engine {

	
	@Override
	public void runCycle(List<Entity> entities, float timeStamp) {
		// TODO Auto-generated method stub
		
	}
	
	public interface InputElement {
		public void applyTo(List<Entity> entities, float timestamp);
	}
}
