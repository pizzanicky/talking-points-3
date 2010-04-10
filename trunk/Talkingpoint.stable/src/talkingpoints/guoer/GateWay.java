// GateWay.java
// Defines the entry point for the TalkingPoints application, as well as 
// the main menu screen.
package talkingpoints.guoer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;


public class GateWay extends GestureUI {
	private ArrayList<String> MenuOptions;
	public static Panel ui;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// super.onCreate(savedInstanceState);
		
		// Set the menu text
		MenuOptions = new ArrayList<String>();
		MenuOptions.add("Tutorial");
		MenuOptions.add("Start");
		// MenuOptions.add("option3");
		// MenuOptions.add("option4");
		// MenuOptions.add("option5");
		// MenuOptions.add("option6");
		super.onCreate(savedInstanceState, MenuOptions);
		// this.options = MenuOptions;
		
		// Set up listener to launch the BTList activity on double-tap
		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
				switch (GestureUI.selected) {
				case 0:
					break;
				case 1:
					Intent intent = new Intent(GateWay.this, BTlist.class);
					startActivity(intent);
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

		super.text.setText("Main Menu");
	}

}
