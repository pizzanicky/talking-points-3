package talkingpoints.guoer;

//import java.util.Calendar;
//import java.util.Timer;
//import java.util.TimerTask;
import java.util.ArrayList;
import java.util.HashMap;
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
	private BroadcastReceiver receiverWifi = null;
	List<ScanResult> wifiList; 
	HashMap<String, String> poiHash;
	
	// Trilaterlation Equation
	// Get coordinates	
	private double[][] wifiLocation;
	private double[] myLocation = new double[2]; 
	
	
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
		
		// SI NORTH first floor 
        double lat1 = 42.289011;
        double lng1 = -83.714281;
        double lat2 = 42.288958;
        double lng2 = -83.714375;
        double lat3 = 42.288919;
        double lng3 = -83.714281;
             
        poiHash = new HashMap<String, String>();  
        // Lab Test MAC version
        poiHash.put("00:40:5a:21:61:59", lat1 + "," + lng1);	// TalkingPoints_WAP1 
        poiHash.put("00:01:36:31:a0:39", lat2 + "," + lng2);	// TalkingPoints_WAP2
        poiHash.put("00:40:5a:21:d9:85", lat3 + "," + lng3);    // TalkingPoints_WAP3
             
        // Home Test MAC version
//        poiHash.put("00:01:36:35:bd:8e", lat1 + "," + lng1);	// TalkingPoints_WAP1 
//        poiHash.put("00:08:9f:0d:9d:48", lat2 + "," + lng2);	// TalkingPoints_WAP2
//        poiHash.put("00:22:75:53:cf:16", lat3 + "," + lng3);    // TalkingPoints_WAP3
        
        // HJ - 2010.6.24
        // wifiLocation[Array1][Array2]
        // Array1 : 3 WAP readings
        // Array2 : [0] Latitude
        //			[1] Longitude
        //			[2] Level (Power reading)
        //			[3] distance
        wifiLocation = new double[3][4]; 		
        
        wifiLocation[0][0] = lat1;
        wifiLocation[0][1] = lng1;
        wifiLocation[0][2] = -10.0;
   	    wifiLocation[0][3] = 20.0;
   	    
   	    wifiLocation[1][0] = lat2;
	   	wifiLocation[1][1] = lng2;
	    wifiLocation[1][2] = -10.0;
	    wifiLocation[1][3] = 10.0;
	    
	    wifiLocation[2][0] = lat3;
   	   	wifiLocation[2][1] = lng3;
   	    wifiLocation[2][2] = -10.0;
   	    wifiLocation[2][3] = 10.0;
		
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
				new Intent(this, GateWayForWifiWoz.class), 0);

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
           String loc;
           String[] lat_lng;
           
           for(int i = 0; i < wifiList.size(); i++) { 
          	 
             ScanResult scan = wifiList.get(i);
             
             // POI Hash filtering 
             // showing only registered WIFI APs
             //if ((loc=poiHash.get(scan.BSSID)) != null)
             {             
          	   //int dist = (int) WifiCoordinator.calcDistance(scan.level);
          	   //Log.d("Distance", "" + dist);

          	   //lat_lng = loc.split(",");
          	   //wifiLocation[i][0] = Double.parseDouble(lat_lng[0]);
          	   //wifiLocation[i][1] = Double.parseDouble(lat_lng[1]);
          	   //wifiLocation[i][2] = scan.level;
          	   //wifiLocation[i][3] = dist;
             }             
           } 
             
             //if (poiHash.get(scan.BSSID) != null)
             {             
          	   //int dist = (int) WifiCoordinator.calcDistance(scan.level);
          	   //Log.d("Distance", "" + dist);
            	 
               myLocation = WifiCoordinator.MyTrilateration(wifiLocation[0][0], wifiLocation[0][1], wifiLocation[0][2],
              		   wifiLocation[1][0], wifiLocation[1][1], wifiLocation[1][2], 
              		   wifiLocation[2][0], wifiLocation[2][1], wifiLocation[2][2]);
            	
          	   Log.w("list debug", "list size = "+ mNewDevicesArrayAdapter.size());
          	 
          	   mNewDevicesArrayAdapter.add("MyLoc: " + myLocation[0] + ", " + myLocation[1]
                       + "\nWAP1: " + wifiLocation[0][0] + ", " + wifiLocation[0][1] + ", " + wifiLocation[0][3] + "\n"
          			   + "\nWAP2: " + wifiLocation[1][0] + ", " + wifiLocation[1][1] + ", " + wifiLocation[1][3] + "\n"
          			   + "\nWAP3: " + wifiLocation[2][0] + ", " + wifiLocation[2][1] + ", " + wifiLocation[2][3]);

              }   
                                    
        } 
   } 
}


