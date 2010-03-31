package talkingpoints.guoer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//show detail info of the selected POI
public class POIview extends ListActivity implements OnGestureListener {
	private static final String TAG = "MAC = ";
	private static String GET_INFO_BYMAC = "http://app.talking-points.org/locations/show_by_bluetooth_mac/";
	private String MAC;
	private String content;
	private GestureDetector gestureScanner;
	View.OnTouchListener gestureListener;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private MsgParser p;
	private String[] mStrings = { "Name", "Description", "City", "State",
			"Post code" };

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
		MAC = getIntent().getStringExtra("MAC");
		p = new MsgParser(GET_INFO_BYMAC + MAC + ".xml");
		// String[] mStrings = { "Name", "Description" };

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mStrings));

		getListView().setTextFilterEnabled(true);
		getListView().setOnTouchListener(gestureListener);
		setResult(Activity.RESULT_OK);

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(POIview.this, POIdetail.class);

		String selected = l.getItemAtPosition(position).toString();
		if (selected.equals("Name"))
			content = p.getName();
		if (selected.equals("Description"))
			content = p.getDescription();
		if (selected.equals("City"))
			content = p.getCity();
		if (selected.equals("State"))
			content = p.getState();
		if (selected.equals("Post code"))
			content = p.getPostalCode();

		intent.putExtra("content", content);
		intent.putExtra("MAC", MAC);
		startActivity(intent);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		try {
			// if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
			// return false;
			// right to left swipe
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				Log.w(TAG, "HAHA = " + "Swip left");
				finish();

			}
		} catch (Exception e) {
			// nothing
		}
		return false;
	}

	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
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
		Log.w("Xdistance", new Float(distanceX).toString());
		Log.w("Ydistance", new Float(distanceY).toString());
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

}