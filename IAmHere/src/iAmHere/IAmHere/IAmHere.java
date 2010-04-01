
package iAmHere.IAmHere;

 /*
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
*/
//import java.net.URISyntaxException;

import java.util.ArrayList;

//import talkingpoints.guoer.MsgParser;
import android.app.Activity;
import android.content.Context;
//import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;

public class IAmHere extends Activity 
{
    private LocationManager lm;
    private LocationListener locationListener;
    
    
    
 //   private MapView mapView;
 //   private MapController mc;

    private LinearLayout main;    
    private TextView viewA;
    private TextView viewB;
    private TextView viewC; 
    
 	private static String GET_TPID_BYLL = "http://app.talking-points.org/locations/by_coordinates/";
	private static String GET_INFO_BYMAC = "http://app.talking-points.org/locations/show_by_bluetooth_mac/";
 
 	
 	private String lac;
	private String lng;
	private MsgParser p;
	private MsgParser p1;
    private String TPID; 
	//private int MAC=00066602CD8E;

    // private double latit= 42,275;
   // private double logit=;-83,735;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         main = new LinearLayout(this);
        main.setBackgroundColor(Color.WHITE);
        main.setLayoutParams(new LinearLayout.LayoutParams(320,480));
       
        viewA = new TextView(this);
        viewA.setBackgroundColor(Color.WHITE);
        viewA.setTextColor(Color.BLACK);
        viewA.setTextSize(16);
        viewA.setLayoutParams(new LinearLayout.LayoutParams(320,80));
        
        viewB = new TextView(this);
        viewB.setBackgroundColor(Color.WHITE);
        viewB.setTextColor(Color.BLACK);
        viewB.setTextSize(16);
        viewB.setLayoutParams(new LinearLayout.LayoutParams(320,160));

        viewC = new TextView(this);
        viewC.setBackgroundColor(Color.WHITE);
        viewC.setTextColor(Color.BLACK);
        viewC.setTextSize(16);
        viewC.setLayoutParams(new LinearLayout.LayoutParams(320,240));
        
        main.addView(viewA);
        main.addView(viewB);
        main.addView(viewC);
       
        
        
        setContentView(main); 
        //setContentView(R.layout.main);
    
        //---use the LocationManager class to obtain GPS locations---
        lm = (LocationManager) 
            getSystemService(Context.LOCATION_SERVICE);    
        
        locationListener = new MyLocationListener();
        
        lm.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 
            0, 
            0, 
            locationListener);      
        
 /*       mapView = (MapView) findViewById(R.id.mapview1);
        mc = mapView.getController();

*/      
  
   
    }
    
    private class MyLocationListener implements LocationListener 
    {
    	
    //	Intent intent = new Intent(null, GetNearbyMe.class);
 
    	//Intent intent = new Intent();

        @Override
        public void onLocationChanged(Location loc) {
            if (loc != null) {
                Toast.makeText(getBaseContext(), 
                    "Location changed : Lat: " + loc.getLatitude() + 
                    " Lng: " + loc.getLongitude(), 
                    Toast.LENGTH_SHORT).show();
                
          /*      GeoPoint p = new GeoPoint(
                        (int) (loc.getLatitude() * 1E6), 
                        (int) (loc.getLongitude() * 1E6));
                mc.animateTo(p);
                mc.setZoom(16);                
                mapView.invalidate();  */
                
        		 
        	//	intent.putExtra("latitude",loc.getLatitude());
        //		intent.putExtra("longitude",loc.getLongitude());
        	//	startActivity(intent);
        		
        		
        		
        /*	  	try {
        			lac = Intent.getIntent(lac).getStringExtra("lactitude");
        		} catch (URISyntaxException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		try {
        			lng = Intent.getIntent(lng).getStringExtra("longitude");
        		} catch (URISyntaxException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		//longitude = getIntent().getStringExtra("longitude");
        		*/
                
         //       http://app.talking-points.org/locations/by_coordinates/42,275;-83,735.xml

        		//p = new MsgParser(GET_TPID_BYLL +loc.getLatitude()+";"+loc.getLongitude()+".xml");
                

             //   p = new MsgParser(GET_TPID_BYLL +42+","+275+";"+-83+","+735+".xml");
                p = new MsgParser("http://app.talking-points.org/locations/by_coordinates/42,275309;-83,736012.xml");

        		// String[] mStrings = { "Name", "Description" };

        	//	setListAdapter(new ArrayAdapter<String>(this,
        	//			android.R.layout.simple_list_item_1, mStrings));

        	//	getListView().setTextFilterEnabled(true);
        	  //  TPID = p.getTPID();
                TPID = p.getCity();
        	    lac = p.getLatitude();
        	    lng = p.getlongitude();
                viewA.setText("THIS IS TPID:"+TPID);
                viewB.setText("THis is Lactitude:"+lac);
                viewC.setText("This is longitude:"+lng);
               // p1 = new MsgParser(GET_TPID_BYLL +loc.getLatitude()+";"+loc.getLongitude()+".xml");
        		
        	// 	setResult(Activity.RESULT_OK); ????

            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
          /*  if (L != null) {
              Toast.makeText(getBaseContext(), 
                    "Current Location: Lat: " + L.getLatitude() + 
                    " Lng: " + L.getLongitude(), 
                    Toast.LENGTH_SHORT).show(); 
            	 viewA.setText( "Current Location: Lat: " + L.getLatitude() + 
                         " Lng: " + L.getLongitude());
            }
            else 
            {
                viewA.setText( "NUll Current Location: Lat: " + L.getLatitude() + 
                        " Lng: " + L.getLongitude());

            	
            }*/
        }

        @Override
        public void onStatusChanged(String provider, int status, 
            Bundle extras) {
            // TODO Auto-generated method stub
        }
    }        
    
}

