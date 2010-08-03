package talkingpoints.guoer;

import java.util.ArrayList;

public class AngleCalculator {

	private ArrayList<Double> NLat = new ArrayList<Double>();
	private ArrayList<Double> NLng = new ArrayList<Double>();
	private Double MLat;
	private Double MLng;
	public static ArrayList<Double> NearbyAngle;

	private final int NORTH = 0;
	private final int EAST = 1;
	private final int SOUTH = 2;
	private final int WEST = 3;

	public AngleCalculator(ArrayList<String> NearLat,
			ArrayList<String> NearLong, String mLat, String mLong) {
		// do the math

		for (int i = 0; i < NearLat.size(); i++)
			NLat.add(new Double(NearLat.get(i)));
		for (int i = 0; i < NearLong.size(); i++)
			NLng.add(new Double(NearLong.get(i)));

		NearbyAngle = new ArrayList<Double>();
		MLat = new Double(mLat);
		MLng = new Double(mLong);
	}

	public void getAngle() {
		// ArrayList<Integer> area = new ArrayList<Integer>();
		DistanceCalculator dc = new DistanceCalculator();
		for (int i = 0; i < NLat.size(); i++) {

			double x = dc.CalculationByDistance(NLat.get(i), NLng.get(i), NLat
					.get(i), MLng);// x axis long
			double y = dc.CalculationByDistance(NLat.get(i), MLng, MLat, MLng);// y
			// axis
			// long

			if (NLat.get(i) > MLat && NLng.get(i) > MLng) {// first
				// area.add(new Integer(1));
				NearbyAngle.add(new Double(180 * Math.atan(x / y) / Math.PI));

			} else if (NLat.get(i) < MLat && NLng.get(i) > MLng) {// second
				// area.add(new Integer(2));

				NearbyAngle.add(new Double(180 - 180 * Math.atan(x / y)
						/ Math.PI));

			} else if (NLat.get(i) > MLat && NLng.get(i) < MLng) {// forth
				// area.add(new Integer(4));
				NearbyAngle.add(new Double(360 - 180 * Math.atan(x / y)
						/ Math.PI));

			} else if (NLat.get(i) < MLat && NLng.get(i) < MLng) {// third
				// area.add(new Integer(3));
				NearbyAngle.add(new Double(180 * Math.atan(x / y) / Math.PI
						+ 180));
			}

		}

		// return NearbyAngle;

	}
}
