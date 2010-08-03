package gesture.tutorial;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureLibrary;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class TutorialShake extends Activity implements OnInitListener,
		OnGestureListener, SensorListener {

	// For shake motion detection.
	private SensorManager sensorMgr;
	private long lastUpdate = -1;
	private float x, y, z;
	private float last_x, last_y, last_z;
	private static final int SHAKE_THRESHOLD = 800;
	private int count = 0;
	//	
	private GestureLibrary mLibrary;
	private float xPoint, yPoint;
	protected ArrayList<String> options;
	private float start_y;
	private float end_y;
	private float ratio;
	protected TextToSpeech mTts;
	private boolean read_flag = false;
	private float cur_y;
	private int height;
	public String message;
	protected GestureDetector gestureScanner;
	public static int selected;
	private boolean read_flag2 = false;
	private boolean move = false;
	public TextView text;
	public TextView pageInfo;
	protected String pageName;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	
	//sound variable 
	public static final int NOTIFICATION=0;
	public static final int ITEM_BY_ITEM=1;
	public static final int EDGE=2;
	public static final int TUTORIAL=3; 
	public static final int TUTORIAL_SUCCEEDED=4; 
    private String path;
    
    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMediaPlayer2;
    private MediaPlayer mMediaPlayer3;
    private MediaPlayer mMediaPlayer4; 
    private MediaPlayer mMediaPlayer5; 
    
    private static boolean flag = false; 
    private static int count1=0;
    private static boolean flag2 = false; 
    private static boolean flag3 = false; 

    private static int count2=0; 
    
	public void onCreate(Bundle savedInstanceState, ArrayList<String> o) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mTts = new TextToSpeech(this, this);
		gestureScanner = new GestureDetector(this);

		options = new ArrayList<String>();
		options = o;
		Display display = ((WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		height = display.getHeight();
		text = (TextView) findViewById(R.id.test);
		pageInfo = (TextView) findViewById(R.id.pageInfo);
		pageInfo.setText(pageName);

		// start motion detection
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		boolean accelSupported = sensorMgr.registerListener(this,
				SensorManager.SENSOR_ACCELEROMETER,
				SensorManager.SENSOR_DELAY_GAME);

		if (!accelSupported) {
			// on accelerometer on this device
			sensorMgr.unregisterListener(this,
					SensorManager.SENSOR_ACCELEROMETER);
		}
		
		 pageName = new String("Gesure Number five. Swipe left.  "+
				 "the shake gesture is used for returning to the list of locations around you within 20 ft. " +
				 "You can use it from anywhere in the application. " +
				 "To shake, flick your wrist to shake the whole phone twice." +
				 " Note that unlike swipe left, shake will always return you to the list of locations around you within 20 ft. " +
				 "By contrast, swipe left will just take you to the previous screen." );
	
        
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mTts = new TextToSpeech(this, this);
		gestureScanner = new GestureDetector(this);

		options = new ArrayList<String>();
		Display display = ((WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		height = display.getHeight();
		text = (TextView) findViewById(R.id.test);
		pageInfo = (TextView) findViewById(R.id.pageInfo);
		pageInfo.setText(pageName);

		// start motion detection
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		boolean accelSupported = sensorMgr.registerListener(this,
				SensorManager.SENSOR_ACCELEROMETER,
				SensorManager.SENSOR_DELAY_GAME);

		if (!accelSupported) {
			// on accelerometer on this device
			sensorMgr.unregisterListener(this,
					SensorManager.SENSOR_ACCELEROMETER);
		}
      
		 pageName = new String("Gesure Number five. Swipe left.  "+
				 "the shake gesture is used for returning to the list of locations around you within 20 ft. " +
				 "You can use it from anywhere in the application. " +
				 "To shake, flick your wrist to shake the whole phone twice." +
				 " Note that unlike swipe left, shake will always return you to the list of locations around you within 20 ft. " +
				 "By contrast, swipe left will just take you to the previous screen." );
	
	}

	public void sayPageName() {
		this.mTts.speak(pageInfo.getText().toString(),
				TextToSpeech.QUEUE_FLUSH, null);
	}

	public void sayPageName(String name) {
		this.mTts.speak(name, TextToSpeech.QUEUE_FLUSH, null);
	}

	public boolean onTouchEvent(MotionEvent event) {

		gestureScanner.onTouchEvent(event);
		return true;

	}

	@Override
	public boolean onDown(MotionEvent e) {
		start_y = e.getY();

		return true;
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
		
			}catch(InterruptedException e11){
				e11.printStackTrace();
			}
			Intent intent2 = new Intent(TutorialShake.this, gt.class);
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
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
  
		return true;

	}
	public void DoinkSoundEffect(){
	//	if(flag)	
	//	{	
			releaseSoundEffect();
			playSound(EDGE);
		//	flag = false; 
		//}
	}
	
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// end_y = e.getY();
		// message = "UP X = " + event.getX() + " Y = " + event.getY();
		return true;
	}
	
	public void playSound(int mode)
	{
        try {
            switch (mode) {
                case NOTIFICATION:
                    /**
                     * TODO: Set the path variable to a local audio file path.
                     */
                    path = "/system/media/audio/notifications/pixiedust.ogg";
              	    mMediaPlayer = new MediaPlayer();
                     mMediaPlayer.setDataSource(path);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    break;
                case ITEM_BY_ITEM:
                	  path = "/system/media/audio/ui/Effect_Tick.ogg";
             	      mMediaPlayer2 = new MediaPlayer();
                       mMediaPlayer2.setDataSource(path);
                      mMediaPlayer2.prepare();
                      mMediaPlayer2.start();
                   //   mMediaPlayer2.release();
                	break;
                case EDGE:
                	  path = "/system/media/audio/notifications/Doink.ogg";
            	      mMediaPlayer3 = new MediaPlayer();
                       mMediaPlayer3.setDataSource(path);
                      mMediaPlayer3.prepare();
                      mMediaPlayer3.start();
                	break;
                case TUTORIAL:
	              	  path = "/system/media/audio/notifications/Tinkerbell.ogg";
	          	      mMediaPlayer4 = new MediaPlayer();
	          	      mMediaPlayer4.setDataSource(path);
	          	      mMediaPlayer4.prepare();
	          	      mMediaPlayer4.start();
              	break;
                case TUTORIAL_SUCCEEDED:
	              	  path = "/system/media/audio/notifications/Heaven.ogg";
	          	      mMediaPlayer5 = new MediaPlayer();
	          	      mMediaPlayer5.setDataSource(path);
	          	      mMediaPlayer5.prepare();
	          	      mMediaPlayer5.start();
              	break;

            }
          //  text.setText("Playing audio...");

        } catch (Exception e) {
            text.setText("Errors!...");
        }
		
	}

	@Override
	public void onInit(int status) {
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
		if (status == TextToSpeech.SUCCESS) {
			// Set preferred language to US english.
			// Note that a language may not be available, and the result will
			// indicate this.
			int result = mTts.setLanguage(Locale.US);
			// Try this someday for some interesting results.
			// int result mTts.setLanguage(Locale.FRANCE);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Lanuage data is missing or the language is not supported.
				// Log.e(TAG, "Language is not available.");
			}
			sayPageName();
		} else {
			// Initialization failed.
			// Log.e(TAG, "Could not initialize TextToSpeech.");
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// pageInfo.setText(pageName);
		sayPageName();
	}

	protected void onPause() {
		if (sensorMgr != null) {
			sensorMgr.unregisterListener(this,
					SensorManager.SENSOR_ACCELEROMETER);
			sensorMgr = null;
		}
		super.onPause();
	}

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		// TODO Auto-generated method stub
		if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
			long curTime = System.currentTimeMillis();
			// only allow one update every 100ms.
			if ((curTime - lastUpdate) > 100) {
				long diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;

				x = values[SensorManager.DATA_X];
				y = values[SensorManager.DATA_Y];
				z = values[SensorManager.DATA_Z];

				float speed = Math.abs(x + y + z - last_x - last_y - last_z)
						/ diffTime * 10000;

				if (speed > SHAKE_THRESHOLD) {
					// shake action
					if(count2>5)
					{
						this.releaseSoundEffect();
						this.playSound(TUTORIAL_SUCCEEDED);
						
				
						try {
							
							Thread.sleep(1000);
					
						}catch(InterruptedException e12){
							e12.printStackTrace();
						}
						this.sayPageName("You have successfully performed Fling");
						
						try {
							
							Thread.sleep(2000);
					
						}catch(InterruptedException e13){
							e13.printStackTrace();
						}
						
						Intent intent = new Intent(TutorialShake.this, TutorialFinished.class);
						startActivity(intent);
					}
					else
					{
						if (count < 3) {
	
							count += 1;
						} else if (count == 3) {
							count = 0;
							this.releaseSoundEffect();
							this.playSound(TUTORIAL);
							
							this.sayPageName("Shaking Detected!");
							count2++;
	//						Intent intent = new Intent(TutorialShake.this, TutorialSlide.class);
	//						startActivity(intent);
						}
					}
					
				}

				last_x = x;
				last_y = y;
				last_z = z;
			}

		}
	}
 
	 
	public void releaseSoundEffect(){
		 
		
        // TODO Auto-generated method stub
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mMediaPlayer2 != null){
        	mMediaPlayer2.release();
        	mMediaPlayer = null;
        }
        if (mMediaPlayer3 != null){
        	mMediaPlayer3.release();
        	mMediaPlayer3 = null;
        }
        if (mMediaPlayer4 != null){
        	mMediaPlayer4.release();
        	mMediaPlayer4 = null;
        }
        if (mMediaPlayer5 != null){
        	mMediaPlayer5.release();
        	mMediaPlayer5 = null;
        }
	}

}