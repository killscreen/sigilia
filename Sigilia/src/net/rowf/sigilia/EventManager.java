package net.rowf.sigilia;

import net.rowf.sigilia.game.component.metadata.Event;
import net.rowf.sigilia.game.engine.EventEngine.EventListener;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;

public class EventManager implements EventListener {
	private Handler handler;
	private Vibrator vibrator;
	
	public EventManager(Context context) {
		super();		
		this.handler = new Handler(context.getMainLooper());
		this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public void dispatch(Event e) {
		switch (e) {
		case PLAYER_HIT:
			handler.post(new VibrateEvent(50));
			break;
		case PLAYER_KILLED:
			handler.post(new VibrateEvent(1000));
			break;					
		}
	}

	private class VibrateEvent implements Runnable {
		private long time;

		public VibrateEvent(long time) {
			super();
			this.time = time;
		}

		@Override
		public void run() {
			if (vibrator != null) {
				Log.d(EventManager.class.getName(), "Trigger vibration for " + time);
				vibrator.vibrate(time);
			}
		}
	}
}
