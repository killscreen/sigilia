package net.rowf.sigilia;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.rowf.sigilia.background.Background;
import net.rowf.sigilia.particle.MovingParticle;
import net.rowf.sigilia.particle.Particle;
import net.rowf.sigilia.renderer.shader.Program;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;



public class ParticleRenderer implements Renderer {
	private float[] cameraMatrix = new float[16];

	
	private float w;
	private float h;
	private List<Particle> testParticles = new CopyOnWriteArrayList<Particle>();	
	
	@Override
	public void onDrawFrame(GL10 arg0) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		bg.draw(cameraMatrix);
		for (Particle testParticle : testParticles) {
			if (testParticle.stale()) testParticles.remove(testParticle);
			else testParticle.draw(cameraMatrix);			
		}
		
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int w, int h) {
		this.w = w;
		this.h = h;
		
        GLES20.glViewport(0, 0, w, h);
        
        float[] fovMatrix = new float[16];
        float[] eyeMatrix = new float[16];
        
        float aspect = (float) w / (float) h;
                
        Matrix.frustumM(fovMatrix, 0, -aspect/2f, aspect/2f, -.5f, .5f, 1, 100);
        Matrix.setLookAtM(eyeMatrix, 0, 0, 0, -1, 0, 0, 50, 0, 1, 0);
        Matrix.multiplyMM(cameraMatrix, 0, fovMatrix, 0, eyeMatrix, 0);
	}

	Program p;
	Background bg;
	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		GLES20.glClearColor(0.0f, 0.0f, 0.25f, 1.0f);
		
		p = new Program(
				"varying lowp vec4 vColor;" +
			    "uniform mat4 uMatrix;" +
			    "attribute vec4 vPosition;" +
			    "void main() {" +
			    "  vColor = vec4(abs(vPosition.y), abs(vPosition.z), 1, " +
			    "            abs(vPosition.z)*abs(vPosition.z) );" +
			    //" vColor = vec4(1,1,1,1); " +
			    "  gl_Position = uMatrix * vPosition;" +
			    "}",
			    
			    
			    "varying lowp vec4 vColor;" +
			    "precision mediump float;" +
			    "void main() {" +
			    "  gl_FragColor = vColor;" +
			    "}"
		);
		
		testParticles.add(new MovingParticle(p, 0.1f, 0.1f));
		bg = new Background(new Program(
				"varying lowp vec2 vCoord;" +
					    "uniform mat4 uMatrix;" +
					    "attribute vec2 vTexCoord;" +
					    "attribute vec4 vPosition;" +
					    "void main() {" +
					    "  vCoord = vec2(abs(vTexCoord.x), abs(vTexCoord.y));" +
					    "  gl_Position = uMatrix * vPosition;" +
					    "}",
					    
					    "uniform sampler2D uTexture;" +
					    "varying lowp vec2 vCoord;" +
					    "precision mediump float;" +
					    "void main() {" +
					    "  gl_FragColor = texture2D(uTexture, vCoord);" +
					    "}"
				), BitmapFactory.decodeResource(ParticleDemoActivity.res, R.drawable.cave_background));
		
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glCullFace(GLES20.GL_BACK);
	}

	public void addParticle(float u, float v) {
		float x = -(u/w) * 2 + 1;
		float y = -(v/h) * 2 + 1;
		testParticles.add(new MovingParticle(p, x, y));
	}
	
}
