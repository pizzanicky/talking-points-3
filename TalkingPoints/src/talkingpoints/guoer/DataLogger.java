package talkingpoints.guoer;

// log the RSSI data and store them in SDCARD
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Environment;
import android.util.Log;

public class DataLogger {
	private static final String TAG = "MAC = ";
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HHmmss";

	public DataLogger(String name, String address, int rssi) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String filename = sdf.format(cal.getTime());
		Log.w(TAG, "MAC = " + filename);
		try {
			File root = Environment.getExternalStorageDirectory();

			if (root.canWrite()) {
				File log = new File(root, name + ".txt");
				FileWriter logwriter = new FileWriter(log, true);
				BufferedWriter out = new BufferedWriter(logwriter);
				out.write(address + "'s RSSI:" + rssi + "\r\n");
				out.close();
			}
		} catch (IOException e) {
			Log.e(TAG, "Could not write file " + e.getMessage());
		}
	}

}