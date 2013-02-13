package net.rowf.sigilia.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;


public class PerspectiveRenderer implements Renderer {
	private RenderableProvider provider;
	private Camera             camera;
	private float[]            viewMatrix = new float[16];
    private float[]            fovMatrix = new float[16];
    private float[]            eyeMatrix = new float[16];
	private int                width, height;
	
	public PerspectiveRenderer(RenderableProvider provider) {
		super();
		this.provider = provider;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		computeViewMatrix();
		for (Renderable r : provider.getOrderedRenderables(camera)) {
			r.render(viewMatrix);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		this.width  = width;
		this.height = height;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.0f, 0.0f, 0.25f, 1.0f);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glCullFace(GLES20.GL_BACK);
	}
	
	private void computeViewMatrix() {        
        float   aspect = (float) width / (float) height;
        float[] eye    = camera.getEye();
        float[] up     = camera.getUp();
        float[] look   = camera.getLookAt();
                
        Matrix.frustumM(fovMatrix, 0, -aspect/2f, aspect/2f, -.5f, .5f, 1, 100);
        Matrix.setLookAtM(eyeMatrix, 0, eye[0], eye[1], eye[2], look[0], look[1], look[2], up[0], up[1], up[2]);
        Matrix.multiplyMM(viewMatrix, 0, fovMatrix, 0, eyeMatrix, 0);
	}
	
	public interface RenderableProvider {
		public Iterable<Renderable> getOrderedRenderables(Camera camera);
	}
	
	public interface Renderable {
		public void render (float[] viewMatrix);
	}

	public interface Camera {
		public float[] getEye();
		public float[] getLookAt();
		public float[] getUp();
	}
}
