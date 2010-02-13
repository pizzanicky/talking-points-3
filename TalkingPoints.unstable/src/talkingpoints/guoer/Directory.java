package talkingpoints.guoer;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.ArrayAdapter;

public class Directory extends ListActivity implements OnGestureListener {
	private String[] mStrings = { "First Floor", "Second Floor", "Third Floor" };
	private GestureDetector gestureScanner;
	View.OnTouchListener gestureListener;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gestureScanner = new GestureDetector(this);
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureScanner.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};

		// String[] mStrings = { "Name", "Description" };

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mStrings));

		getListView().setTextFilterEnabled(true);
		getListView().setOnTouchListener(gestureListener);
		setResult(Activity.RESULT_OK);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
