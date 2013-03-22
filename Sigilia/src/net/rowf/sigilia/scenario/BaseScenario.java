package net.rowf.sigilia.scenario;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.shader.program.FlatTextureShader;

public abstract class BaseScenario implements Scenario {
	
	/*
	 * We get new versions of these common elements exactly once per 
	 * Scenario instantiation. This permits the reuse of a shader through 
	 * multiple objects in scene, but still makes new ones every time 
	 * the GL context is recreated
	 */
	public final DeferredProgram DEFERRED_FLAT_SHADER = FlatTextureShader.deferredForm();
	
	protected Entity spawn(Prototype p, float x, float y, float z) {
		Entity entity = new StandardEntity();
		p.apply(entity);
		entity.setComponent(Position.class, new Position(x,y,z));
		return entity;
	}
	

}
