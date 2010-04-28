package talkingpoints.guoer;

//import java.util.Calendar;
//import java.util.Timer;
//import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;

import talkingpoints.guoer.BTlist.RemoteServiceConnection;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class BTScanner extends Service {

	private NotificationManager mNM;
	// private Timer timer = new Timer();
	private long refreshRate = 12000L;
	private BluetoothAdapter mBtAdapter;
	private Handler serviceHandler;
	public static boolean started = false;
	public static RemoteServiceConnection conn = null;
	private int counter;
	private Task myTask = new Task();
	String[] exsitingPOI = { "00066602CD99", "00066602CD8E" };

	// debug rssi
	private static final String TAG = "MAC = ";
	private int tempRSSI;
	private int currentIndex;
	private List<String> mNewDevicesArrayAdapter;

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(getClass().getSimpleName(), "onBind()");
		return myRemoteServiceStub;
	}

	private RemoteService.Stub myRemoteServiceStub = new RemoteService.Stub() {
		public int getCounter() throws RemoteException {
			return counter;
		}

		public List<String> getBTList() throws RemoteException {
			return mNewDevicesArrayAdapter;
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		mNewDevicesArrayAdapter = new ArrayList<String>();
		// new ArrayAdapter<String>(this,R.layout.device_name);

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);
		// timer.scheduleAtFixedRate(new TimerTask(){ public void run()
		// {doWork();}}, 0, refreshRate);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mNM.cancel(R.string.sensor_service_started);
		serviceHandler.removeCallbacks(myTask);
		serviceHandler = null;
		Log.d(getClass().getSimpleName(), "onDestroy()");
		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
		Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		serviceHandler = new Handler();
		serviceHandler.postDelayed(myTask, refreshRate);
		Log.d(getClass().getSimpleName(), "onStart()");
		Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();

		doDiscovery();
		showNotification();
	}

	class Task implements Runnable {
		public void run() {
			// Scan for bluetooth devices refreshRate times.
			++counter;
			serviceHandler.postDelayed(this, refreshRate);
			doDiscovery();
			// doClear();
			Log.i(getClass().getSimpleName(), "Scanning...");
		}
	}

	private void doClear() {
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		mNewDevicesArrayAdapter.clear();
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery() {

		// Indicate scanning in the title
		// setProgressBarIndeterminateVisibility(true);
		// setTitle(R.string.scanning);

		// Turn on sub-title for new devices
		// findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		mNewDevicesArrayAdapter.clear();
		// Request discover from BluetoothAdapter
		mBtAdapter.startDiscovery();
	}

	private void showNotification() {

		CharSequence text = getText(R.string.sensor_service_started);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.icon, text,
				System.currentTimeMillis());

		// The PendingIntent to launch our activity if the user selects this
		// notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, GateWay.class), 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this,
				getText(R.string.notification_label), text, contentIntent);

		// Send the notification.
		// We use a layout id because it is a unique number. We use it later to
		// cancel.
		mNM.notify(R.string.sensor_service_started, notification);
	}

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
					if (true) {
						Log.w("list debug", "list size = "
								+ mNewDevicesArrayAdapter.size());
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
									.size()) {
								tempRSSI = Integer
										.parseInt(mNewDevicesArrayAdapter.get(
												currentIndex).substring(0, 3));
								if (rssi.intValue() >= tempRSSI) {

									mNewDevicesArrayAdapter.add(currentIndex,
											"" + rssi + "\n" + device.getName()
													+ "\n"
													+ device.getAddress());

									currentIndex = mNewDevicesArrayAdapter
											.size() + 1;

								} else {
									currentIndex++;
								}
							}
							if (currentIndex == mNewDevicesArrayAdapter.size()) {
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
					// setProgressBarIndeterminateVisibility(false);
					// setTitle(R.string.scan_finish);
					/*
					 * if (mNewDevicesArrayAdapter.getCount() == 0) { String
					 * noDevices = getResources().getText(
					 * R.string.none_found).toString();
					 * mNewDevicesArrayAdapter.add(noDevices); }
					 */
				}
			} catch (Exception e) {
				// Log.w(TAG, "ex" + "crash");
			}
		}
	};

}
