package talkingpoints.guoer;

import java.util.Locale;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import android.widget.AdapterView.OnItemClickListener;

public class BTlist extends Activity implements OnInitListener {
	// fake POI
	String[] exsitingPOI = { "sdf", "002608D712B9", "1234567890" };

	// debug rssi
	private static final String TAG = "MAC = ";

	// for testing
	// private Short r1;
	// private Short r2;
	private TextToSpeech mTts;

	private int tempRSSI;
	private int currentIndex;

	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTA_DEVICE_RSSI = "device_rssi";

	private BluetoothAdapter mBtAdapter;
	// private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;

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

		// Find and set up the ListView for newly discovered devices
		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);// ??
		// new_devices
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);

		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		mTts = new TextToSpeech(this, this);
		doDiscovery();
	}

	protected void onDestroy() {
		super.onDestroy();

		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}

		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery() {

		// Indicate scanning in the title
		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.scanning);

		// Turn on sub-title for new devices
		findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}

		// Request discover from BluetoothAdapter
		mBtAdapter.startDiscovery();
	}

	private void doClear() {

		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		mNewDevicesArrayAdapter.clear();

	}

	// The on-click listener for all devices in the ListViews
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			// Cancel discovery because it's costly and we're about to connect
			mBtAdapter.cancelDiscovery();

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

			startActivity(intent);// see detial of selected POI
		}
	};

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {

			try {
				String action = intent.getAction();
				currentIndex = 0;
				// When discovery finds a device
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					// Get the BluetoothDevice object from the Intent
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					MacReader r = new MacReader(device.getAddress());

					POIFilter mFilter = new POIFilter(r.getMacString(),
							exsitingPOI);
					// mFilter.isMatch()
					if (mFilter.isMatch()) {

						Short rssi = intent.getShortExtra(
								BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);

						if (mNewDevicesArrayAdapter.isEmpty()) { // poi list is
							// empty

							mNewDevicesArrayAdapter.add(rssi + "\n"
									+ device.getName() + "\n"
									+ device.getAddress());

						} else {
							// poi list is not empty

							while (currentIndex < mNewDevicesArrayAdapter
									.getCount()) {
								tempRSSI = Integer
										.parseInt(mNewDevicesArrayAdapter
												.getItem(currentIndex)
												.substring(0, 3));
								if (rssi.intValue() >= tempRSSI) {

									mNewDevicesArrayAdapter
											.insert(rssi + "\n"
													+ device.getName() + "\n"
													+ device.getAddress(),
													currentIndex);

									currentIndex = mNewDevicesArrayAdapter
											.getCount() + 1;

								} else {
									currentIndex++;
								}
							}
							if (currentIndex == mNewDevicesArrayAdapter
									.getCount()) {
								mNewDevicesArrayAdapter.add(rssi + "\n"
										+ device.getName() + "\n"
										+ device.getAddress());

							}
						}
					}
					// for logging
					/*
					 * if (device.getName().equals("Zhenan")) { DataLogger dl =
					 * new DataLogger(device.getName(), device .getAddress(),
					 * rssi.intValue()); }
					 */

					// When discovery is finished, change the Activity title
				} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
						.equals(action)) {
					setProgressBarIndeterminateVisibility(false);
					setTitle(R.string.scan_finish);
					/*
					 * if (mNewDevicesArrayAdapter.getCount() == 0) { String
					 * noDevices = getResources().getText(
					 * R.string.none_found).toString();
					 * mNewDevicesArrayAdapter.add(noDevices); }
					 */
				}
			} catch (Exception e) {
				Log.w(TAG, "ex" + "crash");
			}
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
		case R.id.menu_scan:
			doDiscovery();

			break;
		case R.id.menu_clear:
			doClear();
			break;

		}
		return true;
	}
}