package talkingpoints.guoer;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.TextView;

public class POIdetail extends Activity implements OnGestureListener,
		OnInitListener {
	// private String MAC;
	// private static String GET_INFO_BYMAC =
	// "http://app.talking-points.org/locations/show_by_bluetooth_mac/";
	private TextView text;
	// private TextView title;
	private String content;
	private static final String TAG = "MAC = ";
	private TextToSpeech mTts;
	private GestureDetector gestureScanner;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gestureScanner = new GestureDetector(this);
		setContentView(R.layout.poi_view);
		// get MAC address from picked device
		// MAC = getIntent().getStringExtra("MAC");
		content = getIntent().getStringExtra("content");

		// title = (TextView) findViewById(R.id.poi_detail);
		text = (TextView) findViewById(R.id.poi_info);

		// retrieve data from server

		text.setText(content);
		// title.setText(p.getName());
		mTts = new TextToSpeech(this, this);
		mTts.speak(content, TextToSpeech.QUEUE_FLUSH, null);
		// findViewById(R.id.poi_detail).setVisibility(View.VISIBLE);
		findViewById(R.id.poi_info).setVisibility(View.VISIBLE);
		setResult(Activity.RESULT_OK);

	}

	public boolean onTouchEvent(MotionEvent event) {
		if (gestureScanner.onTouchEvent(event)) {
			Log.w(TAG, "HAHA = " + "touch");
			return true;
		} else
			return false;

	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		try {
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
				return false;
			// right to left swipe
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				Log.w(TAG, "HAHA = " + "Swip left");
				finish();

			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				Log.w(TAG, "HAHA = " + "Swip right");
				mTts.speak(content, TextToSpeech.QUEUE_FLUSH, null);
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
		// TODO Auto-generated method stub
		return false;
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
				Log.e(TAG, "Language is not available.");
			}
		} else {
			// Initialization failed.
			Log.e(TAG, "Could not initialize TextToSpeech.");
		}
	}
}
