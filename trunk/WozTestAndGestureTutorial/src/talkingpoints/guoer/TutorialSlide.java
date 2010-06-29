package talkingpoints.guoer;

 
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


public class TutorialSlide extends GestureUI {
	private ArrayList<String> MenuOptions;
	private String message1;
	WozParser p; 
	byCoordinateParser p1;
	private static String message; 
	
	private static String GET_COORDINATE = "http://talkingpoints.dreamhosters.com/maps_test/point.xml";
	private static String BY_COORDINATE = "http://app.talking-points.org/locations/by_coordinates/";
	// private void sayPageName() {
	// this.mTts.speak(PageName, TextToSpeech.QUEUE_FLUSH, null);
	// }
	
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private static int count1=0;
    private static boolean flag=false;
    private static boolean flag2=false;
    private static boolean flag3=false;
    private static boolean flag4=false; 

    private static int count2=0;
    private static int count0=0; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		MenuOptions = new ArrayList<String>();
		MenuOptions.add("orange"); //Bert's calf ay
 		MenuOptions.add("banana");
 		MenuOptions.add("apple");
 		MenuOptions.add("strawberry");
 		 
 		
 		
 		pageName = new String("Gesture number one. Slide." + "Description"+
 				"The slide gesture lets you scroll up or down a list, one item at a time. " +
 				" Quickly slide your thumb upward or downward, anywhere on the screen.  " +
 				"Be careful not to press too hard or hold it for too long."+
 				"practice. " +
 				"Slide down to hear a list of fruit");
 		
		super.onCreate(savedInstanceState, MenuOptions);
		

		
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
			Intent intent2 = new Intent(TutorialSlide.this, BTlist.class);
			startActivity(intent2);
 		
		}else if(e4.getX() - e3.getX() >SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
 		
				this.sayPageName();

		}else if(e3.getY() - e4.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			
			if(flag4)
			{
				
			//	this.sayPageName("up");
				 if(count2>3)
					{
					 
					    this.releaseSoundEffect();
					    this.playSound(TUTORIAL_SUCCEEDED);
					 	try {
							
							Thread.sleep(1000);
					
						}catch(InterruptedException e20){
							e20.printStackTrace();
						}
						this.sayPageName("You have successfully performed Fling");
						try {
							
							Thread.sleep(2000);
					
						}catch(InterruptedException e21){
							e21.printStackTrace();
						}
						Intent intent = new Intent(TutorialSlide.this, TutorialDoubleTap.class);
						startActivity(intent);
					}
				 else 
				 {
					 count2++;
					 
					 if(flag)
					 {
						 flag2=true;
							
						 if(options.size()!=0){
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
												
											}catch(InterruptedException e31){
												e31.printStackTrace();
											}
								 
										}
										else if(options.get(count1).length()>16)
										{
											try {
												
												Thread.sleep(2100);
												releaseSoundEffect();
												playSound(EDGE);
												
											}catch(InterruptedException e32){
												e32.printStackTrace();
											}
								 
										}else 
										{
											try {
												
												Thread.sleep(700);
												releaseSoundEffect();
												playSound(EDGE);
												
											}catch(InterruptedException e33){
												e33.printStackTrace();
											}
										}
		 
									} 
								//	 viewA.setText("Up"+count1);
							   
									count1--;
					
								}
							}
						
					 
	 
						 flag = false;
					 }
				 }
			}
		    //  viewA.setText("-" + "Fling up?" + "-");

			}else if(e4.getY() - e3.getY() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				if(count0>3)
				{

				 	this.releaseSoundEffect();
 				    this.playSound(TUTORIAL_SUCCEEDED);
					try {
						
						Thread.sleep(1000);
				
					}catch(InterruptedException e201){
						e201.printStackTrace();
					}
					this.sayPageName("Practice, Now slide up to hear the list of fruit again.");
					flag4=true; 
				}
				else{
					count0++;
					
					 if(flag)
					 {
						 
						 flag3=true; 
						 if(options.size()!=0){
							 
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
												
											}catch(InterruptedException e41){
												e41.printStackTrace();
											}
								 
										}
										else if(options.get(count1).length()>16)
										{
											try {
												
												Thread.sleep(2100);
												releaseSoundEffect();
												playSound(EDGE);
												
											}catch(InterruptedException e42){
												e42.printStackTrace();
											}
								 
										}else 
										{
											try {
												
												Thread.sleep(700);
												releaseSoundEffect();
												playSound(EDGE);
												
											}catch(InterruptedException e43){
												e43.printStackTrace();
											}
										}
		 
									} 
							    
									count1++;
			
								}
						 }
		 
						 flag = false;
					 }
					 
		 
					}
				
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

