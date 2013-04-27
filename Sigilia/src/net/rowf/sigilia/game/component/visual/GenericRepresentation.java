package net.rowf.sigilia.game.component.visual;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.rowf.sigilia.game.Entity;
import net.rowf.sigilia.game.component.Position;
import net.rowf.sigilia.game.component.physical.Orientation;
import net.rowf.sigilia.game.engine.DecorationEngine.Decorator;
import net.rowf.sigilia.geometry.Vector;
import net.rowf.sigilia.renderer.GenericRenderable;
import net.rowf.sigilia.renderer.GenericRenderable.CompositeRenderingElement;
import net.rowf.sigilia.renderer.GenericRenderable.RenderingElement;
import net.rowf.sigilia.renderer.GenericRenderable.StaticElement;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.decorator.Deferred;
import net.rowf.sigilia.renderer.model.AnimatedModel;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.model.animation.KeyframeSequence;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.ScalarParameter;
import net.rowf.sigilia.renderer.shader.VertexParameter;

public class GenericRepresentation implements Representation, Decorator<Representation> {
	private Collection<RenderingElement> staticElements;
	private List<DynamicRenderingElement> dynamicElements =
			new ArrayList<DynamicRenderingElement>();
	private Deferred<ParameterizedProgram> program;
	private ShortBuffer drawOrder;
	private int         triangleCount;
	
	public GenericRepresentation(Deferred<ParameterizedProgram> program,
			Model model,
			Collection<RenderingElement> staticElements,
			DynamicRenderingElement... dynamicElements) {
		this(program, model.getDrawingOrder(), model.getTriangleCount(), 
				staticElements, dynamicElements);
		this.staticElements.add(new StaticElement<FloatBuffer>(VertexParameter.TEXTURE_COORD, model.getTexCoords()));
		this.staticElements.add(new StaticElement<FloatBuffer>(VertexParameter.VERTEX, model.getVertexes()));
	}
	
	public GenericRepresentation(Deferred<ParameterizedProgram> program,
			ShortBuffer drawOrder, int triangleCount,
			Collection<RenderingElement> staticElements,
			DynamicRenderingElement... dynamicElements) {
		super();
		this.program = program;
		this.drawOrder = drawOrder;
		this.triangleCount = triangleCount;
		this.staticElements = new ArrayList<RenderingElement>();
		this.staticElements.addAll(staticElements);
		Collections.addAll(this.dynamicElements, dynamicElements);
	}

	@Override
	public Renderable makeRenderable(Entity e) {
		Collection<RenderingElement> elements;
	
		if (dynamicElements.isEmpty()) {
			elements = staticElements;
		} else {
			elements = new ArrayList<RenderingElement>(staticElements.size() + dynamicElements.size());
			elements.addAll(staticElements);
			for (DynamicRenderingElement dynamicElement : dynamicElements) {
				RenderingElement element = dynamicElement.produceElement(e);
				if (element != null) {
					elements.add(element);
				}
			}
		}	
		
		GenericRenderable r = new GenericRenderable(program, drawOrder, triangleCount, elements);
		
		Position p = e.getComponent(Position.class);
		if (p != null) {
			r.translate(p.getX(), p.getY(), p.getZ());
		}
		
		Orientation o = e.getComponent(Orientation.class);
		if (o != null) {
			Vector v = o.getRotation(e);
			r.rotate(v.getX(), v.getY(), v.getZ());
		}
		
		return r;
	}

	@Override
	public Representation getDecoration(Entity entity) {
		return this;
	}

	
	public interface DynamicRenderingElement {
		public abstract RenderingElement produceElement(Entity e);
	}
	
	public class AnimationElement implements DynamicRenderingElement {
		private KeyframeSequence seq;

		public AnimationElement(KeyframeSequence seq) {
			super();
			this.seq = seq;
		}

		@Override
		public RenderingElement produceElement(Entity e) {
			Animation anim = e.getComponent(Animation.class);
			if (anim != null) {
			    float t = anim.getProgress();
				AnimatedModel model = seq.getTransition(anim.getCurrentFrame(), anim.getNextFrame());
				return new CompositeRenderingElement(
						new StaticElement<Float>(ScalarParameter.TRANSITION, t),
						new StaticElement<FloatBuffer>(VertexParameter.VERTEX, model.getVertexes()),
						new StaticElement<FloatBuffer>(VertexParameter.SUBSEQUENT_VERTEX, model.getNextVertexes())
				);
			} else {
				return null;
			}
		}		
	}

	public static final DynamicRenderingElement TRANSITION_ELEMENT = new DynamicRenderingElement() {
		@Override
		public RenderingElement produceElement(Entity e) {
			Animation anim = e.getComponent(Animation.class);
			if (anim != null) {
			    float t = anim.getProgress();
				return new StaticElement<Float>(ScalarParameter.TRANSITION, t);
			} else {
				return null;
			}
		}		
	};
}
