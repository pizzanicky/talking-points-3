package talkingpoints.guoer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.util.Log;

public class byCoordinateParser {

	URL feedUrl;

	// /feed attributes///

	public static ArrayList<String> lat;
	public static ArrayList<String> lng;
	public static ArrayList<String> name;
	public static ArrayList<String> tpid;
	public static ArrayList<String> mac;
	public static ArrayList<Float> distance;
	public static ArrayList<Integer> floor;

	// constructor
	public byCoordinateParser(String url, Context con) {
		try {
			this.feedUrl = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		lat = new ArrayList<String>();
		lng = new ArrayList<String>();
		name = new ArrayList<String>();
		tpid = new ArrayList<String>();
		mac = new ArrayList<String>();
		distance = new ArrayList<Float>();
		floor = new ArrayList<Integer>();
		connect(con);
	}

	private void connect(Context con) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = null;

			// ConnectivityManager conMan = (ConnectivityManager) con
			// .getSystemService(Context.CONNECTIVITY_SERVICE);
			//			  
			// State mobile = conMan.getNetworkInfo(0).getState();
			//
			//			 
			// State wifi = conMan.getNetworkInfo(1).getState();
			// Log.d("wifi", wifi.toString());
			// if (wifi == NetworkInfo.State.CONNECTED
			// || mobile == NetworkInfo.State.CONNECTED) {
			try {
				doc = builder.parse(feedUrl.openConnection().getInputStream());
				Element root = doc.getDocumentElement();
				Log.w("root name", " " + root.getNodeName());
				NodeList items = root.getChildNodes();

				for (int j = 1; j < items.getLength(); j += 2) {
					Node item = items.item(j);
					String node_name = item.getNodeName();

					if (node_name.equalsIgnoreCase("record")) {
						if (item.hasChildNodes()) {
							NodeList properties = item.getChildNodes();
							for (int k = 1; k < properties.getLength(); k += 2) {
								Node property = properties.item(k);
								String property_name = property.getNodeName();
								if (property_name.equalsIgnoreCase("lat")) {
									if (property.hasChildNodes()) {
										String la = property.getFirstChild()
												.getNodeValue();
										lat.add(la);
									} else
										lat.add("");
								} else if (property_name
										.equalsIgnoreCase("lng")) {
									if (property.hasChildNodes()) {
										String ln = property.getFirstChild()
												.getNodeValue();
										lng.add(ln);
									} else
										lng.add("");
								} else if (property_name
										.equalsIgnoreCase("name")) {
									if (property.hasChildNodes()) {
										String na = property.getFirstChild()
												.getNodeValue();
										name.add(na);
									} else
										name.add("");

								} else if (property_name
										.equalsIgnoreCase("tpid")) {
									if (property.hasChildNodes()) {
										String id = property.getFirstChild()
												.getNodeValue();
										tpid.add(id);
									}
								} else if (property_name
										.equalsIgnoreCase("bluetooth-mac")) {
									if (property.hasChildNodes()) {
										String ma = property.getFirstChild()
												.getNodeValue();
										mac.add(ma);
									} else
										mac.add("");
								} else if (property_name
										.equalsIgnoreCase("distance")) {
									if (property.hasChildNodes()) {
										String dis = property.getFirstChild()
												.getNodeValue();
										distance.add(Float.parseFloat(dis));
									} else
										distance.add(Float.parseFloat("0"));
								} else if (property_name
										.equalsIgnoreCase("floor-number")) {
									if (property.hasChildNodes()) {
										String fl = property.getFirstChild()
												.getNodeValue();
										floor.add(Integer.parseInt(fl));
									} else
										floor.add(0);
								}
							}
						}
					}
				}

				// }
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// }
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// /getter methods///

	public static ArrayList<String> getLatitude() {
		return lat;
	}

	public static ArrayList<String> getlongitude() {
		return lng;
	}

	public ArrayList<String> getName() {
		return name;
	}

	public ArrayList<String> getTpid() {
		return tpid;
	}

	public static ArrayList<Float> getDistance() {
		return distance;
	}

	public ArrayList<String> getMac() {
		return mac;
	}

}
