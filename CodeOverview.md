# Introduction #

GestureUI.java
We have a template called **GestureUI**, which is an activity the implements the Text2Speech, Gesture and Sensor Listeners. **OnInitListener** refers to Text2Speech, **OnGestureListener** refers to Gesture interaction, and **SensorListener** implements shaking motion. **GestureUI** is our customized activity which include gesture functionality. **GateWay**, which is the Talking Points home screen, provides 3 menu options ("detected location", "building directory", and "keyword search"). Currently, "detected location" is implemented and "building directory" and "keyword search" are placeholder entries.

# Navigation Process #

When we double-tap "detected location" we invoke scanning of bluetooth devices. The BTScanner.java class is responsible for continuously scanning for BT devices and BTlist is the class, which is a controller that handles the stopping and starting of the BTScanner service. WifiScanning is **not** currently implemented in this version.

The output of "detected locations", whether there are POIs or not, use the same screen (activity) . The list is invisible, but the user can scroll down the list of POIs. Within GestureUI.java, the onScroll method handles the user traversing the list. Any screen will extend GestureUI, which enables a user to scroll through a list, whether POIs, menu items, etc. Double-tapping anywhere on screen will invoke selection of the current menu item list.

When BTlist starts, it invokes the background BT scanning service and returns the POI results. The background service will run constantly and the list update occurs every 5 seconds (Thread.sleep(5000)).

The updateList() method within BTList fetches the POIs from the mNewDeviceArrayAdapter list structure. It should be noted here that when updating the POIs, updateList() will loop through all the POIs found and display only the closest points (x>-60), otherwise treat the point as noise. Master points or "decision" points were fixed points with no lat., long. information and saved/stored in the database. When a user is close in proximity (x>-60), the server will use the location of the master tag and assume this is the location of the user.

When a decision point is selected ("double tap"), a page (list) of intersection points is presented to the user, which also trigger the flashlight feature handled by the POIsahead class. Down the road, we may want to change this class to Flashlight. For example, if the user if facing north, it will tell you there are 3 locations or POIs available. Pressing the trackball will report the POIs ahead of you.

The POIsAhead class will call AngleCalculator class to get all the angle information for all nearby POIs when the user clicks on the trackball. The compass class return your current angle between cardinal north and your current direction. All of the POIs have an angle associated with them. The AngleCalculator class is responsible for determining the delta (change) between your current position and the angle associated with the POI. The default flashing angle threshold is currently 60 degrees and is stored in private final double range, which is the beam angel divided 2 (60/2 -> 30).

# POI #

Once you select a specific POI it will initiate an API call to the server, using either the TP ID or Mac Address. Accordingly, the server will return relevant information associated with this POI. The server call is handled in the POImenu class. The onCreate (class Constructor) will either use ID or Mac address and parse the xml returned by the server using the MsgParser method and save it into a MsgParser object (i.e. p), which makes respective getter methods available and other DOM (document object model) functionality. We use the information available in the MsgParser object (i.e. p) to dynamically construct the menu options, except for Type, Description and "What around" schema.

Once you double click on an option within a POI object, it will display the children objects associated with it (either content or another list, which should start another content activity).

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages