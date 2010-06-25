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

import android.widget.Toast;

public class WozParser {

	
	URL feedUrl;

	// /feed attributes///

	private static String lat="0000";
	private static String lng="0000";
 

	// constructor
	public WozParser(String url) {
		try {
			this.feedUrl = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
  
		connect();
	}

	private void connect() {
		try {
		 
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = null;

			try {
				doc = builder.parse(feedUrl.openConnection().getInputStream());
				Element root = doc.getDocumentElement();
 				NodeList items = root.getChildNodes();

				for (int j = 1; j < items.getLength(); j += 2) {
					Node item = items.item(j);
					String node_name = item.getNodeName();

					if (node_name.equalsIgnoreCase("lat")) {
 						lat = item.getFirstChild().getNodeValue();
						 
					}
					else if (node_name.equalsIgnoreCase("lng")) {
 						lng = item.getFirstChild().getNodeValue();
						 
					}
				}

				// }
			} 
			catch (ClientProtocolException e) {
			e.printStackTrace();
			}
			catch (IOException e) {
			e.printStackTrace();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// /getter methods///

	public static String getLatitude() {
		return lat;
	}

	public static String getLongitude() {
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
