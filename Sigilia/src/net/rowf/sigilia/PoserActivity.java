package net.rowf.sigilia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Provides an activity for 'posing' paper cutouts. Used for asset creation; 
 * not exposed to players.
 * @author woeltjen
 *
 */
public class PoserActivity extends Activity implements RenderableProvider, RenderableInitializer, OnTouchListener, Model {
	private float[] locatorMatrix = new float[16];
	private Renderable r;
	private Model pointModel;
	private ParameterizedProgram pointShader;
	private Texture pointTexture;
	
	private Texture triangleTexture;
	
	private Map<String, List<Vertex>> keyframes = new HashMap<String, List<Vertex>>();
	private final List<Vertex> verts = new ArrayList<Vertex>();
	private final List<Vertex> baseVerts = new ArrayList<Vertex>();
	private final List<Triangle> triangles = new ArrayList<Triangle>();

	private ShortBuffer triangleOrder;
	private FloatBuffer texCoords;
	private FloatBuffer currentMesh;
	
	private Vertex selected;
	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		GLSurfaceView view = new GLSurfaceView(this);

	
		String vertString = getIntent().getExtras().getString(EditorActivity.VERTEX_KEY);
		String triString  = getIntent().getExtras().getString(EditorActivity.TRIANGLE_KEY);
		if (vertString != null && triString != null) {
			rehydrateVertexes(vertString);
			rehydrateTriangles(triString);
		}
		makeKeyframe("base");
		
		view.setEGLContextClientVersion(2);
		view.setRenderer(new PerspectiveRenderer(this, this));
		view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        view.setOnTouchListener(this);

		this.setContentView(view);
		
	}
	
	public void updateMesh() {
		int i = 0;
		for (Vertex v : verts) {
			currentMesh.put(i++, v.x);
			currentMesh.put(i++, v.y);
			currentMesh.put(i++, 0);
		}
	}
	
	public void makeKeyframe(String name) {
		List<Vertex> frame = new ArrayList<Vertex>();
		for (Vertex v : verts) frame.add(new Vertex(v.x, v.y));
		keyframes.put(name, frame);			
	}
	
	public void loadKeyframe(String name) {
		
		List<Vertex> frame = keyframes.get(name);
		if (frame != null) {
			verts.clear();
			for (Vertex v : frame) verts.add(new Vertex(v.x, v.y));
		}
		// Point to the new array
		int j = 0;
		for (Triangle t : triangles) {
			t.a = verts.get(triangleOrder.get(j++));
			t.b = verts.get(triangleOrder.get(j++));
			t.c = verts.get(triangleOrder.get(j++));
		}
		updateMesh();
	}
	
	private void promptLoadKeyframe() {
		final Spinner spinner = new Spinner(this);
		ArrayAdapter<String> options = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, keyframes.keySet().toArray(new String[]{}));
		spinner.setAdapter(options);
		
		AlertDialog.Builder popup = new AlertDialog.Builder(this);
		
		popup.setView(spinner);
		popup.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String choice = spinner.getSelectedItem().toString();
				if (choice != null && choice.length() >= 1) {
					loadKeyframe(choice);
				}
			}			
		});
		popup.setNegativeButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
			}			
		});
		
		popup.show();
		
	}
	
	private void promptKeyframe() {
		final EditText textEntry = new EditText(this);
		
		AlertDialog.Builder popup = new AlertDialog.Builder(this);
		
		popup.setView(textEntry);
		popup.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String text = textEntry.getText().toString();
				if (text != null && text.length() >= 1) {
					makeKeyframe(text);
				}
			}			
		});		
		popup.setNegativeButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
			}			
		});
		
		popup.show();
	}

	private void promptSave() {
		final EditText textEntry = new EditText(this);
		
		AlertDialog.Builder popup = new AlertDialog.Builder(this);
		
		popup.setView(textEntry);
		popup.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String text = textEntry.getText().toString();
				if (text != null && text.length() >= 1) {
					File data = Environment.getExternalStorageDirectory();					
					File f = new File(data.getAbsolutePath() + "/" + text);
					save(f);
				}
			}			
		});		
		popup.setNegativeButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
			}			
		});
		
		popup.show();
	}
	
	public void rehydrateKeyframe(String state) {
		String[] kv = state.split("\t");
		List<Vertex> frameVerts = new ArrayList<Vertex>();
		String[] split = kv[1].split(",");
		for (int i = 0; i+1 < split.length; i += 2) {
			frameVerts.add(new Vertex(Float.parseFloat(split[i]), Float.parseFloat(split[i+1])));
		}
		keyframes.put(kv[0], frameVerts);
	}
	
	public void rehydrateVertexes(String state) {				
		verts.clear();
		baseVerts.clear();
		String[] split = state.split(",");
		for (int i = 0; i+1 < split.length; i += 2) {
			verts.add(new Vertex(Float.parseFloat(split[i]), Float.parseFloat(split[i+1])));
			baseVerts.add(new Vertex(Float.parseFloat(split[i]), Float.parseFloat(split[i+1])));
		}
		float[] tex = new float[split.length];
		float[] mesh = new float[split.length + split.length/2];
		int j = 0;
		for (int i = 0; i < split.length; i++) {
			float v = Float.parseFloat(split[i]);
			tex[i] = (v + 0.5f);
			if (i % 2 == 1) tex[i] = 1 - tex[i]; // Flip y to tex-space
			mesh[j++] = v;
			if (i % 2 == 1) mesh[j++] = 0; // Add z value, too
		}
		texCoords = BufferUtil.toBuffer(tex);
		currentMesh = BufferUtil.toBuffer(mesh);
	}
	
	public void rehydrateTriangles(String state) {				
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
		short[] triOrder = new short[split.length];
		for (int i = 0; i < split.length; i++) {
			triOrder[i] = Short.parseShort(split[i]);
		}
		triangleOrder = BufferUtil.toBuffer(triOrder);
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
		return dehydrateVertexes(verts);
	}
	
	public String dehydrateVertexes(List<Vertex> verts) {
		String out = "";
		for (Vertex v : verts) {
			out+= v.x + "," +v.y + ",";
		}
		return out.substring(0, out.length() - 1);
	}
	
	public void save(File file) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(getFullState());
			writer.close();
		} catch (IOException ioe) {
			Log.e("Poser", "IOException writing file: " + ioe.getMessage());
		}
	}
	
	public void load(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder b = new StringBuilder();
			String line;
			while ( (line=reader.readLine()) != null) {
				b.append(line).append('\n');
			}
			reader.close();
			restoreFullState(b.toString());
		} catch (IOException ioe) {
			Log.e("Poser", "IOException reading file: " + ioe.getMessage());
		}
		
	}
	
	private void restoreFullState(String state) {
		String[] split = state.split("\n");
		rehydrateVertexes(split[0]);
		rehydrateTriangles(split[1]);
		for (int i = 2; i < split.length; i++) {
			if (split[i].contains("\t")) {
				rehydrateKeyframe(split[i]);
			}
		}
		updateMesh();
	}
	
	private String getFullState() {
		StringBuilder out = new StringBuilder();
		out.append(dehydrateVertexes(baseVerts)).append('\n');
		out.append(dehydrateTriangles()).append('\n');
		for (Entry<String, List<Vertex>> frame : keyframes.entrySet()) {
			out.append(frame.getKey()).append('\t');
			out.append(dehydrateVertexes(frame.getValue())).append('\n');
		}
		return out.toString();
	}
	
	
	private void relax() {
		for (Triangle t : triangles) {
			float rigidity = t.rigidity * 0.1f;
			int a = verts.indexOf(t.a);
			int b = verts.indexOf(t.b);
			int c = verts.indexOf(t.c);
			relax(t.a, t.b, baseVerts.get(a), baseVerts.get(b), rigidity);
			relax(t.b, t.c, baseVerts.get(b), baseVerts.get(c), rigidity);
			relax(t.c, t.a, baseVerts.get(c), baseVerts.get(a), rigidity);
		}
	}
	
	private void relax(Vertex a, Vertex b, Vertex refA, Vertex refB, float rigidity) {
		float desiredDist = dist(refA, refB);
		float actualDist  = dist(a,b);
		float deltaX = b.x - a.x;
		float deltaY = b.y - a.y;
		
		float change = rigidity * (actualDist - desiredDist) / 2;
		
		a.x += deltaX * change;
		a.y += deltaY * change;
		b.x -= deltaX * change;
		b.y -= deltaY * change;
	}
	
	private float dist(Vertex a, Vertex b) {
		float x = a.x-b.x; float y = a.y-b.y; float z=0; //TODO: Use z!
		return FloatMath.sqrt( x*x + y*y + z*z );
	}

	@Override
	public Iterable<Renderable> getOrderedRenderables(Camera camera) {
		List<Renderable> renderables = new ArrayList<Renderable>();
		
		if (r != null)  renderables.add(r);		
		//for (Triangle t : triangles) renderables.add(t);
		for (Vertex v : this.verts) renderables.add(v);
		
		return renderables;
	}

	@Override
	public void initialize() {
		Matrix.setIdentityM(locatorMatrix, 0);
		Matrix.translateM(locatorMatrix, 0, 0, 0, 1);
		r = new StandardRenderable( 
				new FlatTextureShader(),
				this,
				locatorMatrix,
				new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.wizard_patch))
				);		
		pointModel = new Billboard(0.05f);
		pointShader = new FlatTextureShader();
		pointTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		triangleTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.editor_triangle));
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		relax();
		switch (event.getPointerCount()) {
		case 3:
			break;
		case 1:
			// Move or select a vertex
			Vertex vert = pointToVertex(v, event.getX(), event.getY());
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// Add or select the vert
				for (Vertex other : verts) {
					if (vert.match(other)) vert = other;
				}
				selected = vert;
			}
			if (selected != null) {
				selected.x = vert.x;
				selected.y = vert.y;
			}
		}
		updateMesh();
		
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
		public float x, y, z;

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
		private float rigidity = 1;

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
	public FloatBuffer getVertexes() {
		return currentMesh;
	}


	@Override
	public ShortBuffer getDrawingOrder() {
		return triangleOrder;
	}


	@Override
	public FloatBuffer getTexCoords() {
		return texCoords;
	}


	@Override
	public int getTriangleCount() {
		return triangles.size();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.poser, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.poser_snap:
			promptKeyframe();
			break;
		case R.id.poser_keyframe:
			promptLoadKeyframe();
			break;
		case R.id.poser_reset:
			loadKeyframe("base");
			break;
		case R.id.poser_save:
			promptSave();
			break;
		}
		return true;
	}

	
}
