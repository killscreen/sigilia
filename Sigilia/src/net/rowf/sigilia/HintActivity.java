package net.rowf.sigilia;

import java.util.HashMap;
import java.util.Map;

import net.rowf.sigilia.input.gesture.DeltaSequence;
import net.rowf.sigilia.input.gesture.DirectionSet.Direction;
import net.rowf.sigilia.input.gesture.StaticDeltaSequence;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class HintActivity extends FullscreenActivity implements Runnable {
	public static final String HINT_KEY = "HINT_KEY";
	
	private static final int STEP_COUNT = 100;
	private Map<Integer, Hint> hintMap = new HashMap<Integer, Hint>();
	
	private int countDown = STEP_COUNT;
	private int currentStep = 0;	
	private float rx = 0;
	private float ry = 0;
	private Direction[] steps;
	
	private ImageView hand;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		populateHintMap();
		
		Integer hintId = getIntent().getExtras().getInt(HINT_KEY);
		
		Hint hint = hintMap.get(hintId);
		
		if (hint != null) {
			setContentView(R.layout.hint);
			hand = (ImageView) findViewById(R.id.floating_hand);
			hand.scrollBy(hint.startX, hint.startY);
			hand.setVisibility(View.INVISIBLE);
			currentStep = 0;			
			steps =  hint.sequence.getSampledDelta(STEP_COUNT);
			
			ImageView parchment = (ImageView) findViewById(R.id.hint_parchment);
			parchment.setImageResource(hint.hintDrawable);
			
			animate();
		} else {
			// No hint - go away!
			finish();
		}
	}
	
	private void animate() {
		new Handler().postDelayed(this, 15);
	}

	@Override
	public void run() {
		if (countDown > 0) {
			if (--countDown <= 0) {
				hand.setVisibility(View.VISIBLE);
			} else {
				animate();
				return;
			}
		}
		if (currentStep < STEP_COUNT) {						
			Direction d = steps[currentStep];
			rx +=  d.getX() * 5f;
			ry +=  d.getY() * 5f;
			hand.scrollBy((int) rx, (int) ry);
			rx -= (int) rx;
			ry -= (int) ry;
			currentStep++;
			if (currentStep >= STEP_COUNT) {
				hand.setVisibility(View.INVISIBLE);
				countDown = STEP_COUNT;
			}
			animate();
		} else {
			finish();
		}
	}

	private void populateHintMap() {
		// TODO: Should define initial X/Y in res/values
		hintMap.put(R.id.scenario_1, new Hint(R.drawable.parchment_goblin, 75,   20, StaticDeltaSequence.BOLT));
		hintMap.put(R.id.scenario_2, new Hint(R.drawable.parchment_archer,  0, -150, StaticDeltaSequence.FIRE));
	}
	
	private static class Hint {
		public int hintDrawable;
		public int startX;
		public int startY;
		public DeltaSequence sequence;
		public Hint(int hintDrawable, int startX, int startY,
				DeltaSequence sequence) {
			super();
			this.hintDrawable = hintDrawable;
			this.startX = startX;
			this.startY = startY;
			this.sequence = sequence;
		}
	}
	
}
