package talkingpoints.guoer;

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

public class GestureUI extends Activity implements OnInitListener,
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
	private String message;
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
		// TODO Auto-generated method stub
		try {
			// if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
			// return false;
			// right to left swipe
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

				finish();

			}
		} catch (Exception e) {
			// nothing
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

		if (options.size() != 0) {

			if (options.size() <= 5)// when there are less than 5 options, give
				// each option the 1/5 space of the screen
				ratio = (height - start_y) / (60 * 5);
			else if (options.size() > 5)// if there are more than 5 menu
				// options, compress the list so that
				// all the options fit into one screen
				ratio = (height - start_y) / (60 * options.size());

			synchronized (this) {

				if (Math.abs(e2.getY() - cur_y) > 60 * ratio) {
					read_flag = false;

				}

				if (!read_flag) {
					for (int i = 0; i < options.size(); i++) {

						if (((e2.getY() > (start_y + 60 * ratio * i)))
								&& ((e2.getY() < (start_y + 60 + 60 * ratio * i)))) {

							cur_y = e2.getY();
							message = options.get(i);

							selected = i;
							text.setText(message);

							this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
									null);
							read_flag = true;

							break;
						}

					}
				}

			}
			// }
		} else if (!read_flag2) {
			message = "Nothing interesting detected yet";
			text.setText(message);
			this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
			read_flag2 = true;
		}
		return true;

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
						Intent intent = new Intent(GestureUI.this, BTlist.class);
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if we get any key, clear the Splash Screen
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			this.mTts.speak("Talking Points Home", TextToSpeech.QUEUE_FLUSH,
					null);

			Intent intent = new Intent(GestureUI.this, GateWay.class);
			startActivity(intent);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			super.onPause();
			try {
				wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Toast
					.makeText(getApplicationContext(), "home!",
							Toast.LENGTH_SHORT).show();

			this.mTts.speak("home", TextToSpeech.QUEUE_FLUSH, null);

			onDestroy();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_SEARCH) {

			this.mTts.speak("Keyword Search", TextToSpeech.QUEUE_FLUSH, null);

			// Intent intent = new Intent(GestureUI.this, Search.class);
			// startActivity(intent);

			return true;
		}

		return true;// return super.onKeyDown(keyCode, event);
	}

}