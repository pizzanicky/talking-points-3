package talkingpoints.guoer;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.gesture.GestureLibrary;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;

class Panel extends SurfaceView implements SurfaceHolder.Callback,
		OnInitListener, OnGestureListener {
	private drawThread _thread;
	public static String message;
	private float xPoint, yPoint;
	private ArrayList<String> options;
	private float start_y;
	private float end_y;
	private float ratio;
	private GestureLibrary mLibrary;
	private TextToSpeech mTts;
	private boolean read_flag = false;
	private boolean read_flag2 = false;
	private float cur_y;
	private int height;
	public GestureDetector gestureScanner;
	public static int selected;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	public Panel(Context context, ArrayList<String> o) {
		super(context);
		gestureScanner = new GestureDetector(this);
		getHolder().addCallback(this);
		_thread = new drawThread(getHolder(), this);
		setFocusable(true);
		message = new String("");
		this.options = o;
		mTts = new TextToSpeech(this.getContext(), this);
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		height = display.getHeight();
	}

	public Panel(Context context) {
		super(context);
		// TextView t = new TextView(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureScanner.onTouchEvent(event);

		return true;

	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		Paint messagePaint = new Paint();
		messagePaint.setColor(Color.WHITE);
		messagePaint.setTextSize(20.f);
		canvas.drawText(message, 100, 100, messagePaint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		_thread.setRunning(true);
		_thread.start();
	}

	public float getXPoint() {
		return xPoint;
	}

	public float getYPoint() {
		return yPoint;
	}

	public void setXPoint(float x) {
		xPoint = x;
	}

	public void setYPoint(float y) {
		yPoint = y;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// simply copied from sample application LunarLander:
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		_thread.setRunning(false);
		while (retry) {
			try {
				_thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
	}

	// public void onGesturePerformed(GestureOverlayView overlay, Gesture
	// gesture) {
	// ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
	//
	// // We want at least one prediction
	// if (predictions.size() > 0) {
	// Prediction prediction = predictions.get(0);
	// // We want at least some confidence in the result
	// if (prediction.score > 1.0) {
	// // Show the spell
	// Toast.makeText(this.getContext(), prediction.name,
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	// }
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

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
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
				// this.getContext().
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
			ratio = (height - start_y) / (60 * options.size());
			synchronized (_thread.getSurfaceHolder()) {
				if (e1.getAction() == MotionEvent.ACTION_DOWN) {
					start_y = e1.getY();
					// message = options.get(0);
					// this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
				}

				if (e2.getAction() == MotionEvent.ACTION_MOVE) {

					// ratio = (height - start_y) / (60 * options.size());
					if (Math.abs(e2.getY() - cur_y) > 60 * ratio)
						read_flag = false;

					if (!read_flag) {
						for (int i = 0; i < options.size(); i++) {
							if (((e2.getY() > (start_y + 60 * ratio * i)))
									&& ((e2.getY() < (start_y + 60 + 60 * ratio
											* i)))) {
								cur_y = e2.getY();
								message = options.get(i);
								selected = i;
								this.mTts.speak(message,
										TextToSpeech.QUEUE_FLUSH, null);
								read_flag = true;
								break;
							}
						}
					}

				}
			}
		} else if (!read_flag2) {
			message = "No Options Available";
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
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onDoubleTapEvent(MotionEvent e) {

		return false;
	}
}
