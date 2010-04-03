package talkingpoints.rdevine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class POIScanner extends Activity {

	// debug rssi
	private static final String TAG = "RSSI = ";

	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTA_DEVICE_RSSI = "device_rssi";

	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;

	private String presentLoc;
	String logName;
	SensorManager sensorManager;
	TextView compassText;
	Time time;
	TextView timeText;
	float compassReading;
	 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);
		setResult(Activity.RESULT_CANCELED);
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
	  //Compass text
	    compassText = (TextView)findViewById(R.id.comapassText);
	    compassText.setText("Compass Reading: ");
	    time = new Time();
        time.setToNow();
        logName = "bluetooth_scan_"+time.format2445()+".txt";
        timeText = (TextView) findViewById(R.id.timeText);
        timeText.setText("Time: "+time.format2445());
        
		// Initialize the button to perform device discovery
		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClear();
				doDiscovery();
				// v.setVisibility(View.);
			}
		});

		Button clearButton = (Button) findViewById(R.id.button_log);
		clearButton.setOnClickListener(new OnClickListener() {
			private StringBuilder sb;
			private Writer logFile;

			public void onClick(View v) {
				sb = new StringBuilder();
				for(int i = 0; i < mNewDevicesArrayAdapter.getCount(); i++){ 					
	               sb.append(presentLoc+","+time.format2445()+","+compassReading+","); 
	               
	               sb.append(mNewDevicesArrayAdapter.getItem(i)); 
	               sb.append("\n"); 
	               
	             } 
	             try {
	  			   	File root = Environment.getExternalStorageDirectory();
	  			    if (root.canWrite()){
	  			        logFile = new BufferedWriter(new FileWriter(new File(root, logName)));	            
	  			    }

	  			    logFile.append(sb);
	  			    logFile.close();
	  			}
	            catch (IOException e) {
	  				e.printStackTrace();
	  			}
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
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        
		sensorManager.registerListener(sensorListener, sensorList.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        
	}

	protected void onDestroy() {
		super.onDestroy();

		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}

		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
		sensorManager.unregisterListener(sensorListener);
        
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