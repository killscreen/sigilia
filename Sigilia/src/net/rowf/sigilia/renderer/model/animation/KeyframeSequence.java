package net.rowf.sigilia.renderer.model.animation;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Map;

import net.rowf.sigilia.renderer.model.AnimatedModel;
import net.rowf.sigilia.util.BufferUtil;

public class KeyframeSequence {
	private final FloatBuffer texCoords;
	private final ShortBuffer drawingOrder;
	private final int         triangleCount;
	
	private Map<String, FloatBuffer> keyframes = new HashMap<String, FloatBuffer>();
	private Map<String, Map<String, AnimatedModel>> transitions = 
			new HashMap<String, Map<String, AnimatedModel>>();
	
	public KeyframeSequence(float[] texCoords, short[] drawingOrder) {
		this.texCoords = BufferUtil.toBuffer(texCoords);
		this.drawingOrder = BufferUtil.toBuffer(drawingOrder);
		this.triangleCount = drawingOrder.length / 3;
	}
	
	public void addKeyframe(String name, float[] vertexes) {
		keyframes.put(name, BufferUtil.toBuffer(vertexes));
	}
	
	public AnimatedModel getTransition(String start, String end) {
		// Cache pairs for later use
		if (!transitions.containsKey(start)) {
			transitions.put(start, new HashMap<String,AnimatedModel>());
		}
		Map<String,AnimatedModel> nextMap = transitions.get(start);
		if (!nextMap.containsKey(end)) {
			nextMap.put(end, new Transition(start,end));
		}
		return nextMap.get(end);
	}
	
	private class Transition implements AnimatedModel {
		private FloatBuffer start;
		private FloatBuffer end;

		public Transition(String start, String end) {
			super();
			this.start = keyframes.get(start);
			this.end = keyframes.get(end);
		}

		@Override
		public FloatBuffer getVertexes() {
			return start;
		}

		@Override
		public ShortBuffer getDrawingOrder() {
			return drawingOrder;
		}

		@Override
		public FloatBuffer getTexCoords() {
			return texCoords;
		}

		@Override
		public int getTriangleCount() {
			return triangleCount;
		}

		@Override
		public FloatBuffer getNextVertexes() {
			return end;
		}
		
	}
	
}
