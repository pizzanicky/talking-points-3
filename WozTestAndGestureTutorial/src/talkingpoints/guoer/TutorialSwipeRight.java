package talkingpoints.guoer;

 
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


public class TutorialSwipeRight extends GestureUI {
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
		MenuOptions.add("Apple"); //Bert's calf ay
 		MenuOptions.add("Banana");
 		MenuOptions.add("Cherimoya");
 		MenuOptions.add("Eggfruit");
 		MenuOptions.add("Fig");
 		MenuOptions.add("Grapefruit");
 		MenuOptions.add("Ita Palm");
 		
 		
 		pageName = new String("Gesture number four swipe right.     "+
 				" The swipe right gesture is used for making Talking Points " +
 				"repeat content text or the title of the page you are on." +
 				" Priactice. " +
 				" To swipe right, press your thumb anywhere on the screen and slide it quickly to the right." +
 				" At the ding, swipe right to hear the name of the page you are on repeated. The name should be Ôred apple.Õ");
 		
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
		
			}catch(InterruptedException e31){
				e31.printStackTrace();
			}
			Intent intent2 = new Intent(TutorialSwipeRight.this, BTlist.class);
			startActivity(intent2);

			//finish();
		}else if(e4.getX() - e3.getX() >SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
 				if(count2>5)
				{
 					this.releaseSoundEffect();
 					this.playSound(TUTORIAL_SUCCEEDED);
 					try {
 						
 						Thread.sleep(1000);
 				
 					}catch(InterruptedException e32){
 						e32.printStackTrace();
 					}
					sayPageName("You have successfully performed Swipe Right");
					try {
						
						Thread.sleep(2000);
				
					}catch(InterruptedException e33){
						e33.printStackTrace();
					}
					Intent intent = new Intent(TutorialSwipeRight.this, TutorialSwipeLeft.class);
					startActivity(intent);
				}else
				{
					this.releaseSoundEffect();
					this.playSound(TUTORIAL);
					
				//	 sayPageName("swipe right");
					sayPageName("apple");
					count2++;
				}
				
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

