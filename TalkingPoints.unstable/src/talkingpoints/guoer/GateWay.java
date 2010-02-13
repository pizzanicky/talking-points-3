package talkingpoints.guoer;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class GateWay extends TabActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		final TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("POI")
				.setContent(new Intent(this, BTlist.class)));

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Directory")
				.setContent(new Intent(this, Directory.class)));

		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Search")
				.setContent(
						new Intent(this, Search.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
	}
}
