package net.rowf.sigilia.input;

import java.util.List;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.engine.InputEngine.InputElement;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.input.TapInput.Tap;

public abstract class WeaponInput implements InputElement {
	private TapInput tapInput;
	
	@Override
	public float apply(List<Entity> entities) {
		List<Tap> taps = tapInput.getPendingEvents();
		if (taps.isEmpty()) return 0;

		//TODO: Where to intercept shape events?
		
		Tap finalTap = taps.get(taps.size() - 1);
		Entity e = new StandardEntity();
		//prototype.apply(e)
		//e.setComponent(Position.class, new Position(finalTap.x, finalTap.y, ...);
		entities.add(e);
		
		return getDelay();
	}

	protected abstract float getDelay();
}
