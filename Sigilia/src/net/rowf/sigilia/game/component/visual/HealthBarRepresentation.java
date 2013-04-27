package net.rowf.sigilia.game.component.visual;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.physical.Health;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.model.Billboard;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.VertexParameter;
import net.rowf.sigilia.renderer.shader.program.HealthBarShader;
import android.opengl.GLES20;

public class HealthBarRepresentation implements Representation {
	private DeferredProgram program = HealthBarShader.deferredForm();

	@Override
	public Renderable makeRenderable(Entity e) {
		float h = 1f;
		Health health = e.getComponent(Health.class);
		if (health != null) {
			h = health.getRatio();
		}
		return new HealthBarRenderable(h);
	}
	
	private class HealthBarRenderable implements Renderable {
		private float health;
		
		public HealthBarRenderable(float health) {
			super();
			this.health = health;
		}

		@Override
		public void render(float[] viewMatrix) {
			ParameterizedProgram p = program.get();
			Model model = Billboard.UNIT;
			p.begin();
			p.set(ScalarParameter.TRANSITION, health);
			p.set(VertexParameter.VERTEX, model.getVertexes());
			
			// TODO: May as well move this to program?
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getTriangleCount() * 3, 
					              GLES20.GL_UNSIGNED_SHORT, model.getDrawingOrder());

			p.end();
		}		
	}
}
