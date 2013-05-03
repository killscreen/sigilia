package net.rowf.sigilia.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.rowf.sigilia.renderer.PerspectiveRenderer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchInputListener implements OnTouchListener, TouchInput {
	private PerspectiveRenderer perspectiveRenderer;
	private Touch latest;
	
	public TouchInputListener(PerspectiveRenderer perspectiveRenderer) {
		super();
		this.perspectiveRenderer = perspectiveRenderer;
	}

	@Override
	public List<Touch> getPendingEvents() {
		Touch t = latest;
		latest = null;
		return t == null ? Collections.<Touch>emptyList() : Arrays.<Touch>asList(t);
//		List<Touch> touches = new ArrayList<Touch>();
//		while ( (t = touchQueue.poll()) != null) {
//			touches.add(t);
//		}
//		return touches;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			latest = TouchInput.RELEASE;
		} else {
			float downTime  = ((float) motionEvent.getDownTime()) / 1000f;
			float width     = (float) view.getWidth();
			float height    = (float) view.getHeight();
			float outWidth  = perspectiveRenderer.getWidth();
			float outHeight = perspectiveRenderer.getHeight(); 
			for (int i = 0; i < motionEvent.getPointerCount(); i++) {
				float x = (motionEvent.getX(i) / width ) * outWidth  - (outWidth /2f);
				float y = (motionEvent.getY(i) / height) * outHeight - (outHeight/2f);
				//touchQueue.add(new Touch(-x, -y, downTime));
				latest = new Touch(-x, -y, downTime);
			}
		}
		return true;
	}

}
