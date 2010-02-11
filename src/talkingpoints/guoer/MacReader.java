package talkingpoints.guoer;

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
			String buffer = temp[i];
			MAC = MAC + buffer;
		}

		return MAC;

	}

}
