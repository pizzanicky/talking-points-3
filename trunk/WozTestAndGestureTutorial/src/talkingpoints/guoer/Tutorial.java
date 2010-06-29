package talkingpoints.guoer;

 
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Toast;


public class Tutorial extends GestureUI {
	private ArrayList<String> MenuOptions;
	private String message1;
	WozParser p; 
	byCoordinateParser p1;
	private static String message; 
	
	 
	
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private static int count1=0;
    private static boolean flag=false;
    private static boolean flag2=false;
    private static boolean flag3=false;

    private static int count2=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	 
 		 pageName = new String("This tutorial will describe the seven gestures that you need to use Talking Points successfully." +
 		 		" It will give you a chance to practice each gesture." +
 		 		" You must make each gesture correctly to practice the next one." +
 		 		"  Swipe left at any time to exit the tutorial and return to Talking Points Home.");
 	
		 

 		 super.onCreate(savedInstanceState, MenuOptions);
		//this.sayPageName("This tutorial will describe the seven gestures you need and prompt you to try them. After trying each one, the tutorial will tell you if you have made the gesture successfully. You must successfully make a gesture to proceed of the next. Swipe left at any time to exit tutorial and return to Talking Points Home.");
		
		
//		Intent intent = new Intent(Tutorial.this, TutorialSlide.class);
//		startActivity(intent);
		
 	//	this.sayPageName("Please press the screen long to start the tutorial");
//		
	 
		
		
		
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
		
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			Intent intent2 = new Intent(Tutorial.this, BTlist.class);
			startActivity(intent2);
 		
		}else if(e4.getX() - e3.getX() >SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
 		
				this.sayPageName();

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
		 try {
				
				Thread.sleep(2000);
			
				}catch(InterruptedException e21){
				e21.printStackTrace();
			 }
				
		Intent intent = new Intent(Tutorial.this, TutorialSlide.class);
 		startActivity(intent);
	}
 
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		try {
			
			Thread.sleep(2000);
		
			}catch(InterruptedException e22){
			e22.printStackTrace();
		 }
			
	Intent intent = new Intent(Tutorial.this, TutorialSlide.class);
		startActivity(intent);		
		return true;

	}
	 


}

