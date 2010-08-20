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
	private int floor = 0;

	private ArrayList<String> section_name_value = new ArrayList<String>();
	private ArrayList<String> section_text_value = new ArrayList<String>();

	private ArrayList<String> comment_title = new ArrayList<String>();
	private ArrayList<String> comment_text = new ArrayList<String>();
	private ArrayList<String> comment_username = new ArrayList<String>();
	private ArrayList<String> comment_id = new ArrayList<String>();
	private ArrayList<String> comment_userid = new ArrayList<String>();
	private ArrayList<String> comment_datetime = new ArrayList<String>();
	private ArrayList<String> comment_datetime_in_words = new ArrayList<String>();

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
							for (int k = 1; k < sections.getLength(); k += 2) {
								Node section = sections.item(k);
								String section_name = section.getNodeName();
								// TODO: handle information in the "sections"
								// section
								if (section_name.equalsIgnoreCase("comments")) {
									if (section.hasChildNodes()) {
										NodeList comments = section
												.getChildNodes();
										for (int w = 1; w < comments
												.getLength(); w += 2) {
											Node comment = comments.item(w);
											String comments_name = comment
													.getNodeName();
											if (comment.hasChildNodes()) {
												NodeList commentChildren = comment
														.getChildNodes();
												for (int p = 1; p < commentChildren
														.getLength(); p += 2) {
													Node commentChild = commentChildren
															.item(p);
													String commentChild_name = commentChild
															.getNodeName();

													if (commentChild_name
															.equalsIgnoreCase("title")) {
														String title = commentChild
																.getFirstChild()
																.getNodeValue();
														this.comment_title
																.add(title);
													} else if (commentChild_name
															.equalsIgnoreCase("text")) {

														String text = commentChild
																.getFirstChild()
																.getNodeValue();
														this.comment_text
																.add(text);

													} else if (commentChild_name
															.equalsIgnoreCase("username")) {
														String username = commentChild
																.getFirstChild()
																.getNodeValue();
														this.comment_username
																.add(username);

													} else if (commentChild_name
															.equalsIgnoreCase("id")) {
														String id = commentChild
																.getFirstChild()
																.getNodeValue();
														this.comment_id.add(id);

													} else if (commentChild_name
															.equalsIgnoreCase("user-id")) {
														String userid = commentChild
																.getFirstChild()
																.getNodeValue();
														this.comment_userid
																.add(userid);

													} else if (commentChild_name
															.equalsIgnoreCase("datetime")) {
														String datetime = commentChild
																.getFirstChild()
																.getNodeValue();
														this.comment_datetime
																.add(datetime);

													} else if (commentChild_name
															.equalsIgnoreCase("datetime-in-words")) {
														String datetimeInWords = commentChild
																.getFirstChild()
																.getNodeValue();
														this.comment_datetime_in_words
																.add(datetimeInWords);

													}

												}

											}
										}
									}

								} else if (section.hasChildNodes()) // not
								// comment!
								// //I am
								// sections!
								{
									NodeList sectionChildren = section
											.getChildNodes();
									for (int q = 1; q < sectionChildren
											.getLength(); q += 2) {
										Node sectionChild = sectionChildren
												.item(q);
										String sectionChild_name = sectionChild
												.getNodeName();
										if (sectionChild_name
												.equalsIgnoreCase("name")) {

											String nameValue = sectionChild
													.getFirstChild()
													.getNodeValue();
											this.section_name_value
													.add(nameValue);

										} 
										else if (sectionChild_name
												.equalsIgnoreCase("text")) {
											String textValue = sectionChild
													.getFirstChild()
													.getNodeValue();
											this.section_text_value
													.add(textValue);
										}

									}

								}

							}
						}
					}
				}

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

	public ArrayList<String> getSetionNameValue() {
		return section_name_value;
	}

	public ArrayList<String> getSectionTextValue() {
		return section_text_value;
	}

	public ArrayList<String> getCommentTitle() {
		return comment_title;
	}

	public ArrayList<String> getCommentText() {
		return comment_text;
	}

	public ArrayList<String> getCommentUsername() {
		return comment_username;
	}

	public ArrayList<String> getCommentId() {
		return comment_id;
	}

	public ArrayList<String> getCommentUserid() {
		return comment_userid;
	}

	public ArrayList<String> getCommentDatetime() {
		return comment_datetime;
	}

	public ArrayList<String> getDatetimeInWords() {
		return comment_datetime_in_words;
	}

}