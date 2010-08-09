package talkingpoints.guoer;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureLibrary;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
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
import android.media.AudioManager;
import android.media.MediaPlayer;

public class GestureUI extends Activity implements OnInitListener,
		OnGestureListener, SensorListener {

	// For shake motion detection.
	private SensorManager sensorMgr;
	private long lastUpdate = -1;
	private float x, y, z;
	private float last_x, last_y, last_z;
	private static final int SHAKE_THRESHOLD = 800;
	private int count = 0;
	//	s
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
//	private static final int SWIPE_MIN_DISTANCE = 120;
//	private static final int SWIPE_MAX_OFF_PATH = 250;
//	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	 private static final int SWIPE_MIN_DISTANCE = 10; //120;
	 private static final int SWIPE_MAX_OFF_PATH = 250;
	 private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	 private static final int CHECK_DISTANCE =100; 
	 private static final int CHECK_DISTANCE_2=10; 
		private static final int SWIPE_MIN_DISTANCE_RIGHT_LEFT=100; //more distance require for left and right gesture 

	//sound variable 
	public static final int NOTIFICATION=0;
	public static final int ITEM_BY_ITEM=1;
	public static final int EDGE=2;
	public static final int TUTORIAL=3; 
	public static final int TUTORIAL_SUCCEEDED=4; 
	public static final int NEXT_PAGE=5;
	public static final int MISSED_IT=6; 
	
    private String path;
    
    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMediaPlayer2;
    private MediaPlayer mMediaPlayer3;
    private MediaPlayer mMediaPlayer4; 
    private MediaPlayer mMediaPlayer5; 
    private MediaPlayer mMediaPlayer6;
    private MediaPlayer mMediaPlayer7;
    
    private static boolean flag = false; 
    private static int count1=0;
    private static boolean flag2 = false; 
    private static boolean flag3 = false; 
    private static boolean flagForScrolling=false; 
    private static boolean flagTrackball=false; 
    
    private float FirstX;
    private float FirstY;
    private float LastX;
    private float LastY;
    
    public Context context;
  

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
	
//		  AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//
////		    am.isSpeakerphoneOn(); 
////		    am.isWiredHeadsetOn();
//		   if(am.isWiredHeadsetOn())
//		   {
//			  am.setSpeakerphoneOn(true);
//		   }
		String vibratorService = Context.VIBRATOR_SERVICE;
		Vibrator vibrator = (Vibrator)getSystemService(vibratorService);

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
//		  AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//
////		    am.isSpeakerphoneOn(); 
////		    am.isWiredHeadsetOn();
//		   if(am.isWiredHeadsetOn())
//		   {
//			  am.setSpeakerphoneOn(true);
//		   }
//		
		String vibratorService = Context.VIBRATOR_SERVICE;
		Vibrator vibrator = (Vibrator)getSystemService(vibratorService);
	}

	public void sayPageName() {
		this.mTts.speak(pageInfo.getText().toString(),
				TextToSpeech.QUEUE_FLUSH, null);
	}

	public void sayPageName(String name) {
		this.mTts.speak(name, TextToSpeech.QUEUE_FLUSH, null);
	}

	public boolean onTouchEvent(MotionEvent event) {

		 
			int action = event.getAction();
	    	//down 
			if(action == MotionEvent.ACTION_DOWN||action==MotionEvent.ACTION_MOVE)
				flagForScrolling=true;
			
	        if(action == MotionEvent.ACTION_DOWN)
	        {
	         	FirstX=event.getX();
	        	FirstY=event.getY();
	        }
	    	else if(action == MotionEvent.ACTION_UP)
	    	{
	    		LastX=event.getX();
	    		LastY=event.getY();
	    		
	     		
	    		if(FirstX>0||FirstY>0)
	    		{
	    			final float xD=Math.abs(FirstX-LastX);
	    			final float yD=Math.abs(FirstY-LastY);
	    			
	    			try{
	    				
	    				if(FirstX-LastX>SWIPE_MIN_DISTANCE_RIGHT_LEFT&&yD< CHECK_DISTANCE)
	    				{    // this.mTts.speak("LEFT MOTION", TextToSpeech.QUEUE_FLUSH,null);
		    				releaseSoundEffect();
							playSound(NEXT_PAGE);
								finish();

	    				}
	    				else if(LastX - FirstX >SWIPE_MIN_DISTANCE_RIGHT_LEFT&& yD< CHECK_DISTANCE) 
	    				{	vibrate();
	    					this.sayPageName();

	    				}
	     				   //   this.mTts.speak("Right motion", TextToSpeech.QUEUE_FLUSH,null);
	     				else if(FirstY - LastY > SWIPE_MIN_DISTANCE&& xD< CHECK_DISTANCE)  
	     				{
	     					 // this.mTts.speak("UP Motion", TextToSpeech.QUEUE_FLUSH,null);
	     					 if(flag||flagForScrolling)
	     					 {
	     						 vibrate();
	     						 upMotion();
	     						flagForScrolling=false;
	     					 }
	     					
	     				}
	     				else if(LastY - FirstY > SWIPE_MIN_DISTANCE && xD< CHECK_DISTANCE)  
	     				{	
	     					//this.mTts.speak("down motion", TextToSpeech.QUEUE_FLUSH,null);
	     					
	     					 if(flag||flagForScrolling)
	     					 {
	     						 
	     						 vibrate();
 
	     						downMotion();
	     						flagForScrolling = false; 
	     					 }
	     				}//missed
	     				else if(xD>CHECK_DISTANCE_2&&yD>CHECK_DISTANCE_2)
	     				{
	     					releaseSoundEffect();
							playSound(MISSED_IT);
 	     				}

	    		 

	    			}
	    			catch (Exception e0) {
	    				// nothing
	    			}
	    			
	    		}
	    	}
			
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
		
		final float xDistance = Math.abs(e1.getX() - e2.getX());
		final float yDistance = Math.abs(e1.getY() - e2.getY());
//flag = true;
		
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE_RIGHT_LEFT
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY &&yDistance< CHECK_DISTANCE) {
			releaseSoundEffect();
			playSound(NEXT_PAGE);
			//left
			finish();
		}else if(e2.getX() - e1.getX() >SWIPE_MIN_DISTANCE_RIGHT_LEFT
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY &&yDistance< CHECK_DISTANCE) {
			//right 
			this.sayPageName();
			
		}else if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY &&xDistance< CHECK_DISTANCE) {
		//	this.sayPageName("up");
			 if(flag)
			 { 
				 upMotion();
			 }
			
	    //  viewA.setText("-" + "Fling up?" + "-");

		}else if(e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY &&xDistance< CHECK_DISTANCE) {
			 if(flag)
			 {
				 downMotion();
			 }
			 
 
 
			
			}
//		flag=true;
//		// TODO Auto-generated method stub
//		try {
//			// if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//			// return false;
//			// right to left swipe
//			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
//					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//				
//			 
//				finish();
//
//			}
//			else if(e2.getX() - e1.getX() >SWIPE_MIN_DISTANCE
//					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//				this.sayPageName();
//			}s
//		} catch (Exception e) {
//			// nothing
//			Toast.makeText(this,"fling error!!",Toast.LENGTH_SHORT).show();
//		}
		
	 
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

 	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		flag = true; 

//		 if(flag)
//		 {
//				if (options.size() != 0){
//					if(count1==options.size()){
//						
//						count1=0;
//						
//					}
//					
//					if(count1<options.size()) //count<.size() 
//					{
// 				 //put get(count1)
//						message = options.get(count1);
//					
//
//						selected = count1;
//						text.setText(message)  ;
//
//						this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
//							null);
//					
//						releaseSoundEffect();
//						playSound(ITEM_BY_ITEM);
//					
//						read_flag = true;
//
//						if(count1==(options.size()-1)) 
//						{
//							releaseSoundEffect();
//							playSound(EDGE);
// 	
//						} 
//				   
//						count1++;
//
//					}
//			
//				}
//			 flag = false;
//		 }
		
		
//		if (options.size() != 0) {
//			
//			
//			if (options.size() <= 5)// when there are less than 5 options, give
//				// each option the 1/5 space of the screen
//				ratio = (height - start_y) / (60 * 5); //it was 60 * 5 
//			else if (options.size() > 5)// if there are more than 5 menu
//				// options, compress the list so that
//				// all the options fit into one screen
//				ratio = (height - start_y) / (60 * options.size());
//
//			synchronized (this) {
//
//				if (Math.abs(e2.getY() - cur_y) > 60 * ratio) {
// 					read_flag = false;
//
//				}
//
//				if (!read_flag) {
//					int i=0;
//					
//					for (i = 0; i < options.size(); i++) {
//
//				
//						if (((e2.getY() > (start_y + 60 * ratio * i)))
//								&& ((e2.getY() < (start_y + 60 + 60 * ratio * i)))) {
//
//							cur_y = e2.getY();
//							message = options.get(i);
//							
//
//							selected = i;
//							text.setText(message);
//
//							this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
//									null);
//							
//							releaseSoundEffect();
//							playSound(ITEM_BY_ITEM);
//							
//							read_flag = true;
//
//							//if(i==(options.size()-1)) 
//							if(i==options.size()-1)
//							{
//								/////////////Some
//							//	flag=true; 
//								
//								try {
//									
////									if((options.get(i).length()>8)&&(options.get(i).length()<16))
////									Thread.sleep(1400);
////									else if(options.get(i).length()>16)
////									Thread.sleep(2100);
////									else
//									Thread.sleep(700);
//									releaseSoundEffect();
//									playSound(EDGE);
//									
//								}catch(InterruptedException e){
//									e.printStackTrace();
//								}
////								releaseSoundEffect();
////								playSound(EDGE);
//								break;
//			
//							} 
//						   
//						//	break;
//						}
//
//					}
// 					
//				}
//
//			}
//			// }
//		} else if (!read_flag2) {
//			message = "Nothing interesting detected yet";
//			text.setText(message);
//			this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
//			
// 			read_flag2 = true;
//		}
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
                case NEXT_PAGE:
                	path = "/system/media/audio/notifications/Tinkerbell.ogg";
	          	      mMediaPlayer6 = new MediaPlayer();
	          	      mMediaPlayer6.setDataSource(path);
	          	      mMediaPlayer6.prepare();
	          	      mMediaPlayer6.start();
            	break;	
                case MISSED_IT:
                	path = "/system/media/audio/notifications/Plastic_Pipe.ogg";
	          	      mMediaPlayer7 = new MediaPlayer();
	          	      mMediaPlayer7.setDataSource(path);
	          	      mMediaPlayer7.prepare();
	          	      mMediaPlayer7.start();
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
					if (count < 3) {

						count += 1;
					} else if (count == 3) {
						count = 0;
						releaseSoundEffect();
						playSound(NEXT_PAGE);
						
						Intent intent = new Intent(GestureUI.this, DetectedLocations.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				}

				last_x = x;
				last_y = y;
				last_z = z;
			}

		}
	}
	 @Override
		public boolean onTrackballEvent(MotionEvent _event)
		{
			float vertical = _event.getY();
			float horizontal = _event.getX();
			
			   //viewA.setText("x"+horizontal+"Y"+vertical);
				
			   if(horizontal!=0.0||vertical!=0.0)
			   {
				  flagTrackball=true; 
			   }
			
			
			   if(horizontal<0.0 &&vertical==0.0 ) //left
			   {

			   }
			   else if(horizontal>0.0 &&vertical==0.0 ) //Right
			   {
					this.sayPageName();

			   }
			   else if(horizontal==0.0 &&vertical<0.0 ) //up
			   {
				   if(flagTrackball)
					 {	
 
						upMotion();
						flagTrackball=false;
					 }
			   }
			   else if(horizontal==0.0 &&vertical>0.0 ) //down
			   { 
				   if(flagTrackball)
					 {	
 						downMotion();
						flagTrackball=false;
					 }

			   }
			   
			return false; 
		}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if we get any key, clear the Splash Screen
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//			this.mTts.speak("Talking Points Home", TextToSpeech.QUEUE_FLUSH,
//					null);
//
//			Intent intent = new Intent(GestureUI.this, GateWay.class);
//			startActivity(intent);
//			return true;
//		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
//			super.onPause();
//			try {
//				wait();
//			} catch (InterruptedException e41) {
//				// TODO Auto-generated catch block
//				e41.printStackTrace();
//			}
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e42) {
//				e42.printStackTrace();
//			}
//
//			Toast
//					.makeText(getApplicationContext(), "home!",
//							Toast.LENGTH_SHORT).show();
//
//			this.mTts.speak("home", TextToSpeech.QUEUE_FLUSH, null);
//
//			onDestroy();
//			return true;
//		} else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
//
//			this.mTts.speak("Keyword Search", TextToSpeech.QUEUE_FLUSH, null);
//
//			// Intent intent = new Intent(GestureUI.this, Search.class);
//			// startActivity(intent);
//
//			return true;
//		}else
//		if(keyCode == KeyEvent.KEYCODE_HEADSETHOOK)
//		{
//			Toast.makeText(this,"headset hook!!!", Toast.LENGTH_SHORT).show();
//			
//		}
//		if(keyCode == KeyEvent.KEYCODE_HOME)
//		{
//			Toast.makeText(this,"HOME!", Toast.LENGTH_SHORT).show();
//		}
//		if(keyCode == KeyEvent.KEYCODE_H)
//		{
//			Toast.makeText(this,"HOME!", Toast.LENGTH_SHORT).show();
//		}
		
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			releaseSoundEffect();
			playSound(NEXT_PAGE);
			
			
			
			if(byCoordinateParser.getLatitude().size()>0)
			{
				AngleCalculator oc = new AngleCalculator(byCoordinateParser.getLatitude(), byCoordinateParser
						.getlongitude(),BTlist.LAC1,
						BTlist.LNG1);

			   		oc.getAngle();
	 
				Intent intent0 = new Intent(GestureUI.this,POIsAhead.class);
				intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(intent0); 

			}
			else 
			{
				
				this.mTts.speak("There is no internet connection. Please check", TextToSpeech.QUEUE_FLUSH, null);
			}

		
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
	 			
	 				read_flag = true;
	 
//	 				if(count1==(options.size()-1)) 
//					{
//						
//						if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
//						{
//							try {
//								
//								Thread.sleep(1400);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e51){
//								e51.printStackTrace();
//							}
//				 
//						}
//						else if(options.get(count1).length()>16)
//						{
//							try {
//								
//								Thread.sleep(2100);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e52){
//								e52.printStackTrace();
//							}
//				 
//						}else 
//						{
//							try {
//								
//								Thread.sleep(700);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e53){
//								e53.printStackTrace();
//							}
//						}
//	
//					} 
			    
					count1++;
	
				}
			} 
			//second version
//			if(options.size()!=0)
//			{
//				if(count1==options.size())
//					count1=0;
//				
//				if(flag3)
//				{
//					if(count1==options.size()-1)
//					count1=1;
//					else 
//					count1+=2;
//					
//					flag3=false; 
//				}
//				
//				//view.. 
//				message = options.get(count1);
//				
//
//				selected = count1;
//				text.setText(message);
//
//				this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
//					null);
//			
//				releaseSoundEffect();
//				playSound(ITEM_BY_ITEM);
//				
//				if(count1==options.size()-1)
//				{	
//					releaseSoundEffect();
//					playSound(EDGE);
//				}
//				//
//				if(count1<options.size())
//					count++;
//				
//				flag2=true; 
//			}
			
////////////First version/////
			
//			if (options.size() != 0){
//				 
//				if(count1==0)
//				{
//					count1++;
//
//				}
//				
//				if(count1==options.size()){
//					
//					count1 = options.size()-1;
//					
//				//	count1=0;
//					message = options.get(count1);
//					
//
//					selected = count1;
//					text.setText(message);
//
//					this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
//						null);
//				
//					releaseSoundEffect();
//					playSound(ITEM_BY_ITEM);
//					
//					releaseSoundEffect();
//					playSound(EDGE);
// 					
//					
//				}else if(count1<options.size()) //count<.size() 
//				{
//				 //put get(count1)
//					message = options.get(count1);
//				
//
//					selected = count1;
//					text.setText(message);
//
//					this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
//						null);
//				
//					releaseSoundEffect();
//					playSound(ITEM_BY_ITEM);
//				
// 
//					if(count1==(options.size()-1)) 
//					{
//						releaseSoundEffect();
//						playSound(EDGE);
//	
//					} 
//			   
//					count1++;
//
//				}
//		
//			}
			
		}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
		
			flag2=true;
			
			if(options.size()!=0)
			{
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
				
	
//					if(count1==(options.size()-1)) 
//					{
//						
//						if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
//						{
//							try {
//								
//								Thread.sleep(1400);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e61){
//								e61.printStackTrace();
//							}
//				 
//						}
//						else if(options.get(count1).length()>16)
//						{
//							try {
//								
//								Thread.sleep(2100);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e62){
//								e62.printStackTrace();
//							}
//				 
//						}else 
//						{
//							try {
//								
//								Thread.sleep(700);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e63){
//								e63.printStackTrace();
//							}
//						}
//	
//					} 
				//	 viewA.setText("Up"+count1);
			   
					count1--;
	
				}
			} 
			////Second version
//			flag3=true; 
//			
//			
//			if(options.size()!=0){
//				
//				if(flag2)
//				{
//					if(count1==1)
//						count1=options.size()-1;
//					else if(count1==options.size())
//						count1=options.size()-2;
//					else 
//						count1-=2;
//					
//					flag2=false; 
//				}
//				
//				//view .. 
//				message = options.get(count1);
//				
//
//				selected = count1;
//				text.setText(message);
//
//				this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
//					null);
//			
//				releaseSoundEffect();
//				playSound(ITEM_BY_ITEM);
//				
//				if(count1==options.size()-1)
//				{	
//					releaseSoundEffect();
//					playSound(EDGE);
//				}
//				//
//				
//				if(count1==0)
//				{
//					count1=options.size();
//					count1--;
//				}
//				else 
//				{
//					if(count<options.size()+1)
//						count--;
//				}
//			
//				
//				
//			}
			
//////// First Version 			
			
//  			if (options.size() != 0){
//				
//				if(count1!=0)
//				{
//					if(count1==options.size()){
//				
//					
//						count1=options.size()-2;
//					}	
//				}
//
//				if(count1==0)
//				{
//					message = options.get(count1);
//					
//
//					selected = count1;
//					text.setText(message);
//
//					this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
//						null);
//				
//					releaseSoundEffect();
//					playSound(ITEM_BY_ITEM);
//				
// 
//				}
//				else if(count1<options.size())//count<.size() 
//				{
//				 //put get(count1)
//					message = options.get(count1);
//				
//
//					selected = count1;
//					text.setText(message);
//
//					this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
//						null);
//				
//					releaseSoundEffect();
//					playSound(ITEM_BY_ITEM);
//				
// 
//					if(count1==(options.size()-1)) 
//					{
//						releaseSoundEffect();
//						playSound(EDGE);
//	
//					} 
//			   
//					count1--;
//
//				}
//		
//			}
		}

		return true;// return super.onKeyDown(keyCode, event);
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
				
	
//					if(count1==(options.size()-1)) 
//					{
//						
//						if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
//						{
//							try {
//								
//								Thread.sleep(1400);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e11){
//								e11.printStackTrace();
//							}
//				 
//						}
//						else if(options.get(count1).length()>16)
//						{
//							try {
//								
//								Thread.sleep(2100);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e12){
//								e12.printStackTrace();
//							}
//				 
//						}else 
//						{
//							try {
//								
//								Thread.sleep(700);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e13){
//								e13.printStackTrace();
//							}
//						}
//
//					} 
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
	 			
	 
//					if(count1==(options.size()-1)) 
//					{
//						
//						if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
//						{
//							try {
//								
//								Thread.sleep(1400);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e21){
//								e21.printStackTrace();
//							}
//				 
//						}
//						else if(options.get(count1).length()>16)
//						{
//							try {
//								
//								Thread.sleep(2100);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e22){
//								e22.printStackTrace();
//							}
//				 
//						}else 
//						{
//							try {
//								
//								Thread.sleep(700);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e23){
//								e23.printStackTrace();
//							}
//						}
//
//					} 
			    
					count1++;

				}
		 }  

		 flag = false;
	}
	public void vibrate()
	{
		String vibratorService = Context.VIBRATOR_SERVICE;
		Vibrator vibrator = (Vibrator)getSystemService(vibratorService);

//		long[] pattern = {1000,2000,4000,8000,16000};
// 		vibrator.vibrate(pattern,1);
//		vibrator.vibrate(500,1); //vibrate 0.5 seconds 
		vibrator.vibrate(100);
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
        if (mMediaPlayer6 != null){
        	mMediaPlayer6.release();
        	mMediaPlayer6 = null;
        }
        if (mMediaPlayer7 != null){
        	mMediaPlayer7.release();
        	mMediaPlayer7 = null;
        }
	}

}