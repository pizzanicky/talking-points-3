package talkingpoints.guoer;

import android.util.Log;

public class MacReader {

	private String input;

	public MacReader(String in) {

		input = in;
		getMacString();

	}

	String getMacString() {
		String[] temp = input.split(":");
		String MAC = "";
		for (int i = 0; i < temp.length; i++) {
			String buffer = temp[i].toLowerCase() + ":";
			MAC = MAC + buffer;
		}
		Log.w("mac address", MAC.substring(0, MAC.length() - 1));
		return MAC.substring(0, MAC.length() - 1);

	}

}
