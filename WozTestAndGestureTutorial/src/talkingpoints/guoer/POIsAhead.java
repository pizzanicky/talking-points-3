package talkingpoints.guoer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.widget.Toast;

public class POIsAhead extends GestureUI implements SensorEventListener {
	// private ArrayList<String> POIs;;
	public static ArrayList<String> NamesAhead;
	public static ArrayList<String> MacAddrAhead;
	public static ArrayList<String> TPIDAhead;
	private SensorManager sm = null;
	private Sensor compass;
	//private final double range = 30.0;
	private final double range = 15.0;

	private float[] values;
	private static float angle;
	
//	private static final int SWIPE_MIN_DISTANCE = 120;
//	private static final int SWIPE_MAX_OFF_PATH = 250;
//	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	
	private static final int SWIPE_MIN_DISTANCE = 10; //120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static final int CHECK_DISTANCE =100; 
	
    private static boolean flag = false;
    private static boolean flag2 = false;
    private static boolean flag3 = false;
    private static boolean flagForScrolling=false; 

    private float FirstX;
    private float FirstY;
    private float LastX;
    private float LastY;
    
    private static int count1=0;
    private static int countGesture=0;


	public void onCreate(Bundle savedInstanceState) {

 		pageName = "Finding locations in a chosen direction. Press trackball to hear locations.";
		super.onCreate(savedInstanceState);
		MacAddrAhead = new ArrayList<String>();
		NamesAhead = new ArrayList<String>();
		TPIDAhead = new ArrayList<String>();
 
		sm = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
		compass = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sm.registerListener(this, compass, SensorManager.SENSOR_DELAY_FASTEST);

		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
				
				if(options.size()!=0){
					
					finish();
					
					releaseSoundEffect();
					playSound(NEXT_PAGE);
					
					Intent intent = new Intent(POIsAhead.this, POImenu.class);
				
				//	Toast.makeText(getApplicationContext(), "DoubleTap", Toast.LENGTH_SHORT).show();
					intent.putExtra("tpid", POIsAhead.TPIDAhead
						.get(GestureUI.selected));
//					intent.putExtra("POIname", POIsAhead.NamesAhead
//						.get(GestureUI.selected));
					intent.putExtra("POIname", options
							.get(GestureUI.selected));
					startActivity(intent);  
				}else if(options.size()==0)
				{
					sayPageName("Please press the trackball to hear locations");
				}
				
				return true;
				
			}
			public boolean onDoubleTapEvent(MotionEvent e) {
				return false;
			}

			public boolean onSingleTapConfirmed(MotionEvent e) {
				countGesture++;
				
				if(countGesture==2)
				{	
					
 					countGesture=0;
 					if(options.size()!=0){
 						
 						finish();
 						
 						releaseSoundEffect();
						playSound(NEXT_PAGE);
						
 						
 						Intent intent = new Intent(POIsAhead.this, POImenu.class);
 					
 					//	Toast.makeText(getApplicationContext(), "DoubleTap", Toast.LENGTH_SHORT).show();
 						intent.putExtra("tpid", POIsAhead.TPIDAhead
 							.get(GestureUI.selected));
// 						intent.putExtra("POIname", POIsAhead.NamesAhead
// 							.get(GestureUI.selected));
 						intent.putExtra("POIname", options
 								.get(GestureUI.selected));
 						startActivity(intent);  
 					}else if(options.size()==0)
 					{
 						sayPageName("Please press the trackball to hear locations");
 					}
				}
				return false;
			}

		});

	}

	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			this.options.clear();
			MacAddrAhead.clear();
			TPIDAhead.clear();
			NamesAhead.clear();
			
			
			Double cur_angle = new Double(angle);
			for (int i = 0; i < AngleCalculator.NearbyAngle.size(); i++) {
				if ((Math.abs(cur_angle - AngleCalculator.NearbyAngle.get(i)) < range)
						|| ((360 - cur_angle)
								+ AngleCalculator.NearbyAngle.get(i) < range)
						|| (cur_angle
								+ (360 - AngleCalculator.NearbyAngle.get(i)) < range)) {
					
					//ground floor 
			 			
						if((byCoordinateParser.floor.get(i)==-1)&&((byCoordinateParser.distance.get(i)*5280)<60.00))
						{
							this.options.add(byCoordinateParser.name.get(i));
		 		//	Toast.makeText(this,"name::"+byCoordinateParser.name.get(i),Toast.LENGTH_SHORT).show();
							MacAddrAhead.add(byCoordinateParser.mac.get(i)); 
							TPIDAhead.add(byCoordinateParser.tpid.get(i));
						}
						
				}
			}
		//	NamesAhead = this.options;
			if (this.options.size() == 0)
				this.mTts
				.speak(
						"Nothing interesting ahead of you, keep searching by turning around",
						TextToSpeech.QUEUE_FLUSH, null);
		 	else if (this.options.size() == 1)
			    this.mTts.speak("There is" + Integer.toString(this.options.size())
						+ "location in this direction, scroll to check it out",
						TextToSpeech.QUEUE_FLUSH, null);
			else
				this.mTts.speak("There are" + Integer.toString(this.options.size())
						+ "locations in this direction, scroll to check them out",
						TextToSpeech.QUEUE_FLUSH, null);
			// } 
		
		}
		else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
			 
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
						releaseSoundEffect();
						playSound(EDGE);
	
					} 
			    
					count1++;
	
				}
			}else if(options.size()==0)
			{
				   sayPageName("Please press the trackball to hear locations");

			}
 
		}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
		 
			flag2=true;
			
			if(options.size()!=0){
				if(flag3)
				{
					if(count1==1)
						count1=options.size()-1;
	//				else if(count==5)
	//					count=3;
					else 
					{	
						if(count1!=0)
						count1-=2;
					}
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
						releaseSoundEffect();
						playSound(EDGE);
	
					} 
				//	 viewA.setText("Up"+count1);
			   
					count1--;
	
				}
			}else if(options.size()==0)
			{
				   sayPageName("Please press the trackball to hear locations");

			}
		}
		return true;

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		values = event.values;
		angle = values[0];
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent e) {

		int action = e.getAction();
    	//down 
		if(action == MotionEvent.ACTION_DOWN||action==MotionEvent.ACTION_MOVE)
			flagForScrolling=true;
		
        if(action == MotionEvent.ACTION_DOWN)
        {
         	FirstX=e.getX();
        	FirstY=e.getY();
        }
    	else if(action == MotionEvent.ACTION_UP)
    	{
    		LastX=e.getX();
    		LastY=e.getY();
    		
     		
    		if(FirstX>0||FirstY>0)
    		{
    			final float xD=Math.abs(FirstX-LastX);
    			final float yD=Math.abs(FirstY-LastY);
    			
    			try{
    				if(FirstX-LastX>SWIPE_MIN_DISTANCE&&yD< CHECK_DISTANCE)
    				{
    	    			//	      this.mTts.speak("LEFT MOTION", TextToSpeech.QUEUE_FLUSH,null);

    					releaseSoundEffect();
    					playSound(NEXT_PAGE);
    						finish();

    				}
    				else if(LastX - FirstX >SWIPE_MIN_DISTANCE&& yD< CHECK_DISTANCE) 
    					this.sayPageName();

     				   //   this.mTts.speak("Right motion", TextToSpeech.QUEUE_FLUSH,null);
     				else if(FirstY - LastY > SWIPE_MIN_DISTANCE&& xD< CHECK_DISTANCE)  
     				{
     					 // this.mTts.speak("UP Motion", TextToSpeech.QUEUE_FLUSH,null);
     					 if(flag||flagForScrolling)
     					 {
     						 upMotion();
     						flagForScrolling=false;
     					 }
     					
     				}
     				else if(LastY - FirstY > SWIPE_MIN_DISTANCE && xD< CHECK_DISTANCE)  
     				{	
     					//this.mTts.speak("down motion", TextToSpeech.QUEUE_FLUSH,null);
     					
     					 if(flag||flagForScrolling)
     					 {
     						 
     						 
     						downMotion();
     						flagForScrolling = false; 
     					 }
     				}//missed
     				else 
     				{
     					releaseSoundEffect();
						playSound(MISSED_IT);
     					this.mTts.speak("Please move your thumb in right direction", TextToSpeech.QUEUE_FLUSH,null);
     				}

    		 

    			}
    			catch (Exception e0) {
    				// nothing
    			}
    			
    		}
    	}
		
		gestureScanner.onTouchEvent(e);
		return true;

	}
	@Override
	public boolean onFling(MotionEvent e3, MotionEvent e4, float velocityX,
			float velocityY) {
//		flag=true;
//		// TODO Auto-generated method stub
//		try {
//			// if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//			// return false;
//			// right to left swipe
//			if (e3.getX() - e4.getX() > SWIPE_MIN_DISTANCE
//					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//				
//				
//			 message = "please press the screen longer to finish the talking point";
//			 say(message);
//				//finish();
//
//			}else if(e4.getX() - e3.getX() >SWIPE_MIN_DISTANCE
//					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//				this.sayPageName();
//			}
//		} catch (Exception e) {
//			// nothing
//		}
		
		final float xDistance = Math.abs(e3.getX() - e4.getX());
		final float yDistance = Math.abs(e3.getY() - e4.getY());
		// TODO Auto-generated method stub
		if (e3.getX() - e4.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY &&yDistance< CHECK_DISTANCE) {
			releaseSoundEffect();
			playSound(NEXT_PAGE);
				finish();

		}else if(e4.getX() - e3.getX() >SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY &&yDistance< CHECK_DISTANCE) {
		   //   viewA.setText("-" + "Fling Right" + "-");
				this.sayPageName();

		}else if(e3.getY() - e4.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY &&xDistance< CHECK_DISTANCE) {
			//	this.sayPageName("up");
				 if(flag)
				 {
					 upMotion();
				 }
				
		    //  viewA.setText("-" + "Fling up?" + "-");

			}else if(e4.getY() - e3.getY() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY &&xDistance< CHECK_DISTANCE) {
				 if(flag)
				 {
					 downMotion();
				 }
				 
	 
	 
				
				}
		
		
		return false;
	}
	private void upMotion()
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
					{
						if(count1!=0)
						  count1-=2;
					}
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
								
							}catch(InterruptedException e11){
								e11.printStackTrace();
							}
				 
						}
						else if(options.get(count1).length()>16)
						{
							try {
								
								Thread.sleep(2100);
								releaseSoundEffect();
								playSound(EDGE);
								
							}catch(InterruptedException e12){
								e12.printStackTrace();
							}
				 
						}else 
						{
							try {
								
								Thread.sleep(700);
								releaseSoundEffect();
								playSound(EDGE);
								
							}catch(InterruptedException e13){
								e13.printStackTrace();
							}
						}

					} 
				//	 viewA.setText("Up"+count1);
			   
					count1--;
	
				}
			} 
			
		 

		 flag = false;
	}
	private void downMotion()
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
								
							}catch(InterruptedException e21){
								e21.printStackTrace();
							}
				 
						}
						else if(options.get(count1).length()>16)
						{
							try {
								
								Thread.sleep(2100);
								releaseSoundEffect();
								playSound(EDGE);
								
							}catch(InterruptedException e22){
								e22.printStackTrace();
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
		 }  

		 flag = false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		flag = true; 
		return true;

	}
	 

}