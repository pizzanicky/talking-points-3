package talkingpoints.guoer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;

public class POImenu extends GestureUI {
	private ArrayList<String> MenuOptions;
	private static String GET_INFO_BYMAC = "http://app.talking-points.org/locations/show_by_bluetooth_mac/";
	private String MAC;
	private String content;
	private MsgParser p;

	public void onCreate(Bundle savedInstanceState) {
		// super.onCreate(savedInstanceState);
		MenuOptions = new ArrayList<String>();
		MenuOptions.add("Name");
		MenuOptions.add("Description");
		MenuOptions.add("City");
		MenuOptions.add("State");
		MenuOptions.add("Post code");
		super.onCreate(savedInstanceState, MenuOptions);
		// this.options = MenuOptions;
		MAC = getIntent().getStringExtra("MAC");
		p = new MsgParser(GET_INFO_BYMAC + MAC + ".xml");
		text.setText("POI menu");
		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
				switch (GestureUI.selected) {
				case 0:
					content = p.getName();
					break;
				case 1:
					content = p.getDescription();
					break;
				case 2:
					content = p.getCity();
					break;
				case 3:
					content = p.getState();
					break;
				case 4:
					content = p.getPostalCode();
					break;

				}
				Intent intent = new Intent(POImenu.this, POIdetail.class);
				intent.putExtra("content", content);
				intent.putExtra("MAC", MAC);
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

}
