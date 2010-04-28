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

public class POIsAhead extends GestureUI implements SensorEventListener {
	// private ArrayList<String> POIs;;
	public static ArrayList<String> NamesAhead;
	public static ArrayList<String> MacAddrAhead;
	public static ArrayList<String> TPIDAhead;
	private SensorManager sm = null;
	private Sensor compass;
	private final double range = 30.0;
	private float[] values;
	private static float angle;

	public void onCreate(Bundle savedInstanceState) {

		// POIs = new ArrayList<String>();
		pageName = "Turn to any direction to find locations";
		super.onCreate(savedInstanceState);
		MacAddrAhead = new ArrayList<String>();
		NamesAhead = new ArrayList<String>();
		TPIDAhead = new ArrayList<String>();
		// Compass cp = new Compass();
		sm = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
		compass = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sm.registerListener(this, compass, SensorManager.SENSOR_DELAY_FASTEST);

		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
				Intent intent = new Intent(POIsAhead.this, POImenu.class);
				intent.putExtra("tpid", POIsAhead.TPIDAhead
						.get(GestureUI.selected));
				intent.putExtra("POIname", POIsAhead.NamesAhead
						.get(GestureUI.selected));
				startActivity(intent);
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

	// @Override
	// protected void onStart() {
	// super.onStart();
	// pageInfo.setText(pageName);
	// sayPageName(pageName);
	// }
	//
	// @Override
	// protected void onRestart() {
	// super.onRestart();
	// pageInfo.setText(pageName);
	// sayPageName(pageName);
	// }

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
					this.options.add(NearbyParser.name.get(i));
					MacAddrAhead.add(NearbyParser.mac.get(i));
					TPIDAhead.add(NearbyParser.id.get(i));
				}
			}
			NamesAhead = this.options;
			if (this.options.size() == 0)
				this.mTts
						.speak(
								"Nothing interesting ahead of you, keep searching by turning around",
								TextToSpeech.QUEUE_FLUSH, null);
			else if (this.options.size() == 1)
				this.mTts.speak("There is" + this.options.size()
						+ "location ahead of you, scroll to check it out",
						TextToSpeech.QUEUE_FLUSH, null);
			else
				this.mTts.speak("There are" + this.options.size()
						+ "locations ahead of you, scroll to check them out",
						TextToSpeech.QUEUE_FLUSH, null);
			// }

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

}