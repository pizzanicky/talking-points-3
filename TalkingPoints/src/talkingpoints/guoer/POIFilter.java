package talkingpoints.guoer;

public class POIFilter {

	private String FoundMac;
	private String[] POImac;

	public POIFilter(String f, String[] p) {
		FoundMac = f;
		POImac = p;

		isMatch();
	}

	protected boolean isMatch() {
		boolean match = false;
		for (int i = 0; i < POImac.length; i++) {
			if (POImac[i].equals(FoundMac))
				match = true;
		}

		return match;
	}

}
