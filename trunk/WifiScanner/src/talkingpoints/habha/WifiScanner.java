package talkingpoints.habha;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	 StringBuilder sb = new StringBuilder(); 
	 BufferedWriter logFile;
	 String logName;
	 String presentLoc;
	 SensorManager sensorManager;
	 TextView compassText;
	 Time time;
	 TextView timeText;
	 float compassReading;
	 public void onCreate(Bundle savedInstanceState) { 
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.main); 
	    //Text showing the wifi scan results
	    mainText = (TextView) findViewById(R.id.mainText);
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
        
        //add refresh button listener to start scan and update text view
	    Button refreshButton = (Button)this.findViewById(R.id.refreshButton);
	    refreshButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				mainWifi.startScan(); 
				time.setToNow();
				timeText.setText("Time: "+time.format2445());
		        mainText.setText("Starting Scan");
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
             for(int i = 0; i < wifiList.size(); i++){ 
               sb.append(new Integer(i+1).toString() + " -- "); 
               ScanResult scan = wifiList.get(i);
               sb.append(scan.SSID+"\t"+scan.BSSID+"\t"+scan.level); 
               sb.append("\n"); 
               
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
     
        

}