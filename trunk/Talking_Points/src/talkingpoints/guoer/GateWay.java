package talkingpoints.guoer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;

public class GateWay extends GestureUI {
	private ArrayList<String> MenuOptions;

	// private void sayPageName() {
	// this.mTts.speak(PageName, TextToSpeech.QUEUE_FLUSH, null);
	// }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MenuOptions = new ArrayList<String>();
		MenuOptions.add("Detected Locations");
		MenuOptions.add("Building Directory");
		MenuOptions.add("Keyword Search");

		pageName = new String("Talking Points Home");
		super.onCreate(savedInstanceState, MenuOptions);

		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
				switch (GestureUI.selected) {
				case 0:
					Intent intent = new Intent(GateWay.this, BTlist.class);
					startActivity(intent);
					break;
				case 1://
					break;
				case 2:
					break;
				}
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

}
