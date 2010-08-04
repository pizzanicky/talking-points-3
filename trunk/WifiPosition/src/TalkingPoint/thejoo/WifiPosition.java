package TalkingPoint.thejoo;

import TalkingPoint.thejoo.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.skyhookwireless.wps.*;

public class WifiPosition extends Activity {
    /** Called when the activity is first created. */
	
	private WifiManager mainWifi;
	private WifiReceiver receiverWifi;
	private SensorManager sensorManager;
	
//	// WPS SETTING
//	private WPS	wps;
//	private WPSAuthentication auth;
//	private String myUsername = null;
//	private String myRealm = null;
//	
//	private MyLocationCallback _callback;
//	private Handler _handler;
//	private WPSStreetAddressLookup _streetAddressLookup;	
	
	private double[][] wifiLocation;
//	private double myLatitude, myLongitude;
	private double[] myLocation = new double[2]; 
	
	private float CompassValue = 0;
	String presentLoc;
	boolean toScan = false;
	
	private int SamplingIntval = 2000;
	
	Time time;
	TextView mainText;
	TextView txtLocation;
	TextView txtMyLocation;
	TextView txtCompass;
	List<ScanResult> wifiList;
	Button btnRefresh;
	Button btnLog;	
	Button btnGMap;
	Button btnSampling;
	Spinner spnSetting;
	
	StringBuilder sb = new StringBuilder();
	BufferedWriter logFile;
	String logName;
	HashMap<String, String> poiHash;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        txtMyLocation = (TextView)findViewById(R.id.txtmyLocation);
        txtCompass 	= (TextView)findViewById(R.id.txtCompass);
        mainText 	= (TextView)findViewById(R.id.mainText);
        btnRefresh	= (Button)findViewById(R.id.btnRefresh);
        btnLog		= (Button)findViewById(R.id.btnLog);
        btnGMap		= (Button)findViewById(R.id.btnMap);
        btnSampling = (Button)findViewById(R.id.btnSampling);
        spnSetting 	= (Spinner)findViewById(R.id.spnSetting);
        
        btnGMap.setOnClickListener(OpenMap);
        btnSampling.setOnClickListener(Sampling);
        
        
	    //file name of scan log named same as present time
	    time = new Time();
        time.setToNow();
        //timeText = (TextView) findViewById(R.id.timeText);
        //timeText.setText("Time: "+time.format2445());
        
        
        // TODO: 2010.6.15 
        // 		 Update new POI locations!!!!!!       
        //HashMap to store POIs
        //Union Wireless APs: BSSID (Mac Address) and GPS (lat, long)
        
//		  SI NORTH Third floor lab (room 3250)        
//        double lat1 = 42.2890741;
//        double lng1 = -83.7144923;
//        double lat2 = 42.2889951;
//        double lng2 = -83.7144952;
//        double lat3 = 42.2890012;
//        double lng3 = -83.7143811;

//      // Michigan Lat&Long
//      double lat1 = 42.27508602790031;
//      double lng1 = -83.7416124343872;
//      double lat2 = 42.27491634181181;
//      double lng2 = -83.7412865459919;
//      double lat3 = 42.27481909460777;
//      double lng3 = -83.74136164784431;
       
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
        
        // Create WifiManager & Register the receiver
        mainWifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        receiverWifi = new WifiReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(receiverWifi, filter );
        
        // Create Compass
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(sensorListener, sensorList.get(0), 
        		SensorManager.SENSOR_DELAY_NORMAL);
        
        // Start Wifi Scanning
        mainWifi.startScan();
        mainText.setText("\nStarting Scan...\n");        
           
	     btnRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mainText.setText("Start Scanning...");
				txtMyLocation.setText("My Lat & Long Sacnning...");

				// sacnning WIFI and WPS Location
				mainWifi.startScan();					

			}    	 
	     });
	     
	     btnLog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sb = new StringBuilder();
				for(int i = 0; i < wifiList.size(); i++){
					ScanResult scan = wifiList.get(i);
					//if(poiHash.get(scan.BSSID) != null) 
					{
						sb.append(presentLoc + ", " + CompassValue + ", " 
	            		   + wifiLocation[i][0] + ", " + wifiLocation[i][1]+ ", "			               
	               		   + scan.SSID + ", " + scan.BSSID + ", " + scan.level 
	               		   + ", " + scan.frequency + "\n"); 
					}	               	               
	             } 
                try {
					logFile.append(sb);
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}	    	 
	     });	     
	     
	     
        //add spinner listener
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
        		R.array.spnFeet, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spnSetting.setAdapter(adapter);	    
	    spnSetting.setOnItemSelectedListener(new poiListListener());

    }     
    
    private View.OnClickListener OpenMap = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			//Uri _curLocationUri = Uri.parse(String.format("geo:%f, %f", Longitude, Latitude));
			//Intent _curLocation = new Intent(Intent.ACTION_VIEW, _curLocationUri);
			try {
				Intent intent = new Intent(WifiPosition.this, GoogleMap.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("Longitude", Longitude);
				intent.putExtra("Latitude", Latitude);
				startActivity(intent);
			}
			catch (ActivityNotFoundException e){
				Toast.makeText(WifiPosition.this, "Activity Not Found", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
    // Sampling Wifi beacons periodically 
    private View.OnClickListener Sampling = new View.OnClickListener() {				
		public void onClick(View v) {
			toScan = !toScan;
			Thread thread = new Thread(Scanner);
		    if (toScan) {	
				mainText.setText("Start Scanning...");
				txtMyLocation.setText("My Lat & Long Sacnning...");
				btnSampling.setText("Stop sampling");
				Toast.makeText(WifiPosition.this, "Start Sampling", Toast.LENGTH_SHORT).show();
		    	thread.start();		    	
		    }
		    else {
		    	thread.stop();
		    	btnSampling.setText("Start sampling");
		    	Toast.makeText(WifiPosition.this, "Stop Sampling", Toast.LENGTH_SHORT).show();
		    }
		}
	};
	
 	private Runnable Scanner = new Runnable() {
 		public void run() {
 			//for(int cnt=0; cnt<10; cnt++) {
 			while(true) {
				// sacnning WIFI and WPS Location
				mainWifi.startScan();
				
				try {
					Thread.sleep(SamplingIntval);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				sb = new StringBuilder();
				for(int i = 0; i < wifiList.size(); i++){
					ScanResult scan = wifiList.get(i);
					//if(poiHash.get(scan.BSSID) != null)
					{
						
						if( i== 0 ) {
							sb.append(presentLoc + ", " + CompassValue + ", ");								 	
									 
						}
						
						sb.append(presentLoc + ", " + CompassValue + ", "			// loc, compass 
	            		   + wifiLocation[i][0] + ", " + wifiLocation[i][1]+ ", " 	// AP Lat, Long	               
	               		   + scan.SSID + ", " + scan.BSSID + ", " + scan.level 		// ssid, bssid, power
	               		   + ", " + scan.frequency + ", " + SamplingIntval + ", " 	// freq, interval
	               		   + wifiLocation[i][3] + ","  								// distance
	               		   
	               		   
	               		   + myLocation[0] + ", " + myLocation[1] + "\n"); 			// my Lat, Long
					}	               	               
	             } 
                try {
					logFile.append(sb);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
 		}
 	};
       
    
	 protected void onPause() { 
         unregisterReceiver(receiverWifi); 
         sensorManager.unregisterListener(sensorListener);
         try {
			logFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
         super.onPause(); 
    } 

    protected void onResume() { 
   	 try {
		File root = Environment.getExternalStorageDirectory();
		if (root.canWrite()){
			time = new Time();
		    time.setToNow();
		    logName = "wifi_scan_"+time.format2445()+".txt";
			logFile = new BufferedWriter(new FileWriter(new File(root, logName)));	            
		}
   	 }
   	 catch (IOException e) {
   		 e.printStackTrace();
	 }
   	 
	 registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)); 
	 List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
	 sensorManager.registerListener(sensorListener, sensorList.get(0), SensorManager.SENSOR_DELAY_NORMAL);
	 super.onResume(); 

    } 
	
    class WifiReceiver extends BroadcastReceiver { 
        public void onReceive(Context c, Intent intent) { 
           sb = new StringBuilder(); 
           wifiList = mainWifi.getScanResults();
           String loc;
           String[] lat_lng;
           
           for(int i = 0; i < wifiList.size(); i++) { 
          	 
             ScanResult scan = wifiList.get(i);             
             
             // POI Hash filtering 
             // showing only registered WIFI APs
             //loc = "00:40:5a:21:61:59, 123, 456";
//             if ((loc=poiHash.get(scan.BSSID)) != null)
//             {             
//          	   int dist = (int) Utilities.calcDistance(scan.level);
//          	   Log.d("Distance", "" + dist);
//
//          	   lat_lng = loc.split(",");
//          	   wifiLocation[i][0] = Double.parseDouble(lat_lng[0]);
//          	   wifiLocation[i][1] = Double.parseDouble(lat_lng[1]);
//          	   wifiLocation[i][2] = scan.level;
//          	   wifiLocation[i][3] = dist;
//          	   
//          	   sb.append(scan.SSID+"\t"+scan.BSSID+"\t"+scan.level+"\t"+dist+"\n");
//          	   //sb.append(poiHash.get(scan.BSSID) +"\n\n");
//              }   
           } 
           
           // TODO: 2010.6.15
           // 		After setting up testbed, update the rssi to get location.
           //		Get My Location using Trilateration
//           myLatitude = Utilities.getLatitude(wifiLocation[0][0], wifiLocation[0][1], wifiLocation[0][2],
//        		   wifiLocation[1][0], wifiLocation[1][1], wifiLocation[1][2], 
//        		   wifiLocation[2][0], wifiLocation[2][1], wifiLocation[2][2]);
//           myLongitude = Utilities.getLongitude(wifiLocation[0][0], wifiLocation[0][1], wifiLocation[0][2],
//        		   wifiLocation[1][0], wifiLocation[1][1], wifiLocation[1][2], 
//        		   wifiLocation[2][0], wifiLocation[2][1], wifiLocation[2][2]);
          
           myLocation = Utilities.MyTrilateration(wifiLocation[0][0], wifiLocation[0][1], wifiLocation[0][2],
        		   wifiLocation[1][0], wifiLocation[1][1], wifiLocation[1][2], 
        		   wifiLocation[2][0], wifiLocation[2][1], wifiLocation[2][2]);
//           
           // test
//           myLocation = Utilities.MyTrilateration(42.289011, -83.714281, 1, 42.288958, -83.714375, 1, 42.288919, -83.714281, 1);

           txtMyLocation.setText("   " + myLocation[0] + ", " + myLocation[1]);
           mainText.setText(sb);                           
        } 
   } 
    
    private final SensorEventListener sensorListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if(event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
				CompassValue = event.values[0];
				txtCompass.setText("Compass: " + CompassValue);				
			}
		}    	
    };
    
    //spinner listener will set present location of the device.
    class poiListListener implements AdapterView.OnItemSelectedListener {
	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	presentLoc = parent.getItemAtPosition(pos).toString();
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    }
    }
    
//    private class MyLocationCallback implements WPSLocationCallback, 
//    									WPSPeriodicLocationCallback {
//        public void done(){
//            // tell the UI thread to re-enable the buttons
//            _handler.sendMessage(_handler.obtainMessage(DONE_MESSAGE));
//        }
//
//        public WPSContinuation handleError(WPSReturnCode error){
//            // send a message to display the error
//            _handler.sendMessage(_handler.obtainMessage(ERROR_MESSAGE, error));
////            // return WPS_STOP if the user pressed the Stop button
////            if (! _stop)
////                return WPSContinuation.WPS_CONTINUE;
////            else
//                return WPSContinuation.WPS_STOP;
//        }
//
////        public void handleIPLocation(IPLocation location){
////            // send a message to display the location
////            _handler.sendMessage(_handler.obtainMessage(LOCATION_MESSAGE, location));
////        }
//
//        public void handleWPSLocation(WPSLocation location){
//            // send a message to display the location
//            _handler.sendMessage(_handler.obtainMessage(LOCATION_MESSAGE,
//                                                        location));
//        }
//
//        public WPSContinuation handleWPSPeriodicLocation(WPSLocation location){
//            _handler.sendMessage(_handler.obtainMessage(LOCATION_MESSAGE, location));
//            // return WPS_STOP if the user pressed the Stop button
////            if (! _stop)
////                return WPSContinuation.WPS_CONTINUE;
////            else
//                return WPSContinuation.WPS_STOP;
//        }
//	}
    
    private static final int LOCATION_MESSAGE = 1;
    private static final int ERROR_MESSAGE = 2;
    private static final int DONE_MESSAGE = 3;
    private double Longitude, Latitude;
    
//    private void setUIHandler() {
//    	_handler = new Handler() {
//
//			@Override
//			public void handleMessage(Message msg) {
//				// TODO Auto-generated method stub
//				switch(msg.what) {
//				case LOCATION_MESSAGE:
//					Location location = (Location) msg.obj;			           
//					Latitude = location.getLatitude();
//					Longitude = location.getLongitude();
//					txtLocation.setText("   " + Latitude + ", " + Longitude);		 
//					return;
//				case ERROR_MESSAGE:
//					txtLocation.setText(((WPSReturnCode) msg.obj).name());
//					return;
//				case DONE_MESSAGE:
//					//deactivateStopButton();
//					//_stop = false;
//					//return;
//				}
//			}
//    		
//    	
//    	};
//    }
    
    
}