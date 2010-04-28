package talkingpoints.guoer;

import java.util.ArrayList;
import java.util.List;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class BTlist extends GestureUI {
	// fake POI
	// String[] exsitingPOI = { "sdf", "002608D712B9", "1234567890" };
	public static BTlist scanning_home = null;
	private Timer m_timerForSensorUpdate = null;
	// debug rssi
	private static final String TAG = "MAC = ";
	public static final int UPDATEIDENTIFIER = 0;
	public static final int CREATPANEL = 1;
	private RemoteService remoteService;
	// private boolean started = false;

	private List<String> cachList = null;
	public static String GET_NEARBY_BYLATLONG1 = "http://app.talking-points.org/locations/";
	public static String GET_NEARBY_BYLATLONG2 = "/get_nearby.xml";
	// private int tempRSSI;
	// private int currentIndex;
	ListComparer NewItemfilter;
	private List<String> NofiticationList;
	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTA_DEVICE_RSSI = "device_rssi";
	public static ArrayList<String[]> MasterTags;
	// private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	public static ArrayAdapter<String> mNewDevicesArrayAdapter;
	// private ArrayList<String> POIOptions;
	public static ArrayList<String> MacAddr;
	public int m_periodUpdate = 3000;
	// Background service scanner
	private BTScanner btScanner;
	public static boolean isRunning;
	public Thread t;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	public static boolean foundMasterTag;

	public static int index_MT = 0;
	public static ArrayList<String> nearbyPOIs;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		BTlist.foundMasterTag = false;
		pageName = new String("Scanning Locations");
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// setContentView(R.layout.main);
		setResult(Activity.RESULT_CANCELED);
		// POIOptions = new ArrayList<String>();
		MacAddr = new ArrayList<String>();
		nearbyPOIs = new ArrayList<String>();
		foundMasterTag = false;
		MasterTags = new ArrayList<String[]>();
		String[] mt1 = new String[] { "00:1e:8d:19:83:24",
				"42.274863748954786", "-83.74122619628906" };
		MasterTags.add(mt1);

		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);

		cachList = new ArrayList<String>();

		startService();
		bindService();

		t = new Thread(new myThread());
		t.start();
		isRunning = true;

		scanning_home = this;
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

		} else {
			try {

				List<String> tempList = remoteService.getBTList();
				// this.mTts.speak("Scanning", TextToSpeech.QUEUE_FLUSH, null);
				if (tempList.size() == 0) {
					Toast.makeText(BTlist.this,
							mNewDevicesArrayAdapter.getCount() + " POI found!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(BTlist.this, tempList.size() + " POI found",
							Toast.LENGTH_SHORT).show();
					// if (tempList.size() == 1)
					// sayPageName("One location detected");
					// else
					// sayPageName(tempList.size() + " locations detected");
					if (cachList != null) {
						NewItemfilter = new ListComparer(tempList, cachList);
						// the new found devices list
						NofiticationList = NewItemfilter.getNewItems();

						// notify new devices found
						if (NofiticationList != null
								&& NofiticationList.size() > 0) {
							for (int i = 0; i < NofiticationList.size(); i++) {

								Toast.makeText(BTlist.this,
										NofiticationList.get(i) + " found!",
										Toast.LENGTH_SHORT).show();
							}
						}

						cachList.clear();
					}

					mNewDevicesArrayAdapter.clear();
					// Here we get data from RemoteService.

					for (int i = 0; i < tempList.size(); i++) {
						mNewDevicesArrayAdapter.add(tempList.get(i));
						// cachList.add(tempList.get(i));

					}

					cachList = tempList;
					updateList();
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
			case BTlist.CREATPANEL:
				if (options.size() == 0)
					// updateList();
					break;
			}
			// super.handleMessage(msg);
		}
	};

	public void updateList() {

		this.options.clear();
		// fetch name into options, fetch MAC address into MacAddress
		for (int i = 0; i < mNewDevicesArrayAdapter.getCount(); i++) {

			String temp[] = mNewDevicesArrayAdapter.getItem(i).toString()
					.split("\n");
			this.options.add(temp[1]);
			// if temp[2] is a master tag MAC address and the temp[0] value
			// (rssi value) is big enough
			MacAddr.add(temp[2]);

			for (index_MT = 0; index_MT < BTlist.MasterTags.size(); index_MT++) {
				if ((temp[2]
						.equalsIgnoreCase(BTlist.MasterTags.get(index_MT)[0]))
						&& Integer.valueOf(temp[0]) > -60) {
					// this.mTts.speak("Orientation Direction Available",
					// TextToSpeech.QUEUE_FLUSH, null);
					mNewDevicesArrayAdapter.insert(mNewDevicesArrayAdapter
							.getItem(i), 0);
					this.options.add(0, "Intersection Point");
					mNewDevicesArrayAdapter.remove(mNewDevicesArrayAdapter
							.getItem(i + 1));
					this.options.remove(i + 1);
					this.MacAddr.add(0, temp[2]);
					this.MacAddr.remove(i + 1);

					nearbyPOIs = this.options;
					foundMasterTag = true;
				} else if ((temp[2].equalsIgnoreCase(BTlist.MasterTags
						.get(index_MT)[0]))
						&& Integer.valueOf(temp[0]) <= -60) {
					mNewDevicesArrayAdapter.remove(mNewDevicesArrayAdapter
							.getItem(i));
					this.options.remove(i);
					this.MacAddr.remove(i);
					nearbyPOIs = this.options;
					foundMasterTag = false;
				}

				if (foundMasterTag)
					break;
			}
		}

		// BTlist.ui = new Panel(this, POIOptions);

		// BTlist.ui.gestureScanner
		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				if (GestureUI.selected == 0 && BTlist.foundMasterTag) {

					NearbyParser p = new NearbyParser(
							BTlist.GET_NEARBY_BYLATLONG1
									+ BTlist.MasterTags.get(index_MT)[0]
									+ BTlist.GET_NEARBY_BYLATLONG2);

					AngleCalculator oc = new AngleCalculator(p.getLatitude(), p
							.getlongitude(), MasterTags.get(index_MT)[1],
							MasterTags.get(index_MT)[2]);

					oc.getAngle();

					Intent intent = new Intent(BTlist.this, POIsAhead.class);
					startActivity(intent);

				} else {
					Intent intent = new Intent(BTlist.this, POImenu.class);
					MacReader r = new MacReader(BTlist.MacAddr
							.get(GestureUI.selected));
					intent.putExtra("MAC", r.getMacString());
					intent.putExtra("POIname", BTlist.nearbyPOIs
							.get(GestureUI.selected));
					startActivity(intent);
				}
				return true;
			}

			@Override
			public boolean onDoubleTapEvent(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		// this.setContentView(ui);

		// if (foundMasterTag)
		// GetNearby(index_MT);

	}

	// public void GetNearby(int index_MT) {
	//
	// }

	class myThread implements Runnable {

		public void run() {
			while (!Thread.currentThread().isInterrupted() && BTlist.isRunning) {
				Message message = new Message();
				Message msg = new Message();
				message.what = BTlist.UPDATEIDENTIFIER;
				msg.what = BTlist.CREATPANEL;
				BTlist.this.myHandler.sendMessage(message);
				try {
					Thread.sleep(5000);
					if (// !panel_created&&
					BTlist.mNewDevicesArrayAdapter.getCount() > 0) {
						BTlist.this.myHandler.sendMessage(msg);
						// panel_created = true;
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	// @Override
	// protected void onRestart() {
	// super.onRestart();
	// pageInfo.setText(pageName);
	// sayPageName(pageName);
	// }
}
