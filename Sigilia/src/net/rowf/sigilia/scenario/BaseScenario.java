package net.rowf.sigilia.scenario;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.entity.Prototype;
import net.rowf.sigilia.game.entity.StandardEntity;
import net.rowf.sigilia.renderer.decorator.DeferredProgram;
import net.rowf.sigilia.renderer.model.animation.KeyframeSequence;
import net.rowf.sigilia.renderer.shader.program.AnimatedFlatTextureShader;
import net.rowf.sigilia.renderer.shader.program.FlatTextureShader;
import android.content.res.Resources;
import android.util.Log;

public abstract class BaseScenario implements Scenario {
	
	/*
	 * We get new versions of these common elements exactly once per 
	 * Scenario instantiation. This permits the reuse of a shader through 
	 * multiple objects in scene, but still makes new ones every time 
	 * the GL context is recreated
	 */
	public final DeferredProgram DEFERRED_FLAT_SHADER = FlatTextureShader.deferredForm();
	public final DeferredProgram DEFERRED_ANIM_SHADER = AnimatedFlatTextureShader.deferredForm();
	
	protected Entity spawn(Prototype p, float x, float y, float z) {
		Entity entity = new StandardEntity();
		p.apply(entity);
		entity.setComponent(Position.class, new Position(x,y,z));
		return entity;
	}

	protected KeyframeSequence loadKeyframeSequence (Resources res, int id) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(res.openRawResource(id)));
			
			String[] texLine = reader.readLine().split(",");
			String[] drawingLine = reader.readLine().split(",");
			float[] texCoords = new float[texLine.length];
			short[] drawingOrder = new short[drawingLine.length];
			
			// Load texture coordinates. These need a little bit of 
			// conversion, as they are in object space, not UV space
			for (int i = 0; i < texLine.length; i++) {
				texCoords[i] = Float.parseFloat(texLine[i]) + 0.5f;
				if (i % 2 == 1) {
					texCoords[i] = 0.999f - texCoords[i];
				}
			}
			
			for (int i = 0; i < drawingLine.length; i++) {
				drawingOrder[i] = Short.parseShort(drawingLine[i]);
			}
			
			KeyframeSequence seq = new KeyframeSequence(texCoords, drawingOrder);
			
			// Load keyframes from remaining lines
			String line;
			while ((line = reader.readLine()) != null) {
				String[] split    = line.split("\t");
				String   name     = split[0];
				String[] vertsStr = split[1].split(",");
				float[]  verts    = new float[vertsStr.length + vertsStr.length/2];
				int j = 0;
				for (int i = 0; i < vertsStr.length; i++) {
					verts[j++] = Float.parseFloat(vertsStr[i]);
					if (i % 2 == 1) {
						verts[j++] = 0; // Fill in z values
					}
				}
				seq.addKeyframe(name, verts);
			}
			
			return seq;
		} catch (Exception e) {
			Log.e("BaseScenario", "Could not read resource for keyframe sequence: " + e.getMessage());
			return null;
		}
	}
}
