package net.rowf.sigilia.input;

import java.util.Collections;
import java.util.List;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.engine.InputEngine.InputElement;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.game.entity.weapon.Weapon;
import net.rowf.sigilia.input.TouchInput.Touch;
import net.rowf.sigilia.input.gesture.DynamicDeltaSequence;
import net.rowf.sigilia.input.gesture.StaticDeltaSequence;
import android.util.Log;

public class WeaponInput implements InputElement {
	private Weapon defaultWeapon;
	private Weapon weapon;
	private List<Weapon> alternates;
	private TouchInput tapInput;
	private float delay;
	private float expiration;
	
	private DynamicDeltaSequence activeSequence = 
			new DynamicDeltaSequence(32, StaticDeltaSequence.DEFAULT);
	
	public WeaponInput(Weapon weapon, TouchInput tapInput, float delay) {
		this(weapon, Collections.<Weapon>emptyList(), tapInput, delay);
	}
	
	public WeaponInput(Weapon weapon, List<Weapon> alternates, TouchInput tapInput, float delay) {
		super();
		this.defaultWeapon = weapon;
		this.alternates = alternates;
		this.weapon = weapon;
		this.tapInput = tapInput;
		this.delay = delay;
	}

	@Override
	public float apply(List<Entity> entities, float timeStamp) {
		List<Touch> taps = tapInput.getPendingEvents();
		if (taps.isEmpty()) return 0;

		Touch finalTap = taps.get(taps.size() - 1);
		
		if (weapon == defaultWeapon) {
			if (finalTap == TouchInput.RELEASE) {
				float sim = activeSequence.getSimilarity(StaticDeltaSequence.BOLT, 24);
				//Log.i("WeaponInput", "Found a  with confidence " + sim);
				
				for (Weapon w : alternates) {
					if (activeSequence.getSimilarity(w.getSigil(), 24) > 0.9f) {
						Log.i("WeapnInput", "Found a " + w.getClass().getName());
						weapon = w;
						entities.add(w.visibleSigil.spawn(0, 0, 1.05f));
						expiration = timeStamp + w.getLifetime();
						break;
					}
				}
				
				activeSequence.reset();
				return 0;
			} else {
				activeSequence.addPoint(finalTap.x, finalTap.y);
			}
		} else {
			if (timeStamp > expiration) {
				weapon = defaultWeapon;
			}
		}
		
		if (finalTap != TouchInput.RELEASE) {
			Entity e = new StandardEntity();
			weapon.apply(e, finalTap.x, finalTap.y);
			entities.add(e);
			return weapon.getDelay();
		} else {
			return 0f; // Don't delay on release!
		}
		
	}

}
