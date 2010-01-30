package talkingpoints.guoer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class BTlist extends Activity {

	// debug rssi
	private static final String TAG = "RSSI = ";

	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTA_DEVICE_RSSI = "device_rssi";

	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);
		setResult(Activity.RESULT_CANCELED);

		// Initialize the button to perform device discovery
		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doDiscovery();
				// v.setVisibility(View.);
			}
		});

		Button clearButton = (Button) findViewById(R.id.button_clear);
		clearButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClear();
				// v.setVisibility(View.);
			}
		});

		// Initialize array adapters.
		// for newly discovered devices

		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);

		// Find and set up the ListView for newly discovered devices
		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);// ??
		// new_devices
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		// newDevicesListView.setOnItemClickListener(mDeviceClickListener);

		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

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
		mNewDevicesArrayAdapter.clear();
	}

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,
						Short.MIN_VALUE);
				// Log.w(TAG, "RSSI = " + rssi);

				mNewDevicesArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress() + "\n" + "RSSI = " + rssi);

				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				setTitle(R.string.scan_finish);
				if (mNewDevicesArrayAdapter.getCount() == 0) {
					String noDevices = getResources().getText(
							R.string.none_found).toString();
					mNewDevicesArrayAdapter.add(noDevices);
				}
			}
		}
	};
}