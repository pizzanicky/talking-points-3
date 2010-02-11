package talkingpoints.guoer;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;

public class BTScanner extends Service {
	
	private Timer timer = new Timer();
	private long refreshRate = 12000L;
	private BluetoothAdapter mBtAdapter;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		
		timer.scheduleAtFixedRate(new TimerTask(){ public void run() {doWork();}}, 0, refreshRate);
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}
	}
	private void doWork(){
    	
    }

}
