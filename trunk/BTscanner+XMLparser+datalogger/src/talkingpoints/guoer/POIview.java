package talkingpoints.guoer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//show detail info of the selected POI
public class POIview extends Activity {
	private static final String TAG = "MAC = ";
	private String MAC;
	private static String GET_INFO_BYMAC = "http://test.talking-points.org/locations/show_by_bluetooth_mac/";
	private TextView text;
	private TextView title;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poi_view);
		// get MAC address from picked device
		MAC = getIntent().getStringExtra("MAC");

		title = (TextView) findViewById(R.id.poi_detail);
		text = (TextView) findViewById(R.id.poi_info);

		// retrieve data from server
		MsgParser p = new MsgParser(GET_INFO_BYMAC + MAC + ".xml");
		text.setText(p.getDescription());
		title.setText(p.getName());

		findViewById(R.id.poi_detail).setVisibility(View.VISIBLE);
		findViewById(R.id.poi_info).setVisibility(View.VISIBLE);

		setResult(Activity.RESULT_OK);
	}

}