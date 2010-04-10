package talkingpoints.guoer;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.gesture.GestureLibrary;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.widget.TextView;

public class GestureUI extends Activity implements OnInitListener,
		OnGestureListener {
	private GestureLibrary mLibrary;
	private float xPoint, yPoint;
	protected ArrayList<String> options;
	private float start_y; // Y position on screen where touch is first detected
	private float end_y;
	private float ratio;
	private TextToSpeech mTts;
	private boolean read_flag = false;
	private float cur_y;
	private int height; // Screen height
	private String message;
	protected GestureDetector gestureScanner;
	public static int selected;
	private boolean read_flag2 = false;
	private boolean move = false;
	public static TextView text;
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
		// text = (TextView) findViewById(R.id.test1);
	}

	public boolean onTouchEvent(MotionEvent event) {

		gestureScanner.onTouchEvent(event);
		return true;

	}

	@Override
	public boolean onDown(MotionEvent e) {
		start_y = e.getY();
		Log.w("onDown", "down");
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
		try {
			// if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
			// return false;
			// right to left swipe
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				// Log.w(TAG, "HAHA = " + "Swip left");
				// mTts.shutdown();
				// mTts.stop();

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
		Log.w("shit2", "" + "scrolling");
		// text = (TextView) findViewById(R.id.test);

		if (options.size() != 0) {
			ratio = (height - start_y) / (60 * 6);

			synchronized (this) {
				// if (e1.getAction() == MotionEvent.ACTION_DOWN) {
				//
				// // start_y = e1.getY();
				// // Log.w("f", "down");
				// // text.setText("down");
				// // message = options.get(0);
				// // this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
				// }

				// if (e2.getAction() == MotionEvent.ACTION_MOVE) {

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
							Log.w("message", text.toString());
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
			message = "No Options Available";
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
		} else {
			// Initialization failed.
			// Log.e(TAG, "Could not initialize TextToSpeech.");
		}
	}

}