package talkingpoints.guoer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MsgParser {

	URL feedUrl;

	// /feed attributes///
	private String name = "Not Available";
	private String description = "Not Available";
	private String status = "Not Available";
	private String city = "Not Available";
	private String updatedAt = "Not Available";
	private String zip = "Not Available";
	private String tpid = "Not Available";
	private String hide = "Not Available";
	private String country = "Not Available";
	private String locationType = "Not Available";
	private String phone = "Not Available";
	private String street = "Not Available";
	private String lat = "Not Available";
	private String lng = "Not Available";
	private String rfid = "Not Available";
	private String BTMac = "Not Available";
	private String WIFIMac = "Not Available";
	private String createdAt = "Not Available";
	private String state = "Not Available";
	private String url = "Not Available";

	// /......///

	// constructor
	public MsgParser(String url) {
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

				for (int j = 0; j < items.getLength(); j++) {
					Node item = items.item(j);
					String node_name = item.getNodeName();
					if (node_name.equalsIgnoreCase("name")) {
						if (item.hasChildNodes())
							this.name = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("status")) {
						if (item.hasChildNodes())
							this.status = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("description")) {
						if (item.hasChildNodes())
							this.description = item.getFirstChild()
									.getNodeValue();
					} else if (node_name.equalsIgnoreCase("city")) {
						if (item.hasChildNodes())
							this.city = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("updatedAt")) {
						if (item.hasChildNodes())
							this.updatedAt = item.getFirstChild()
									.getNodeValue();
					} else if (node_name.equalsIgnoreCase("postal-code")) {
						if (item.hasChildNodes())
							this.zip = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("tpid")) {
						if (item.hasChildNodes())
							this.tpid = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("hide")) {
						if (item.hasChildNodes())
							this.hide = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("country")) {
						if (item.hasChildNodes())
							this.country = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("state")) {
						if (item.hasChildNodes())
							this.state = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("url")) {
						if (item.hasChildNodes())
							this.url = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("created-at")) {
						if (item.hasChildNodes())
							this.createdAt = item.getFirstChild()
									.getNodeValue();
					} else if (node_name.equalsIgnoreCase("location-type")) {
						if (item.hasChildNodes())
							this.locationType = item.getFirstChild()
									.getNodeValue();
					} else if (node_name.equalsIgnoreCase("lng")) {
						if (item.hasChildNodes())
							this.lng = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("lat")) {
						if (item.hasChildNodes())
							this.lat = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("phone")) {
						if (item.hasChildNodes())
							this.phone = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("street")) {
						if (item.hasChildNodes())
							this.street = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("rfid")) {
						if (item.hasChildNodes())
							this.rfid = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("bluetooth-mac")) {
						if (item.hasChildNodes())
							this.BTMac = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("wifi-mac")) {
						if (item.hasChildNodes())
							this.WIFIMac = item.getFirstChild().getNodeValue();
					} else if (node_name.equalsIgnoreCase("sections")) {
						if (item.hasChildNodes()) {
							NodeList sections = item.getChildNodes();
							for (int k = 0; k < sections.getLength(); k++) {
								Node section = sections.item(k);
								String section_name = section.getNodeName();
								// TODO: handle information in the "sections"
								// section
								// if(section_name.equalsIgnoreCase("comments")){}
							}
						}
					} // TODO: when there's a tag says "error", read the error
					// type information
					// else if (node_name.equalsIgnoreCase("error")){}
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
	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public String getCity() {
		return city;
	}

	public String getUpdateTime() {
		return updatedAt;
	}

	public String getPostalCode() {
		return zip;
	}

	public String getTPID() {
		return tpid;
	}

	public String getIsHide() {
		return hide;
	}

	public String getCountry() {
		return country;
	}

	public String getLocationType() {
		return locationType;
	}

	public String getPhone() {
		return phone;
	}

	public String getStreet() {
		return street;
	}

	public String getLatitude() {
		return lat;
	}

	public String getlongitude() {
		return lng;
	}

	public String getRFID() {
		return rfid;
	}

	public String getBTMacAddress() {
		return BTMac;
	}

	public String getWIFIMacAddress() {
		return WIFIMac;
	}

	public String getCreatedTime() {
		return createdAt;
	}

	public String getState() {
		return state;
	}

	public String getURL() {
		return url;
	}
	// /......///

}
