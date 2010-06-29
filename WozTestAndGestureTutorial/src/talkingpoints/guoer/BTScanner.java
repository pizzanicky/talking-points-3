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
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
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
	private long refreshRate = 12000L; //12000L
	//private BluetoothAdapter mBtAdapter;
	private Handler serviceHandler;
	public static boolean started = false;
	public static RemoteServiceConnection conn = null;
	//public static talkingpoints.guoer.POIsAhead2.RemoteServiceConnection conn2 = null;
	private int counter=0;
	private Task myTask = new Task();
	String[] exsitingPOI = { "00066602CD99", "00066602CD8E" };

	// debug rssi
	private static final String TAG = "MAC = ";
	private int tempRSSI=0;
	private int currentIndex=0;
	private List<String> mNewDevicesArrayAdapter;

	WozParser p1;
	
	private static String GET_COORDINATE ="http://talkingpoints.dreamhosters.com/maps_test/point.xml";
	private static String StringLac="haha";
	private static String StringLng="hoho";
	//http://talkingpoints.dreamhosters.com/maps_test/
 	
	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(getClass().getSimpleName(), "onBind()");
		return myRemoteServiceStub;
		
		
	}
//
	private RemoteService.Stub myRemoteServiceStub = new RemoteService.Stub() {
		public int getCounter() throws RemoteException {
			return counter;
		}

		public List<String> getBTList() throws RemoteException {
			return mNewDevicesArrayAdapter;
		}
		public String getLac() throws RemoteException {
			return StringLac;		
		}
		public String getLng() throws RemoteException {
			return StringLng;
		}
		public List<String> getWIFI() throws RemoteException {
			return mNewDevicesArrayAdapter;
		} 
	};

	@Override
	public void onCreate() {
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Get the local Bluetooth adapter
		//mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		mNewDevicesArrayAdapter = new ArrayList<String>();
		// new ArrayAdapter<String>(this,R.layout.device_name);
 
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mNM.cancel(R.string.sensor_service_started);
		serviceHandler.removeCallbacks(myTask);
		serviceHandler = null;
		Log.d(getClass().getSimpleName(), "onDestroy()");
		// Unregister broadcast listeners
	//	this.unregisterReceiver(mReceiver);
		Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		serviceHandler = new Handler();
		serviceHandler.postDelayed(myTask, refreshRate);
		Log.d(getClass().getSimpleName(), "onStart()");
		Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
		getCoordinates();

 
	}

	class Task implements Runnable {
		public void run() {
			// Scan for bluetooth devices refreshRate times.
			++counter;
			serviceHandler.postDelayed(this, refreshRate);
 
			getCoordinates();
 			Log.i(getClass().getSimpleName(), "Scanning...");
			
		}
	}
	  private void getCoordinates(){
	      	Toast.makeText(this,"Get coordinate!", Toast.LENGTH_SHORT).show();

	   	p1 = new WozParser(GET_COORDINATE);
	    	
	    	  StringLac = WozParser.getLatitude();
	          StringLng = WozParser.getLongitude();
	        
	//   LAC = WozParser.getLatitude();
	 //  LNG = WozParser.getLongitude();
	   //   	Toast.makeText(this,"Lat:::"+WozParser.getLatitude()+"Lng:::"+WozParser.getLongitude(), Toast.LENGTH_SHORT).show();
			
	       

	          
	    }
	  

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	 
 

}
