package talkingpoints.habha;

public class Utilities {

	/* The calcDistance equation is based on a least squares polynomial regression of empirical data
	   collected from 1st floor of the Michigan Union site. The data and the processing script used 
	   to generate this formula is available in the Talking Points dropbox Dev folder for review. 
	   
	   Although the following equation is currently being evaluated as a general model to infer 
	   distance (in feet) based on RSSI readings (in dBm), additional modeling may be required at some
	   point to accommodate differences in wireless infrastructure power settings.  
	   
	   For optimal results and positioning accuracy, we may need to generate separate equations per 
	   wireless AP should we find an especially high degree of variation in signal (power) readings 
	   beyond what we might otherwise expect from path affects, signal reflections, and other site 
	   factors which might influence signal propagation characteristics (e.g. building construction, 
	   channel saturation, etc.)
	   
	   rdevine@umich.edu 
	*/
	
	static double calcDistance(int rssi) {

		double distance = 1278.89666284 + 98.19763231 * rssi + 2.69949458* Math.pow(rssi,2) 
		  + 0.03184348*Math.pow(rssi, 3) + 0.00013895 * Math.pow(rssi,4); 
		
		return distance;
	}	
}
