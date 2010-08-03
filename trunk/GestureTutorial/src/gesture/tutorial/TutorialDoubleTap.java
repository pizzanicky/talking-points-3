package gesture.tutorial;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


public class TutorialDoubleTap extends GestureUI {
	private ArrayList<String> MenuOptions;
  
 
 
	
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private static int count1=0;
    private static boolean flag=false;
    private static boolean flag2=false;
    private static boolean flag3=false;
    private static boolean flag4=false; 
    private static int count2=0; 
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		MenuOptions = new ArrayList<String>();
		MenuOptions.add("orange"); //Bert's calf ay
 		MenuOptions.add("banana");
 		MenuOptions.add("apple");
 		MenuOptions.add("strawberry");

 		pageName = new String("Gesture number two. Double Tap. The double tap gesture is used for selecting an item from a list. " +
 				" After you hear a list of items and identify one you want to select, tap your thumb two times in a row," +
 				" in quick succession anywhere on the screen. Taps must be in the same spot." +
 				"practice." +
 				"Slide to hear a list of fruit (orange, banana, apple, strawberry) and double tap to select Ôapple.");
		super.onCreate(savedInstanceState, MenuOptions);
		
		 

		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
//				if(flag4)
//				{   
//					MenuOptions.clear();
//					
//					releaseSoundEffect();
//					playSound(TUTORIAL_SUCCEEDED);
//					 
//					try {
//						
//						Thread.sleep(2000);
//				
//					}catch(InterruptedException e11){
//						e11.printStackTrace();
//					}
//					
//					sayPageName("You have successfully performed double tap");
//					try {
//						
//						Thread.sleep(2000);
//				
//					}catch(InterruptedException e12){
//						e12.printStackTrace();
//					}
//					
//					 
//					Intent intent = new Intent(TutorialDoubleTap.this, TutorialTrackball.class);
//					startActivity(intent);
//					
//					flag4=false; 
//				}
//				else 
//				{
					switch (GestureUI.selected) {
					case 0:
						releaseSoundEffect();
						playSound(TUTORIAL);
						sayPageName("You've selected orange. Please select Apple.");
						break;
					case 1:
						releaseSoundEffect();
						playSound(TUTORIAL);
						sayPageName("You've selected banana. Please select Apple.");
 						break;
					case 2:
						releaseSoundEffect();
						playSound(TUTORIAL);
						sayPageName("You've selected apple.");
//						MenuOptions.clear();
//						try {
//							
//							Thread.sleep(2000);
//					
//						}catch(InterruptedException e10){
//							e10.printStackTrace();
//						}
//						
						releaseSoundEffect();
						playSound(TUTORIAL_SUCCEEDED);
						 
						try {
							
							Thread.sleep(2000);
					
						}catch(InterruptedException e11){
							e11.printStackTrace();
						}
						
						sayPageName("You have successfully performed double tap");
						try {
							
							Thread.sleep(2000);
					
						}catch(InterruptedException e12){
							e12.printStackTrace();
						}
						
						 
						Intent intent = new Intent(TutorialDoubleTap.this, TutorialTrackball.class);
						startActivity(intent);
						flag4=true; 
						break;
					case 3:
						releaseSoundEffect();
						playSound(TUTORIAL);
						sayPageName("You've selected strawberry. Please select Apple.");
 						break;
					
					}
// 			}
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
			Intent intent2 = new Intent(TutorialDoubleTap.this, gt.class);
			startActivity(intent2);

 		}else if(e4.getX() - e3.getX() >SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		   //   viewA.setText("-" + "Fling Right" + "-");
				this.sayPageName();

		}else if(e3.getY() - e4.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
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
											
										}catch(InterruptedException e32){
											e32.printStackTrace();
										}
							 
									}
									else if(options.get(count1).length()>16)
									{
										try {
											
											Thread.sleep(2100);
											releaseSoundEffect();
											playSound(EDGE);
											
										}catch(InterruptedException e33){
											e33.printStackTrace();
										}
							 
									}else 
									{
										try {
											
											Thread.sleep(700);
											releaseSoundEffect();
											playSound(EDGE);
											
										}catch(InterruptedException e34){
											e34.printStackTrace();
										}
									}
	 
								} 
							//	 viewA.setText("Up"+count1);
						   
								count1--;
				
							}
						}
						
					 
	 
					 flag = false;
				 }
				
		    //  viewA.setText("-" + "Fling up?" + "-");

			}else if(e4.getY() - e3.getY() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
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
		
		
		return false;
	}

	public void say(String message) {
		this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	@Override	
	public void onLongPress(MotionEvent e5) {
		// TODO Auto-generated method stub
 
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		flag = true; 
		return true;

	}
	 


}

