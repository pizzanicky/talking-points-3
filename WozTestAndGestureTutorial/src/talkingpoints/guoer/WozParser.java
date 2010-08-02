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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;
import android.widget.Toast;

public class WozParser {

	
	URL feedUrl;

	// /feed attributes///

	private static String lat="0000";
	private static String lng="0000";
 

	// constructor
	public WozParser(String url, Context con) {
		try {
			this.feedUrl = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
  
		connect(con);
	}

	private void connect(Context con) {
		try {
		 
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = null;

//			ConnectivityManager conMan = (ConnectivityManager)con.getSystemService(Context.CONNECTIVITY_SERVICE);
//			// WifiManager conMan=(WifiManager)
//			// Context.getSystemService(Context.WIFI_SERVICE);
//			// mobile
//			State mobile = conMan.getNetworkInfo(0).getState();
//		
//			// wifi
//			State wifi = conMan.getNetworkInfo(1).getState();
//			Log.d("wifi", wifi.toString());
//			if (wifi == NetworkInfo.State.CONNECTED
//					|| mobile == NetworkInfo.State.CONNECTED) {
//			
			
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
//		}
		}
			catch (Exception e) {
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
