package talkingpoints.guoer;

public class WifiCoordinator {

	static double calcDistance(int rssi) {
		double base = 10;
		double exponent = -(rssi + 51.504)/16.532;
		double distance = Math.pow(base, exponent);
		return distance;
	}	
}
