package talkingpoints.habha;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView; 

public class WifiScanner extends Activity { 
	 TextView mainText; 
	 WifiManager mainWifi; 
	 WifiReceiver receiverWifi; 
	 List<ScanResult> wifiList; 
	 List<ScanResult> wifiListCopy;
	 
	 StringBuilder sb = new StringBuilder(); 
	 BufferedWriter logFile;
	 String logName;
	 String presentLoc;
	 SensorManager sensorManager;
	 TextView compassText;
	 Time time;
	 TextView timeText;
	 TextView scanStatusText;
	 float compassReading;
	 HashMap<String, String> poiHash;
	 boolean toScan = false;
	 
	 public void onCreate(Bundle savedInstanceState) { 
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.main); 
	    //Text showing the wifi scan results
	    mainText = (TextView) findViewById(R.id.mainText);
	    //Text showing scan status
	    scanStatusText = (TextView) findViewById(R.id.ScanStatus);
    	scanStatusText.setText("Beginning Scanning");  
    	
	    //Compass text
	    compassText = (TextView)findViewById(R.id.comapassText);
	    compassText.setText("Compass Reading: ");
	    //Wifi manager doing the scanning
	    mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE); 	    
	    receiverWifi = new WifiReceiver(); 
	    //register the reciever
	    registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)); 
	    mainWifi.startScan(); 
	    mainText.setText("\nStarting Scan...\n"); 
	    //file name of scan log named same as present time
	    time = new Time();
        time.setToNow();
        timeText = (TextView) findViewById(R.id.timeText);
        timeText.setText("Time: "+time.format2445());
        
        //HashMap to store POIs
        //Union Wireless APs: BSSID (Mac Address) and GPS (lat, long)
        //updated 6.9.2010
        poiHash = new HashMap<String, String>();  
        poiHash.put("00:0c:e6:01:21:02", "42.27508602790031,-83.7416124343872");
        poiHash.put("00:0c:e6:01:15:02", "42.27491634181181,-83.7412865459919");
        poiHash.put("00:0c:e6:01:26:02", "42.27481909460777,-83.74136164784431");
        poiHash.put("00:0c:e6:01:18:02", "42.27482703316066,-83.74156549572945");
        poiHash.put("00:0c:e6:01:17:02", "42.27495008060263,-83.74159902334213");
        poiHash.put("00:0c:e6:01:19:02", "42.27502152739418,-83.74159768223763");
        poiHash.put("00:0c:e6:01:1b:02", "42.275055266128696,-83.74165803194046");
        poiHash.put("00:0c:e6:01:1c:02", "42.27537578320581,-83.74150112271309");
        poiHash.put("00:0c:e6:01:1d:02", "42.2753747908954,-83.74145418405533");
        poiHash.put("00:0c:e6:01:1e:02", "42.27537677551623,-83.74136701226234");
        poiHash.put("00:0c:e6:01:1f:02", "42.275266628966314,-83.74120742082596");
        
        //add refresh button listener to start scan and update text view
	    Button refreshButton = (Button)this.findViewById(R.id.refreshButton);
	    refreshButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				mainWifi.startScan();
				time.setToNow();
				timeText.setText("Time: " + time.format2445());
				mainText.setText("Starting Scan");
				
				//toScan = !toScan;
			    //if (toScan) {
			    //  Add threat to enable continuous scanning	
			    //	Thread thread = new Thread(Scanner);
			    //	thread.start();
			    //	scanStatusText.setText("Scanning");
			    
			    	
			    	
			    //}
			    //else {
			    //	scanStatusText.setText("End Scan");
			    //}
			}
		});
	    
	    //log the present scanned results
	    Button logButton = (Button) this.findViewById(R.id.LogButton);
	    logButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sb = new StringBuilder();
				for(int i = 0; i < wifiList.size(); i++){ 					
	               sb.append(presentLoc+","+time.format2445()+","+compassReading+","); 
	               ScanResult scan = wifiList.get(i);
	               sb.append(scan.SSID+","+scan.BSSID+","+scan.level); 
	               sb.append("\n");
	               
	             } 
	                try {
						logFile.append(sb);
					} catch (IOException e) {
						e.printStackTrace();
					}
	  			 
			}
		});
	    
	    //Read the mapPoints file and load them onto spinner.
	    List<CharSequence> refList = new ArrayList<CharSequence>();
	    String tmpStr = new String();
	    BufferedReader mapFile = null;
	    try {
		   	File root = Environment.getExternalStorageDirectory();
		    if (root.canWrite()){
		        mapFile = new BufferedReader(new FileReader(new File(root, "mapPoints.txt")));	            
		    }
		    
		    while((tmpStr = mapFile.readLine())!= null){
		    	refList.add((CharSequence)tmpStr);
		    	
		    }
		    mapFile.close();
		    
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //add spinner listener
        Spinner pointList = (Spinner) this.findViewById(R.id.pointList);
	    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, refList);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    pointList.setAdapter(adapter);
	    presentLoc = (String)refList.get(0);
	    pointList.setOnItemSelectedListener( new poiListListener());
	    
	    sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	 } 
	 
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
             
             for(int i = 0; i < wifiList.size(); i++) { 
            	 
               ScanResult scan = wifiList.get(i);
               
           //    if (poiHash.get(scan.BSSID) != null && scan.level > -70) {
               
               if (poiHash.get(scan.BSSID) != null) {
               
            	   int dist = (int) Utilities.calcDistance(scan.level);
            	   Log.d("Distance", "" + dist);
               
            	   sb.append(scan.SSID+"\t"+scan.BSSID+"\t"+scan.level+"\t"+dist+"\n");
            	   sb.append(poiHash.get(scan.BSSID) +"\n\n");
                }   
             } 
             mainText.setText(sb);                           
          } 
     } 
     
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
     
     private final SensorEventListener sensorListener = new SensorEventListener() {		
 		@Override
 		public void onSensorChanged(SensorEvent event) {
 			if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
 				compassText.setText("Compass Reading: " + event.values[0]);
 				compassReading = event.values[0];
 			}
 			
 		}
 		
 		@Override
 		public void onAccuracyChanged(Sensor sensor, int accuracy) {
 			// TODO Auto-generated method stub
 			
 		}
 		
 	};  
     
 	private Runnable Scanner = new Runnable() {
 		public void run() {
 			while(toScan) {
 				
 				mainWifi.startScan(); 
 				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
 			}
 		}
 	};
       

}