package gesture.tutorial;

 
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


public class TutorialFinished extends GestureUI {
	private ArrayList<String> MenuOptions;
 
 

	private static final int SWIPE_MIN_DISTANCE = 120;
 	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
 
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		 
		MenuOptions = new ArrayList<String>();
		MenuOptions.add("Apple"); //Bert's calf ay
 		MenuOptions.add("Banana");
 		MenuOptions.add("Cherimoya");
 		MenuOptions.add("Eggfruit");
 		MenuOptions.add("Fig");
 		MenuOptions.add("Grapefruit");
 		MenuOptions.add("Ita Palm");
 		
 		pageName = new String("This concludes our tutorial. At the ding," +
 				" Talking Points will automatically return to Talking Points Home. " +
 				"If at any point you are not sure where you are within Talking Points," +
 				" you can swipe left repeatedly until returning to Talking Points home." +
 				" Thank you.");
 		
		super.onCreate(savedInstanceState, MenuOptions);
 

		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
			 
				return true;
			}

			public boolean onDoubleTapEvent(MotionEvent e) {
				return false;
			}

			public boolean onSingleTapConfirmed(MotionEvent e) {
				return false;
			}

		});
		
		
	}
	
	@Override
	public boolean onFling(MotionEvent e3, MotionEvent e4, float velocityX,
			float velocityY) {
 	
		// TODO Auto-generated method stub
		if (e3.getX() - e4.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			//swipe left
			this.sayPageName("Finish Tutorial");
			try {
				
				Thread.sleep(2000);
		
			}catch(InterruptedException e11){
				e11.printStackTrace();
			}
			Intent intent2 = new Intent(TutorialFinished.this, gt.class);
			startActivity(intent2);

			//finish();
		}else if(e4.getX() - e3.getX() >SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
 			 
				
			}else if(e3.getY() - e4.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			 

			}else if(e4.getY() - e3.getY() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				 
				 
	 
	 
				
				}
		
		
		return false;
	}

	public void say(String message) {
		this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	@Override	
	public void onLongPress(MotionEvent e5) {
	 
	}
 
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
 		return true;

	}
	 


}

