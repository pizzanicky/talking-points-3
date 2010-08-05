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
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class WifiList extends GestureUI {
	// fake POI
	// String[] exsitingPOI = { "sdf", "002608D712B9", "1234567890" };
	public static WifiList scanning_home = null;
	private Timer m_timerForSensorUpdate = null;
	// debug rssi
	private static final String TAG = "MAC = ";
	public static final int UPDATEIDENTIFIER = 0;
	public static final int CREATPANEL = 1;
	private IRemoteService remoteService;
	// private boolean started = false;

	private ArrayList<String> cachList = null;
	public static String GET_NEARBY_BYLATLONG1 = "http://app.talking-points.org/locations/";
	public static String GET_NEARBY_BYLATLONG2 = "/get_nearby.xml";
	
	private static String BY_COORDINATE1 = "http://app.talking-points.org/locations/by_coordinates/";
	private static String BY_COORDINATE2 = ".xml?within=2";

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
	private WifiScanner mWifiScanner;
	public static boolean isRunning;
	public Thread t;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	public static boolean foundMasterTag;
	private ArrayList<String> MenuOptions;

	public static int index_MT = 0;
	public static ArrayList<String> nearbyPOIs;

	private static boolean flag0=false;
	private static boolean flag=false;

	public static String LAC1="haha"; //changed from private to public static 
	public static String LNG1="haha"; // ''
	byCoordinateParser p2;
	private static int countGesture=0; 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		WifiList.foundMasterTag = false;
		pageName = new String("Scanning Wifi Locations");
	 
			
			
			MenuOptions = new ArrayList<String>();
			MenuOptions.add("Detect locations within 20 feet"); //Bert's calf ay
		//	MenuOptions.add("Quick Tutorial");
			MenuOptions.add("Flashlight");
//			MenuPOINameWithDistance.add("Keyword Search");
//			MenuPOINameWithDistance.add("Flashlight");
			pageName = new String("Talking Points Home. Swipe down to hear menu options. Double tap to select.");
			
			WozList.foundMasterTag = false;
	 		 
			
			super.onCreate(savedInstanceState, MenuOptions);			// requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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

		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

		cachList = new ArrayList<String>();

		startService();
		bindService();

		t = new Thread(new myThread());
		t.start();
		isRunning = true;

		scanning_home = this;
	 
	super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
		public boolean onDoubleTap(MotionEvent e) {
			if(flag0)
			{
				switch (GestureUI.selected) {
				case 0:
				{
					releaseSoundEffect();
					playSound(NEXT_PAGE);
					
					Intent intent = new Intent(WifiList.this, DetectedLocations.class);
					 
//					intent.putStringArrayListExtra("POINameWithDistance",POINameWithDistance);
//					intent.putStringArrayListExtra("POIName",onlyPOInames);
//					intent.putStringArrayListExtra("tpids", tpids);
//					intent.putStringArrayListExtra("MacAddr", MacAddr);
					
					startActivity(intent);
					break;
				}
				case 1: //flashlight 
					{

						if(byCoordinateParser.getLatitude().size()>0)
						{
					   		AngleCalculator oc = new AngleCalculator(byCoordinateParser.getLatitude(), byCoordinateParser
								.getlongitude(),LAC1,
								LNG1);

					   		oc.getAngle();

					   		releaseSoundEffect();
							playSound(NEXT_PAGE);
							
					   		Intent intent2 = new Intent(WifiList.this, POIsAhead.class);
					   		startActivity(intent2); 
	 
						}
						else 
						{
							mTts.speak("There is no internet connection. Please check", TextToSpeech.QUEUE_FLUSH, null);
						}

			 
					   } 
					break;
				}
				flag0=false; 
			}
			return true;
		}

		public boolean onDoubleTapEvent(MotionEvent e) {
			return false;
		}

		public boolean onSingleTapConfirmed(MotionEvent e) {
	
			 
			countGesture++;
			
			if(countGesture==2)
			{	
//				Toast.makeText(getApplicationContext(),"countG: "+countGesture,Toast.LENGTH_SHORT).show();
				countGesture=0;
				switch (GestureUI.selected) {
				case 0:
					releaseSoundEffect();
					playSound(NEXT_PAGE);
					
					Intent intent = new Intent(WifiList.this, DetectedLocations.class);
					 
//					intent.putStringArrayListExtra("POINameWithDistance",POINameWithDistance);
//					intent.putStringArrayListExtra("POIName",onlyPOInames);
//					intent.putStringArrayListExtra("tpids", tpids);
//					intent.putStringArrayListExtra("MacAddr", MacAddr);
					
					startActivity(intent);
					break;
//				case 1:
//					releaseSoundEffect();
//					playSound(NEXT_PAGE);
//					
//					Intent intent1 = new Intent(BTlist.this, Tutorial.class);
//					startActivity(intent1);
//					break;
				case 1: //flashlight 
					{

						if(byCoordinateParser.getLatitude().size()>0)
						{
					   		AngleCalculator oc = new AngleCalculator(byCoordinateParser.getLatitude(), byCoordinateParser
								.getlongitude(),LAC1,
								LNG1);

					   		oc.getAngle();

					   		releaseSoundEffect();
							playSound(NEXT_PAGE);
							
					   		Intent intent2 = new Intent(WifiList.this, POIsAhead.class);
					   		startActivity(intent2); 
	 
						}
						else 
						{
							mTts.speak("There is no internet connection. Please check", TextToSpeech.QUEUE_FLUSH, null);
						}

			 
					   } 
					break;
				}
			}
			return false;
		}

	});

}
@Override
public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
		float distanceY) {
	flag = true; 
	flag0 = true; 
	
	return true;

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
		public void onServiceConnected(ComponentName className,IBinder boundService) {
			remoteService = IRemoteService.Stub.asInterface((IBinder) boundService);
			Log.d(getClass().getSimpleName(), "onServiceConnected()");
		}

		public void onServiceDisconnected(ComponentName className) {
			remoteService = null;
			// updateServiceStatus();
			Log.d(getClass().getSimpleName(), "onServiceDisconnected");
		}
	};

	private void startService() {
		if (WifiScanner.started) {
			Toast.makeText(WifiList.this, "Service already started",
					Toast.LENGTH_SHORT).show();
		} else {
			Intent i = new Intent();
			i.setClassName("talkingpoints.guoer",
					"talkingpoints.guoer.WifiScanner");
			startService(i);
			
			
//			Intent service = new Intent(WifiScanner.WIFI_SERVICE);
//			startService(service);
			
			WifiScanner.started = true;
			// updateServiceStatus();
			Log.d(getClass().getSimpleName(), "startService()");
		}
	}

	private void stopService() {
		t.stop();
		try {
			if (!WifiScanner.started) {
				Toast.makeText(WifiList.this, "Service not yet started",
						Toast.LENGTH_SHORT).show();
			} else {
				Intent i = new Intent();
				i.setClassName("talkingpoints.guoer",
						"talkingpoints.guoer.WifiScanner");
				stopService(i);
				WifiScanner.started = false;
				
//				Intent service = new Intent(WifiScanner.WIFI_SERVICE);
//				stopService(service);				
				
				// updateServiceStatus();
				Log.d(getClass().getSimpleName(), "stopService()");
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

	private void bindService() {
		if (WifiScanner.conn == null) {
			WifiScanner.conn = new RemoteServiceConnection();
			Log.d(getClass().getSimpleName(), "conn = " + WifiScanner.conn);
			
			// for some reason bindService doesn't work with child of
			// TabActivity, so we use getApplicationContext().bindService here
			// Intent i = new Intent(WifiList.this, WifiScanner.class);			
			// getApplicationContext().bindService(i, WifiScanner.conn,
			//		Context.BIND_AUTO_CREATE);
			
			Intent i = new Intent();
			i.setClassName("talkingpoints.guoer", "talkingpoints.guoer.WifiScanner");
			//getApplicationContext().bindService(i, WifiScanner.conn, Context.BIND_AUTO_CREATE);			
			bindService(i, WifiScanner.conn, Context.BIND_AUTO_CREATE);
			
			// updateServiceStatus();
			Log.d(getClass().getSimpleName(), "bindService()");
		} else {
			Toast.makeText(WifiList.this, "Cannot bind - service already bound",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void releaseService() {
		try {
			if (WifiScanner.conn != null) {
				//getApplicationContext().
				unbindService(WifiScanner.conn);
				WifiScanner.conn = null;
				// updateServiceStatus();
				Log.d(getClass().getSimpleName(), "releaseService()");
			} else {
				// bindService();
				//getApplicationContext().
				unbindService(WifiScanner.conn);
				WifiScanner.conn = null;
				Toast.makeText(WifiList.this,
								"Cannot unbind - service not bound",
								Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

	public void invokeService() {
		if (WifiScanner.conn == null) {
			Toast.makeText(WifiList.this, "Cannot refresh - service not bound",
					Toast.LENGTH_SHORT).show();
		} else {
			try {
				ArrayList<String> tempList = (ArrayList<String>) remoteService.getWifiList();
				// this.mTts.speak("Scanning", TextToSpeech.QUEUE_FLUSH, null);
				if (tempList.size() == 0) {
					Toast.makeText(WifiList.this,
							mNewDevicesArrayAdapter.getCount() + " POI found!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(WifiList.this, tempList.size() + " POI found",
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

								Toast.makeText(WifiList.this, NofiticationList.get(i),
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
				Log.e(getClass().getSimpleName(), "??Exception:"
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
			if (!WifiScanner.started) {
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
			case WifiList.UPDATEIDENTIFIER:
				invokeService();
				break;
			case WifiList.CREATPANEL:
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

			for (index_MT = 0; index_MT < WifiList.MasterTags.size(); index_MT++) {
				if ((temp[2]
						.equalsIgnoreCase(WifiList.MasterTags.get(index_MT)[0]))
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
				} else if ((temp[2].equalsIgnoreCase(WifiList.MasterTags
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

		// WifiList.ui = new Panel(this, POIOptions);

		// WifiList.ui.gestureScanner
//		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
//			@Override
//			public boolean onDoubleTap(MotionEvent e) {
//				if (GestureUI.selected == 0 && WifiList.foundMasterTag) {
//
//					NearbyParser p = new NearbyParser(
//							WifiList.GET_NEARBY_BYLATLONG1
//									+ WifiList.MasterTags.get(index_MT)[0]
//									+ WifiList.GET_NEARBY_BYLATLONG2);
//
//					AngleCalculator oc = new AngleCalculator(p.getLatitude(), p
//							.getlongitude(), MasterTags.get(index_MT)[1],
//							MasterTags.get(index_MT)[2]);
//
//					oc.getAngle();
//
//					Intent intent = new Intent(WifiList.this, POIsAhead.class);
//					startActivity(intent);
//
//				} else {
//					Intent intent = new Intent(WifiList.this, POImenu.class);
//					MacReader r = new MacReader(WifiList.MacAddr
//							.get(GestureUI.selected));
//					intent.putExtra("MAC", r.getMacString());
//					intent.putExtra("POIname", WifiList.nearbyPOIs
//							.get(GestureUI.selected));
//					startActivity(intent);
//				}
//				return true;
//			}
//
//			@Override
//			public boolean onDoubleTapEvent(MotionEvent e) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean onSingleTapConfirmed(MotionEvent e) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		});
		// this.setContentView(ui);

		// if (foundMasterTag)
		// GetNearby(index_MT);

	}

	// public void GetNearby(int index_MT) {
	//
	// }

	class myThread implements Runnable {

		public void run() {
			while (!Thread.currentThread().isInterrupted() && WifiList.isRunning) {
				Message message = new Message();
				Message msg = new Message();
				message.what = WifiList.UPDATEIDENTIFIER;
				msg.what = WifiList.CREATPANEL;
				WifiList.this.myHandler.sendMessage(message);
				try {
					Thread.sleep(5000);
					if (// !panel_created&&
					WifiList.mNewDevicesArrayAdapter.getCount() > 0) {
						WifiList.this.myHandler.sendMessage(msg);
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
