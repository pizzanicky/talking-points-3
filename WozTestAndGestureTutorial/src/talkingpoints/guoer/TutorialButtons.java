package talkingpoints.guoer;

 
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Toast;


public class TutorialButtons extends GestureUI {
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
 		
		pageName = new String("Gesture number six. buttons." +
				" At the right side of your phone, there is one long button." +
				" On a list or menu of items, pressing the top half of the button will take you one up," +
				" and pressing the bottom half will take you one down.+" +
				"use the buttons to scroll up and down the list." +
				" Scroll to ÔappleÕ and doubletap to select ÔappleÕ. " );
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
		
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			Intent intent2 = new Intent(TutorialButtons.this, GateWay.class);
			startActivity(intent2);;
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
		flag = true; 
		return true;

	}
	 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if we get any key, clear the Splash Screen
		if(count2>10)
		{
			releaseSoundEffect();
			playSound(TUTORIAL_SUCCEEDED);
			try {
				
				Thread.sleep(2000);
		
			}catch(InterruptedException e11){
				e11.printStackTrace();
			}
			sayPageName("You have successfully performed double tap");
			try {
				
				Thread.sleep(3000);
		
			}catch(InterruptedException e12){
				e12.printStackTrace();
			}
			Intent intent = new Intent(TutorialButtons.this, TutorialShake.class);
			startActivity(intent);
		}
		count2++;
		
		if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
 
			flag3=true; 
			
			if(flag2)
			{
				if(count1==options.size()-1)
					count1=1;
				else 
					count1+=2;
				
				flag2=false; 
			}
			
			if(count1==options.size()){
				
				count1=0;
				
			}
			
			if(count1<options.size()) //count<.size() 
			{
				
				// viewA.setText("Down"+count1);
     			message = options.get(count1);
 			
 
 				selected = count1;
  				text.setText(message);
 
 				this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
 					null);
 			
 				releaseSoundEffect();
 				playSound(ITEM_BY_ITEM);
 			
  
 				if(count1==(options.size()-1)) 
				{
					
					if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
					{
						try {
							
							Thread.sleep(1400);
							releaseSoundEffect();
							playSound(EDGE);
							
						}catch(InterruptedException e20){
							e20.printStackTrace();
						}
			 
					}
					else if(options.get(count1).length()>16)
					{
						try {
							
							Thread.sleep(2100);
							releaseSoundEffect();
							playSound(EDGE);
							
						}catch(InterruptedException e21){
							e21.printStackTrace();
						}
			 
					}else 
					{
						try {
							
							Thread.sleep(700);
							releaseSoundEffect();
							playSound(EDGE);
							
						}catch(InterruptedException e23){
							e23.printStackTrace();
						}
					}

				} 
		    
				count1++;

			}
 
		}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
		
			flag2=true;
			
			if(flag3)
			{
				if(count1==1)
					count1=options.size()-1;
//				else if(count==5)
//					count=3;
				else 
					count1-=2;
				
				flag3=false;
			}
			
			if(count1!=0)
			{
				if(count1==options.size()){
			
				
					count1=options.size()-2;
				}	
			}

			
			if(count1==0){
			//	this.sayPageName("0");
				
				message = options.get(count1);
				

				selected = count1;
				text.setText(message);

				this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
					null);
			    
				
				releaseSoundEffect();
				playSound(ITEM_BY_ITEM);
			
		    //	 viewA.setText("UP"+count1);
				count1=options.size()-1;
				
			}
			else if(count1<options.size())
				{
				
				
				message = options.get(count1);
			

				selected = count1;
				text.setText(message);

				this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
					null);
			
				releaseSoundEffect();
				playSound(ITEM_BY_ITEM);
			

				if(count1==(options.size()-1)) 
				{
					
					if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
					{
						try {
							
							Thread.sleep(1400);
							releaseSoundEffect();
							playSound(EDGE);
							
						}catch(InterruptedException e30){
							e30.printStackTrace();
						}
			 
					}
					else if(options.get(count1).length()>16)
					{
						try {
							
							Thread.sleep(2100);
							releaseSoundEffect();
							playSound(EDGE);
							
						}catch(InterruptedException e31){
							e31.printStackTrace();
						}
			 
					}else 
					{
						try {
							
							Thread.sleep(700);
							releaseSoundEffect();
							playSound(EDGE);
							
						}catch(InterruptedException e32){
							e32.printStackTrace();
						}
					}

				} 
			//	 viewA.setText("Up"+count1);
		   
				count1--;

			}
			
 
		}

		return true;// return super.onKeyDown(keyCode, event);
	}

}

