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
import net.rowf.sigilia.input.gesture.DynamicDeltaSequence;
import net.rowf.sigilia.input.gesture.StaticDeltaSequence;

public class WeaponInput implements InputElement {
	private Prototype prototype;
	private TouchInput tapInput;
	private float delay;
	
	private DynamicDeltaSequence activeSequence = 
			new DynamicDeltaSequence(32, StaticDeltaSequence.DEFAULT);
	
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

		Touch finalTap = taps.get(taps.size() - 1);
		
		if (finalTap == TouchInput.RELEASE) {
			activeSequence.reset();
			return 0;
		} else {
			
		}
		
		Entity e = new StandardEntity();
		prototype.apply(e);
		e.setComponent(Motion.class, new ConstantMotion(new Vector(finalTap.x*4, finalTap.y*4, 4f)));
		e.setComponent(Position.class, new Position(finalTap.x, finalTap.y, 1f));
		entities.add(e);
		
		return delay;
	}

}
