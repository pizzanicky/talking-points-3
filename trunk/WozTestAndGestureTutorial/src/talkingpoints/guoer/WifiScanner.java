package talkingpoints.guoer;

//import java.util.Calendar;
//import java.util.Timer;
//import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;

import talkingpoints.guoer.WifiList.RemoteServiceConnection;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class WifiScanner extends Service {

	private NotificationManager mNM;
	// private Timer timer = new Timer();
	private long refreshRate = 12000L;
	
	//private BluetoothAdapter mBtAdapter;
	
	//WIFI 
	WifiManager mainWifi;
	//WifiReceiver receiverWifi;
	private BroadcastReceiver receiverWifi = null;
	
	//StringBuilder sb = new StringBuilder(); 	
	List<ScanResult> wifiList; 
	
	private Handler serviceHandler;
	public static boolean started = false;
	public static RemoteServiceConnection conn = null;
	
	//private int counter;
	private Task myTask = new Task();
	//String[] exsitingPOI = { "00066602CD99", "00066602CD8E" };

	// debug rssi
	// private static final String TAG = "MAC = ";
	// private int tempRSSI;
	// private int currentIndex;
	private List<String> mNewDevicesArrayAdapter;

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(getClass().getSimpleName(), "onBind()");
		return myRemoteServiceStub;
	}

	private IRemoteService.Stub myRemoteServiceStub = new IRemoteService.Stub() {
		public List<String> getWifiList() throws RemoteException {
			return mNewDevicesArrayAdapter;
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();

		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		receiverWifi = new WifiReceiver();	
		
		// Setup Wifi manager 
		mainWifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		mNewDevicesArrayAdapter = new ArrayList<String>();		
			
		IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		this.registerReceiver(receiverWifi, filter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mNM.cancel(R.string.sensor_service_started);
		serviceHandler.removeCallbacks(myTask);
		serviceHandler = null;
		Log.d(getClass().getSimpleName(), "onDestroy()");
		
//		// Unregister broadcast listeners
		this.unregisterReceiver(receiverWifi);
		Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(this, "On Start!", Toast.LENGTH_SHORT).show();
		serviceHandler = new Handler();
		//serviceHandler.postDelayed(myTask, refreshRate);
		Log.d(getClass().getSimpleName(), "onStart()");				
		
		Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
		
		doDiscovery();
		showNotification();
	}

	class Task implements Runnable {
		public void run() {
			// Scan for bluetooth devices refreshRate times.
			//++counter;
			serviceHandler.postDelayed(this, refreshRate);
			// doDiscovery();
			// doClear();
			//Log.i(getClass().getSimpleName(), "Scanning...");
		}
	}

	private void doClear() {	
		mainWifi.disconnect();
		mNewDevicesArrayAdapter.clear();
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery() {

		//Toast.makeText(this, "doDiscovery!", Toast.LENGTH_SHORT).show();
		// If we're already discovering, stop it
		if( mainWifi.isWifiEnabled() ) {
			mainWifi.disconnect();
		}
		mNewDevicesArrayAdapter.clear();
		// Request discover from BluetoothAdapter		
		mainWifi.startScan();
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

    class WifiReceiver extends BroadcastReceiver { 
        public void onReceive(Context c, Intent intent) { 
    
           wifiList = mainWifi.getScanResults();
           
           for(int i = 0; i < wifiList.size(); i++) { 
          	 
             ScanResult scan = wifiList.get(i);
             
             //if (poiHash.get(scan.BSSID) != null)
             {
             
          	   int dist = (int) Utilities.calcDistance(scan.level);
          	   //Log.d("Distance", "" + dist);
          	   Log.w("list debug", "list size = "+ mNewDevicesArrayAdapter.size());
          	   
          	   mNewDevicesArrayAdapter.add("BSSID: " + scan.BSSID + "\nDIST: " + dist + "\nRSSI: "
          			   + scan.level);

              }   
           }                          
        } 
   } 
}


