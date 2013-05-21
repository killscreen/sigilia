package net.rowf.sigilia.renderer.decorator;

import net.rowf.sigilia.renderer.texture.Texture;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * A DeferredTexture allows textures to be defined ahead of time,
 * but initialized when used (as definition will typically happen 
 * before the OpenGL context has been created, but initialization must 
 * occur after.)  
 * 
 * @author woeltjen
 *
 */
public class DeferredTexture extends Deferred<Texture> {
	private Bitmap bitmap;

	public DeferredTexture(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	/**
	 * Create a new DeferredTexture. This will not be uploaded to the GL 
	 * context until it is asked for. 
	 * 
	 * Note that this form of the constructor will load the 
	 * Bitmap from the specified Resources object, which may be time-intensive;
	 * this should not be constructed on the UI thread.
	 * 
	 * @param res
	 * @param id
	 */
	public DeferredTexture(Resources res, int id) {
		this(BitmapFactory.decodeResource(res, id));
	}
	
	@Override
	protected Texture create() {
		return new Texture(bitmap);
	}
}
