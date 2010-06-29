package talkingpoints.guoer;

 
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


public class TutorialSwipeLeft extends GestureUI {
	private ArrayList<String> MenuOptions;
	private String message1;
	WozParser p; 
	byCoordinateParser p1;
	private static String message; 
 
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private static int count1=0;
    private static int count2=0;
    private static boolean flag=false;
    private static boolean flag2=false;
    private static boolean flag3=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MenuOptions = new ArrayList<String>();
	    pageName = new String("Gesure Number five. Swipe left.  "+
	    		"The swipe left gesture is used for making Talking Points go back to the previous page. " +
	    		"Practice. " +
	    		"To swipe left, press your thumb anywhere on the screen and slide it quickly to the left. " +
	    		"At the ding, swipe left to return to the fruit list.");
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
			if(count2>5)
			{
				this.releaseSoundEffect();
				this.playSound(TUTORIAL_SUCCEEDED);
				try {
					
					Thread.sleep(1000);
			
				}catch(InterruptedException e11){
					e11.printStackTrace();
				}
				sayPageName("You have successfully performed Swipe Right");
				try {
					
					Thread.sleep(3000);
			
				}catch(InterruptedException e12){
					e12.printStackTrace();
				}
				Intent intent = new Intent(TutorialSwipeLeft.this, TutorialButtons.class);
				startActivity(intent);
			}else
			{
				this.releaseSoundEffect();
				this.playSound(TUTORIAL);
				
				sayPageName("swipe left");
				count2++;
			}
			

			//finish();
		}else if(e4.getX() - e3.getX() >SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			//swipe left
			this.sayPageName("Finish Tutorial");
			try {
				
				Thread.sleep(2000);
		
			}catch(InterruptedException e31){
				e31.printStackTrace();
			}
			Intent intent2 = new Intent(TutorialSwipeLeft.this, BTlist.class);
			startActivity(intent2);

		}else if(e3.getY() - e4.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			
				
		    //  viewA.setText("-" + "Fling up?" + "-");

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
		// TODO Auto-generated method stub
		
	//s	if(getLongPressTimeout()
	/*	try{	
			
			message = "bye";
			say(message);
			finish();
		
		}catch(Exception e1){
		//nothing 
		}*/
	}
 
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		flag = true; 
		return true;

	}
	 


}

