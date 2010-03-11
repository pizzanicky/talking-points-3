package talkingpoints.guoer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BTlist extends Activity implements OnInitListener {
	// fake POI
	// String[] exsitingPOI = { "sdf", "002608D712B9", "1234567890" };
	private Timer m_timerForSensorUpdate = null;
	// debug rssi
	private static final String TAG = "MAC = ";
	public static final int UPDATEIDENTIFIER = 0;
	private RemoteService remoteService;
	// private boolean started = false;

	private TextToSpeech mTts;
	private List<String> cachList = null;
	// private int tempRSSI;
	// private int currentIndex;
	ListComparer NewItemfilter;
	private List<String> NofiticationList;
	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTA_DEVICE_RSSI = "device_rssi";

	// private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;

	public int m_periodUpdate = 3000;
	// Background service scanner
	private BTScanner btScanner;
	public static boolean isRunning;
	public Thread t;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);
		setResult(Activity.RESULT_CANCELED);

		// Initialize the button to perform device discovery
		/*
		 * Button scanButton = (Button) findViewById(R.id.button_scan);
		 * scanButton.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { doDiscovery(); // v.setVisibility(View.); } });
		 * 
		 * Button clearButton = (Button) findViewById(R.id.button_clear);
		 * clearButton.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { doClear(); // v.setVisibility(View.); } });
		 */

		// Initialize array adapters.
		// for newly discovered devices

		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);

		cachList = new ArrayList<String>();
		// Find and set up the ListView for newly discovered devices
		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);// ??
		// new_devices
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);

		// Register for broadcasts when a device is discovered

		mTts = new TextToSpeech(this, this);
		// doDiscovery();
		// startSensorUpdate();
		startService();
		bindService();

		t = new Thread(new myThread());
		t.start();
		isRunning = true;

	}

	protected void onDestroy() {
		super.onDestroy();
		releaseService();
		stopService();
		isRunning = false;
		t.stop();
		// t.destroy();
	}

	private void doClear() {

		mNewDevicesArrayAdapter.clear();

	}

	class RemoteServiceConnection implements ServiceConnection {
		public void onServiceConnected(ComponentName className,
				IBinder boundService) {
			remoteService = RemoteService.Stub
					.asInterface((IBinder) boundService);
			Log.d(getClass().getSimpleName(), "onServiceConnected()");
		}

		public void onServiceDisconnected(ComponentName className) {
			remoteService = null;
			// updateServiceStatus();
			Log.d(getClass().getSimpleName(), "onServiceDisconnected");
		}
	};

	private void startService() {
		if (BTScanner.started) {
			Toast.makeText(BTlist.this, "Service already started",
					Toast.LENGTH_SHORT).show();
		} else {
			Intent i = new Intent();
			i.setClassName("talkingpoints.guoer",
					"talkingpoints.guoer.BTScanner");
			startService(i);
			BTScanner.started = true;
			// updateServiceStatus();
			Log.d(getClass().getSimpleName(), "startService()");
		}

	}

	private void stopService() {
		t.stop();
		try {
			if (!BTScanner.started) {
				Toast.makeText(BTlist.this, "Service not yet started",
						Toast.LENGTH_SHORT).show();

			} else {
				Intent i = new Intent();
				i.setClassName("talkingpoints.guoer",
						"talkingpoints.guoer.BTScanner");
				stopService(i);
				BTScanner.started = false;
				// updateServiceStatus();
				Log.d(getClass().getSimpleName(), "stopService()");
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

	private void bindService() {
		if (BTScanner.conn == null) {
			BTScanner.conn = new RemoteServiceConnection();
			Log.d(getClass().getSimpleName(), "conn = " + BTScanner.conn);
			Intent i = new Intent(BTlist.this, BTScanner.class);
			// for some reason bindService doesn't work with child of
			// TabActivity, so we use getApplicationContext().bindService here
			getApplicationContext().bindService(i, BTScanner.conn,
					Context.BIND_AUTO_CREATE);
			// updateServiceStatus();
			Log.d(getClass().getSimpleName(), "bindService()");
		} else {
			Toast.makeText(BTlist.this, "Cannot bind - service already bound",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void releaseService() {
		try {
			if (BTScanner.conn != null) {
				getApplicationContext().unbindService(BTScanner.conn);
				BTScanner.conn = null;
				// updateServiceStatus();
				Log.d(getClass().getSimpleName(), "releaseService()");
			} else {
				// bindService();
				getApplicationContext().unbindService(BTScanner.conn);
				BTScanner.conn = null;
				Toast
						.makeText(BTlist.this,
								"Cannot unbind - service not bound",
								Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

	public void invokeService() {
		if (BTScanner.conn == null) {
			Toast.makeText(BTlist.this, "Cannot refresh - service not bound",
					Toast.LENGTH_SHORT).show();
			Log.w("timer", "not in try");
		} else {
			try {
				Log.w("timer", "in try");
				List<String> tempList = remoteService.getBTList();
				if (tempList.size() == 0)
					Toast.makeText(BTlist.this,
							mNewDevicesArrayAdapter.getCount() + " POI found!",
							Toast.LENGTH_SHORT).show();
				else {
					Toast.makeText(BTlist.this, tempList.size() + " POI found",
							Toast.LENGTH_SHORT).show();
					Log.w("timer", tempList.size() + " POI found");
					if (cachList != null) {
						NewItemfilter = new ListComparer(tempList, cachList);
						// the new found devices list
						NofiticationList = NewItemfilter.getNewItems();
						Log.w("timer", "cachlist size" + cachList.size());
						// notify new devices found
						if (NofiticationList != null
								&& NofiticationList.size() > 0) {
							for (int i = 0; i < NofiticationList.size(); i++) {
								Log.w("NofiticationList", NofiticationList
										.get(i));
								Toast.makeText(BTlist.this,
										NofiticationList.get(i) + " found!",
										Toast.LENGTH_SHORT).show();
							}
						}

						cachList.clear();
					}

					mNewDevicesArrayAdapter.clear();
					// Here we get data from RemoteService.
					Log.w("timer", "list clear");
					for (int i = 0; i < tempList.size(); i++) {
						mNewDevicesArrayAdapter.add(tempList.get(i));
						// cachList.add(tempList.get(i));

					}
					cachList = tempList;
					Log.d(getClass().getSimpleName(), "invokeService()");

				}
			} catch (RemoteException re) {
				Log.e(getClass().getSimpleName(), "RemoteException");
			} catch (Exception e) {
				Log
						.e(getClass().getSimpleName(), "??Exception:"
								+ e.toString());
			}
		}
	}

	// The on-click listener for all devices in the ListViews
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			// Cancel discovery because it's costly and we're about to connect
			// mBtAdapter.cancelDiscovery();

			// Get the device MAC address, which is the last 17 chars in the
			// View
			String info = ((TextView) v).getText().toString();
			String address = info.substring(info.length() - 17);
			MacReader r = new MacReader(address);
			// Create the result Intent and include the MAC address
			Intent intent = new Intent(BTlist.this, POIview.class);

			intent.putExtra("MAC", r.getMacString());
			// setResult(Activity.RESULT_OK, intent);
			// finish();

			startActivity(intent);// see detail of selected POI
		}
	};

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

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_start:
			if (!BTScanner.started) {
				t = new Thread(new myThread());
				t.start();
				startService();
				bindService();
			}
			break;
		case R.id.menu_stop:
			t.interrupt();
			releaseService();
			stopService();
			// t.destroy();
			break;
		case R.id.menu_refresh:
			invokeService();
			break;
		case R.id.menu_clear:
			doClear();
			break;

		}
		return true;
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BTlist.UPDATEIDENTIFIER:
				invokeService();
				break;
			}
			// super.handleMessage(msg);
		}
	};

	// public void startSensorUpdate() {
	//
	// m_timerForSensorUpdate = new Timer();
	// // set timer for location update
	// // Log.w("tag", "haha");
	// m_timerForSensorUpdate.scheduleAtFixedRate(new TimerTask() {
	// public void run() {
	// if (BTScanner.started) {
	// invokeService();
	// Log.w("timer", "lala");
	// }
	// }
	// }, 0, m_periodUpdate);
	// }
	class myThread implements Runnable {
		public void run() {
			while (!Thread.currentThread().isInterrupted() && BTlist.isRunning) {
				Message message = new Message();
				message.what = BTlist.UPDATEIDENTIFIER;

				BTlist.this.myHandler.sendMessage(message);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
