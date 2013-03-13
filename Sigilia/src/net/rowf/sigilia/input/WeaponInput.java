package net.rowf.sigilia.input;

import java.util.List;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.physical.ConstantMotion;
import net.rowf.sigilia.game.component.physical.Motion;
import net.rowf.sigilia.game.engine.InputEngine.InputElement;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.geometry.Vector;
import net.rowf.sigilia.input.TouchInput.Touch;

public class WeaponInput implements InputElement {
	private Prototype prototype;
	private TouchInput tapInput;
	private float delay;
	
	public WeaponInput(Prototype prototype, TouchInput tapInput, float delay) {
		super();
		this.prototype = prototype;
		this.tapInput = tapInput;
		this.delay = delay;
	}

	@Override
	public float apply(List<Entity> entities) {
		List<Touch> taps = tapInput.getPendingEvents();
		if (taps.isEmpty()) return 0;

		//TODO: Where to intercept shape events?
		
		Touch finalTap = taps.get(taps.size() - 1);
		Entity e = new StandardEntity();
		prototype.apply(e);
		e.setComponent(Motion.class, new ConstantMotion(new Vector(finalTap.x, finalTap.y, 1f)));
		e.setComponent(Position.class, new Position(finalTap.x, finalTap.y, 1f));
		entities.add(e);
		
		return delay;
	}

}
