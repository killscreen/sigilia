package net.rowf.sigilia;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import net.rowf.sigilia.renderer.PerspectiveRenderer;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Camera;
import net.rowf.sigilia.renderer.PerspectiveRenderer.Renderable;
import net.rowf.sigilia.renderer.PerspectiveRenderer.RenderableInitializer;
import net.rowf.sigilia.renderer.PerspectiveRenderer.RenderableProvider;
import net.rowf.sigilia.renderer.StandardRenderable;
import net.rowf.sigilia.renderer.model.Billboard;
import net.rowf.sigilia.renderer.model.BufferUtil;
import net.rowf.sigilia.renderer.model.Model;
import net.rowf.sigilia.renderer.shader.ParameterizedProgram;
import net.rowf.sigilia.renderer.shader.program.FlatTextureShader;
import net.rowf.sigilia.renderer.texture.Texture;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Provides an activity useful for editing meshes of 'cut-out' style models (i.e. enemies).
 * Not exposed to players.
 * @author woeltjen
 *
 */
public class EditorActivity extends Activity implements RenderableProvider, RenderableInitializer, OnTouchListener {
	public static final String VERTEX_KEY = EditorActivity.class.getPackage().getName() + ".vertex_key";
	public static final String TRIANGLE_KEY = EditorActivity.class.getPackage().getName() + ".triangle_key";
	private float[] locatorMatrix = new float[16];
	private Renderable r;
	private Model pointModel;
	private Texture pointTexture;
	private ParameterizedProgram pointShader;
	
	private Texture triangleTexture;
	
	private final List<Vertex> verts = new ArrayList<Vertex>();
	private final List<Triangle> triangles = new ArrayList<Triangle>();
	
	private Vertex selected = null;
	
	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		GLSurfaceView view = new GLSurfaceView(this);

		if (b != null) {
			rehydrateVertexes(b.getString(VERTEX_KEY));
			rehydrateTriangles(b.getString(TRIANGLE_KEY));
		}
		
		view.setEGLContextClientVersion(2);
		view.setRenderer(new PerspectiveRenderer(this, this));
		view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        view.setOnTouchListener(this);

		this.setContentView(view);
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle b) {
		super.onSaveInstanceState(b);
		b.putString(VERTEX_KEY, dehydrateVertexes());
		b.putString(TRIANGLE_KEY, dehydrateTriangles());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle b) {
		super.onRestoreInstanceState(b);
		rehydrateVertexes(b.getString(VERTEX_KEY));
		rehydrateTriangles(b.getString(TRIANGLE_KEY));
	}
	
	public void rehydrateVertexes(String state) {		
		if (state == null) return;
		verts.clear();
		String[] split = state.split(",");
		for (int i = 0; i+1 < split.length; i += 2) {
			verts.add(new Vertex(Float.parseFloat(split[i]), Float.parseFloat(split[i+1])));
		}
	}
	
	public void rehydrateTriangles(String state) {				
		Log.i("REHYDRATE", state);
		if (state == null) return;
		triangles.clear();
		String[] split = state.split(",");
		for (int i = 0; i+2 < split.length; i += 3) {
			triangles.add(new Triangle(
					verts.get(Integer.parseInt(split[i])),
					verts.get(Integer.parseInt(split[i+1])),
					verts.get(Integer.parseInt(split[i+2]))
					));
		}
	}
	
	public String dehydrateTriangles() {
		String out = "";
		for (Triangle t : triangles) {
			for (Vertex v : new Vertex[]{t.a,t.b,t.c}) {
				out+= verts.indexOf(v) + ",";
			} 
		}
		return out.substring(0, out.length() - 1);
	}
	
	public String dehydrateVertexes() {
		String out = "";
		for (Vertex v : verts) {
			out+= v.x + "," +v.y + ",";
		}
		return out.substring(0, out.length() - 1);
	}

	@Override
	public Iterable<Renderable> getOrderedRenderables(Camera camera) {
		List<Renderable> renderables = new ArrayList<Renderable>();
		
		if (r != null) {
			renderables.add(r);
		}
		
		for (Triangle t : triangles) renderables.add(t);
		synchronized(verts) {
			for (Vertex v : this.verts) renderables.add(v);
		}
		
		return renderables;
	}

	@Override
	public void initialize() {
		Matrix.setIdentityM(locatorMatrix, 0);
		Matrix.translateM(locatorMatrix, 0, 0, 0, 1);
		r = new StandardRenderable( 
				new FlatTextureShader(),
				Billboard.UNIT,
				locatorMatrix,
				new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.wizard_patch ))
				);		
		pointModel = new Billboard(0.05f);
		pointShader = new FlatTextureShader();
		pointTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		triangleTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.editor_triangle));
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized(verts) {
			switch (event.getPointerCount()) {
			case 3:
				if (true) {
					Vertex[] vertArr = new Vertex[3];
					int found = 0;
					for (int i = 0 ; i < 3; i++) {
						vertArr[i] = pointToVertex(v, event.getX(i), event.getY(i));
						for (Vertex other : verts) {
							if (vertArr[i].match(other)) vertArr[i] = other;
						}				
					}
					// Ensure all are unique, and none are novel
					if (!vertArr[0].equals(vertArr[1])
							&& !vertArr[1].equals(vertArr[2])
							&& !vertArr[2].equals(vertArr[0])
							&& verts.contains(vertArr[0])
							&& verts.contains(vertArr[1])
							&& verts.contains(vertArr[2])) {
						Triangle t = new Triangle(vertArr[0], vertArr[1],
								vertArr[2]);
						// Ensure redundant triangles aren't added
						boolean toAdd = true;
						for (Triangle other : triangles) {
							if (t.same(other)) toAdd = false;
						}
						if (toAdd) triangles.add(t);
					}
				}
				break;
			case 1:
				// Move or select a vertex
				Vertex vert = pointToVertex(v, event.getX(), event.getY());
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// Add or select the vert
					for (Vertex other : verts) {
						if (vert.match(other)) vert = other;
					}
					if (!verts.contains(vert)) {
						verts.add(vert);
					}
					selected = vert;
				}
				if (selected != null) {
					selected.x = vert.x;
					selected.y = vert.y;
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// Maybe delete vert & triangle
					// (area outside of sprite is "trash"
					if (selected != null && (
					    selected.x < -0.66f || selected.x > 0.66f ||
					    selected.y < -0.66f || selected.y > 0.66f)) {
						verts.remove(selected);
						List<Triangle> toRemove = new ArrayList<Triangle>();
						for (Triangle t : triangles) {
							if (t.contains(selected)) toRemove.add(t);
						}
						triangles.removeAll(toRemove);
						selected = null;
					}
					// Finally, clamp locations
					if (selected != null) {
						if (selected.x < -0.495f) selected.x = -0.495f;
						if (selected.x >  0.495f) selected.x =  0.495f;
						if (selected.y < -0.495f) selected.y = -0.495f;
						if (selected.y >  0.495f) selected.y =  0.495f;
	
					}
				}
			}
		}
		return true;
	}
	
	private Vertex pointToVertex(View v, float x, float y) {
		float w = v.getWidth();
		float h = v.getHeight();
		float s = Math.min(w, h);
		if (w < h) {
			y -= (h - w) / 2;
		} else if (h < w) {
			x -= (w - h) / 2;
		}
		x/=s;
		y/=s;
		x-=0.5f;
		y-=0.5f;
		return new Vertex(-x,-y);
	}


	private static final float EPSILON = 0.075f;
	private class Vertex implements Renderable {
		public float x, y;

		public Vertex(float x, float y) {
			this.x = x; this.y = y;
		}
		
		public boolean match(Vertex v) {
			return Math.abs(v.x-x) < EPSILON &&
				   Math.abs(v.y-y) < EPSILON;
		}
		
		@Override
		public void render(float[] viewMatrix) {
			float[] mat = new float[16];
			Matrix.setIdentityM(mat, 0);
			Matrix.translateM(mat, 0, x, y, 1);
			new StandardRenderable(
					pointShader,
					pointModel,
					mat,
					pointTexture
					).render(viewMatrix);
		}
	}

	private static final ShortBuffer triangleDrawOrder = BufferUtil.toBuffer(new short[] { 0 , 1 , 2 } );
	private static final FloatBuffer triangleTexOrder  = BufferUtil.toBuffer(new float[] { 0.995f,0.995f,  0,0f,  0.995f,0f } );
	private class Triangle implements Renderable, Model {
		private Vertex a, b, c;

		public Triangle(Vertex a, Vertex b, Vertex c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}
		
		public boolean same(Triangle t) {
			return t.contains(a) && t.contains(b) && t.contains(c);
		}
		
		public boolean contains(Vertex v) {
			return v.equals(a) || v.equals(b) || v.equals(c);
		}

		@Override
		public FloatBuffer getVertexes() {
			return BufferUtil.toBuffer(new float[] { a.x, a.y, 0, b.x, b.y, 0, c.x, c.y, 0 });
		}

		@Override
		public ShortBuffer getDrawingOrder() {
			return triangleDrawOrder;
		}

		@Override
		public FloatBuffer getTexCoords() {
			return triangleTexOrder;
		}

		@Override
		public int getTriangleCount() {
			return 1;
		}

		@Override
		public void render(float[] viewMatrix) {
			float[] mat = new float[16];
			Matrix.setIdentityM(mat, 0);
			Matrix.translateM(mat, 0, 0, 0, 1);
			new StandardRenderable(
					pointShader,
					this,
					mat,
					triangleTexture
					).render(viewMatrix);			
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.editor, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent poser = new Intent(this, PoserActivity.class);
		poser.putExtra(VERTEX_KEY, dehydrateVertexes());
		poser.putExtra(TRIANGLE_KEY, dehydrateTriangles());
		startActivity(poser);
		return true;
	}
	
	
}
