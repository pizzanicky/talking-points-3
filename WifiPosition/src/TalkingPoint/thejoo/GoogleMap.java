package TalkingPoint.thejoo;

//import android.app.Activity;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView.LayoutParams;



public class GoogleMap extends MapActivity  {

	private double Longitude = 0;
	private double Latitude = 0;

	MapView mapView;
    MapController mc;
	GeoPoint p;
	
    class MapOverlay extends com.google.android.maps.Overlay
    {
    	@Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts);
 
            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.pushpin);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);         
            return true;
        }
    } 


	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mapview);
	    
	    Longitude 	= getIntent().getDoubleExtra("Longitude", 0);
	    Latitude 	= getIntent().getDoubleExtra("Latitude", 0);	 
	    
		mapView = (MapView)findViewById(R.id.mapview);
		
		LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);		// show zoom-in/out layer
        

         
        mc = mapView.getController();
		p = new GeoPoint((int)(Latitude*1E6), (int)(Longitude*1E6));
		 
		mc.animateTo(p);
		
	    mc.setZoom(30); 
	    
	    //---Add a location marker---
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);           
        
	    mapView.invalidate();
		
		//mapView.setClickable(true);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
