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

public class TutorialTrackball extends GestureUI implements SensorEventListener {
	// private ArrayList<String> POIs;;
	 
	private SensorManager sm = null;
	private Sensor compass;
	//private final double range = 30.0;
 
	private float[] values;
 	
	private static final int SWIPE_MIN_DISTANCE = 120;
 	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

 
    private static int count2=0;
    

	public void onCreate(Bundle savedInstanceState) {

 		pageName = new String("Gesture Number Three. Trackball. Pressing the trackball once will tell you what locations are in the direction" +
 				" you are pointing when you use the point and find function. " +
 				"At the bottom center of the phone, you will feel a small trackball. " +
 				"Practice." +
 				" To use the trackball, locate it at the center bottom of the phone and press it once quickly.");
 		
 		
		super.onCreate(savedInstanceState);


 
		sm = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
		compass = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sm.registerListener(this, compass, SensorManager.SENSOR_DELAY_FASTEST);

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
	public boolean onKeyDown(int keyCode, KeyEvent msg) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
				
			if(count2>3)
			{
				this.releaseSoundEffect();
				this.playSound(TUTORIAL_SUCCEEDED);
				
		
				try {
					
					Thread.sleep(1000);
			
				}catch(InterruptedException e11){
					e11.printStackTrace();
				}
				this.sayPageName("You have successfully performed Fling");
				
				try {
					
					Thread.sleep(2000);
			
				}catch(InterruptedException e12){
					e12.printStackTrace();
				}
				
				Intent intent = new Intent(TutorialTrackball.this, TutorialSwipeRight.class);
				startActivity(intent);
			}else
			{
				this.releaseSoundEffect();
				this.playSound(TUTORIAL);
				try {
					
					Thread.sleep(1000);
			
				}catch(InterruptedException e13){
					e13.printStackTrace();
				}
				this.sayPageName("East");
				count2++;

			}

		}
		else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
		 
 
		}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){

			
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
 	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
//flag = true;
		
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			//swipe left
			this.sayPageName("Finish Tutorial");
			try {
				
				Thread.sleep(2000);
		
			}catch(InterruptedException e14){
				e14.printStackTrace();
			}
			Intent intent2 = new Intent(TutorialTrackball.this, GateWay.class);
			startActivity(intent2);

 		}else if(e2.getX() - e1.getX() >SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
 			
		}else if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
		 
		}else if(e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
		 
 
 
			
			}
 
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
 		return true;

	}
	 

}