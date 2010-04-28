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

import android.util.Log;

public class NearbyParser {

	URL feedUrl;

	// /feed attributes///

	private ArrayList<String> lat;
	private ArrayList<String> lng;
	public static ArrayList<String> name;
	public static ArrayList<String> mac;
	public static ArrayList<String> id;

	// constructor
	public NearbyParser(String url) {
		try {
			this.feedUrl = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		lat = new ArrayList<String>();
		lng = new ArrayList<String>();
		name = new ArrayList<String>();
		mac = new ArrayList<String>();
		id = new ArrayList<String>();
		connect();
	}

	private void connect() {
		try {
			name.clear();
			mac.clear();
			id.clear();
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = null;

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
								boolean hasLatAndLng = true;
								Node property = properties.item(k);
								String property_name = property.getNodeName();
								if (property_name.equalsIgnoreCase("lat")) {
									if (property.hasChildNodes()
											&& hasLatAndLng) {
										String la = property.getFirstChild()
												.getNodeValue();
										this.lat.add(la);
									} else
										hasLatAndLng = false;
								} else if (property_name
										.equalsIgnoreCase("lng")) {
									if (property.hasChildNodes()
											&& hasLatAndLng) {
										String ln = property.getFirstChild()
												.getNodeValue();
										this.lng.add(ln);
									} else
										hasLatAndLng = false;
								} else if (property_name
										.equalsIgnoreCase("bluetooth-mac")) {
									if (property.hasChildNodes()
											&& hasLatAndLng) {
										String ln = property.getFirstChild()
												.getNodeValue();
										mac.add(ln);
									} else
										mac.add("");
								} else if (property_name
										.equalsIgnoreCase("name")) {
									if (property.hasChildNodes()
											&& hasLatAndLng) {
										String ln = property.getFirstChild()
												.getNodeValue();
										name.add(ln);
									} else
										name.add("");
								} else if (property_name
										.equalsIgnoreCase("tpid")) {
									if (property.hasChildNodes()
											&& hasLatAndLng) {
										String ln = property.getFirstChild()
												.getNodeValue();
										id.add(ln);
									}
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// /getter methods///

	public ArrayList<String> getLatitude() {
		return lat;
	}

	public ArrayList<String> getlongitude() {
		return lng;
	}

	// public ArrayList<String> getName() {
	// return name;
	// }
	//
	// public ArrayList<String> getMac() {
	// return mac;
	// }
}
