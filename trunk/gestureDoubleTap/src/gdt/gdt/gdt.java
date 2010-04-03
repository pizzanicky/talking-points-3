package gdt.gdt;

//  setContentView(R.layout.main);
  
import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;
import android.view.GestureDetector.OnDoubleTapListener;



public class gdt extends Activity implements OnGestureListener
{    
    private LinearLayout main;    
    private TextView viewA;
   
    private GestureDetector gestureScanner;
     
	private GestureLibrary mLibrary;

    
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    private float startY;
    private float currentY;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       

        
        gestureScanner = new GestureDetector(this);
       
        main = new LinearLayout(this);
        main.setBackgroundColor(Color.WHITE);
        main.setLayoutParams(new LinearLayout.LayoutParams(320,480));
       
        viewA = new TextView(this);
        viewA.setBackgroundColor(Color.WHITE);
        viewA.setTextColor(Color.BLACK);
        viewA.setTextSize(16);
        viewA.setLayoutParams(new LinearLayout.LayoutParams(320,80));
        
        main.addView(viewA);
       
        
        setContentView(main);
        
        gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener(){
            public boolean onDoubleTap(MotionEvent e) {
                 viewA.setText("-" + "onDoubleTap" + "-");
                 return false;
            }
            public boolean onDoubleTapEvent(MotionEvent e) {
                // viewA.setText("-" + "onDoubleTapEvent" + "-");
                 return false;
            }
            public boolean onSingleTapConfirmed(MotionEvent e) {
                 viewA.setText("-" + "onSingleTapConfirmed" + "-");
                 return false;
            }
      
     }); 
       
     
    }
   
    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
     this.gestureScanner.onTouchEvent(me);
     return super.onTouchEvent(me);
    }
   
    public boolean onDown(MotionEvent e)
    {
   viewA.setText("-" + "DOWN" + "-");
     return true;
    }
   

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
      viewA.setText("-" + "FLING" + "-");
    

     return true;
    }
   
 
    public void onLongPress(MotionEvent e)
    {
    viewA.setText("-" + "LONG PRESS" + "-");
    }
   
 
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        startY=e1.getY();
        currentY=e2.getY();
           
    	
       viewA.setText("SCROLL" + "::startY::"+startY+"::currentY::"+currentY);
       
    
        
     return true;
    }
   

    public void onShowPress(MotionEvent e)
    {
      viewA.setText("-" + "SHOW PRESS" + "-");
     }    
   
 
    public boolean onSingleTapUp(MotionEvent e)    
    {
        viewA.setText("-" + "onSingleTapUP" +"::currentY::"+currentY);

      return true;
    }

    
    public boolean onDoubleTap(MotionEvent e) {
      return false;
    }
 
    public boolean onDoubleTapEvent(MotionEvent e) {
        viewA.setText("-" + "onDoubleTapEvent" + "-");

       return false;
    }
 
    public boolean onSingleTapConfirmed(MotionEvent e) {
      return false;
    }

  
   
}