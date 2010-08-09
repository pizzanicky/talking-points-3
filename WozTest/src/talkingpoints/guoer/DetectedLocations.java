package talkingpoints.guoer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class DetectedLocations extends GestureUI {
 

 	public static String GET_NEARBY_BYLATLONG1 = "http://app.talking-points.org/locations/";
	public static String GET_NEARBY_BYLATLONG2 = "/get_nearby.xml";
	// private int tempRSSI;
	// private int currentIndex;
  

//	public static ArrayList<String> MacAddr;
 
	// Background service scanner
 
//	private static final int SWIPE_MIN_DISTANCE = 120;
//	private static final int SWIPE_MAX_OFF_PATH = 250;
//	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	
	private static final int SWIPE_MIN_DISTANCE = 10; //120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static final int CHECK_DISTANCE =100; 
 	private static final int CHECK_DISTANCE_2=10; 
	private static final int SWIPE_MIN_DISTANCE_RIGHT_LEFT=100; //more distance require for left and right gesture 

	public static boolean foundMasterTag;

	public static int index_MT = 0;
//	public static ArrayList<String> nearbyPOIs;
//	public static ArrayList<String> tpids;
	
 
	byCoordinateParser p2;
 	
  	private static boolean flag=false;
 	
 //	private static ArrayList<String> POIName;
//	private ArrayList<String> POINameWithDistance;
 
	private static boolean flag2=false; 
	private static boolean flag3=false; 
    private static boolean flagForScrolling=false; 

    private float FirstX;
    private float FirstY;
    private float LastX;
    private float LastY;
    
  	private static int count1=0;
  
	private static int countGesture=0; 
 	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
 
 		pageName = new String();
		pageName = "List of Detected Locations. Scroll to hear locations.";
		super.onCreate(savedInstanceState);		
 		// requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// setContentView(R.layout.main);
		setResult(Activity.RESULT_CANCELED);
	
		// POIOptions = new ArrayList<String>();
//		MacAddr = new ArrayList<String>();
//		nearbyPOIs = new ArrayList<String>();
//		
//		tpids = new ArrayList<String>();
// 

//		if(BTScanner.conn!=null)
//			BTScanner.conn = null;
		
//		POIName = new ArrayList<String>();
//		POINameWithDistance = new ArrayList<String>();
//		tpids = new ArrayList<String>();
//		MacAddr = new ArrayList<String>();
//		
//		POINameWithDistance = getIntent().getStringArrayListExtra("POINameWithDistance");
//		POIName = getIntent().getStringArrayListExtra("POIName");
//		tpids = getIntent().getStringArrayListExtra("tpids");
//		MacAddr = getIntent().getStringArrayListExtra("MacAddr");
		
		if(BTlist.getPOInamesWithDistance().size()!=0)
		{
			for(int i=0;i<BTlist.getPOInamesWithDistance().size();i++)
				this.options.add(BTlist.getPOInamesWithDistance().get(i));
			
		}
//		else if(BTlist.getPOInamesWithDistance().size()==0)
//		{
//			
//		}
	///	onceflag=true;
  
		
		
//		recentlyDetectedPOIs = new ArrayList<String>();
//		oldDetectedPOIs = new ArrayList<String>();
//		newlyDetectedPOIS = new ArrayList<String>();
//	//	recentlyDetectedPOIs.clear();
//	 //	Toast.makeText(this,"lac::"+bt.getLat() +"lng::"+bt.getLong(), Toast.LENGTH_SHORT).show();
//
//		newPOIs = new ArrayList<String>();
//		onlyPOInames = new ArrayList<String>();
		
		
	
// 		NumberFormat formatter = new DecimalFormat("#0");
	 
 		 //only returns POInames not including distance 
// 		
// 		this.options.add(0,"Finding locations in a chosen direction");
// 		this.tpids.add(0,"Finding locations in a chosen direction");
// 		this.MacAddr.add(0,"Finding locations in a chosen direction");
// 		this.onlyPOInames.add(0,"Finding locations in a chosen direction");
 		//if(onceflag)
		
// 		if(byCoordinateParser.getDistance().size()!=0)
// 		{
// 			if(onceflag)
// 	 		{ 
// 			
// 	 			try
// 	 			{
// 	 				super.releaseSoundEffect();
// 	 	 			super.playSound(NOTIFICATION);
// 	 				Thread.sleep(2000);
// 	 			}catch(InterruptedException e11){
// 	 				
// 					e11.printStackTrace();
// 				}
// 	 			
//// 	 			mTts.speak("Scroll to hear locations.",
//// 							TextToSpeech.QUEUE_FLUSH, null);       
// 	 			onceflag=false;
// 			}
 		
///////////////////////////////
// 			btList.callAngleCalculator();
// 		
// 			
// 			this.options.clear();
// 	 		this.tpids.clear();
// 	 		this.MacAddr.clear();
// 	 		this.onlyPOInames.clear();
// 	 		
//	 		for(int a=0;a<byCoordinateParser.getDistance().size();a++)
//	 		{	
//	// 			if(byCoordinateParser.floor.get(a)==1)
//	// 				Toast.makeText(this,"First floor!"+a,Toast.LENGTH_SHORT);
//	 	 
//	 			if((byCoordinateParser.floor.get(a)==-1)&&((byCoordinateParser.distance.get(a)*5280)<20.00))
//	 			{
//			 
//						this.options.add(byCoordinateParser.name.get(a)+" within "+formatter.format(byCoordinateParser.distance.get(a)*5280)+"feet");
//						this.MacAddr.add(byCoordinateParser.mac.get(a));
//						this.tpids.add(byCoordinateParser.tpid.get(a));
//						nearbyPOIs = this.options;
//						this.onlyPOInames.add(byCoordinateParser.name.get(a));
//	
//	 			}
//	 		
//	 		}
//	 		//notification
//	 		if(this.recentlyDetectedPOIs.size()!=0)
//	 		{
//	 			NewItemfilter = new ListComparer(this.onlyPOInames,this.recentlyDetectedPOIs);
//	 			
//	 			NofiticationList = NewItemfilter.getNewItems();
//	 			
//	 			if(NofiticationList!=null 
//	 				&& NofiticationList.size()>0){
//	 			  	if(mTts.isSpeaking())
//	 			  	{
//	 			  	//	mTts.stop();
//	 			  		mTts.shutdown();
//	 			  	}
//	 				super.releaseSoundEffect();
//		 			super.playSound(NOTIFICATION);
//		 		 
//	 		 		for(int i=0;i<NofiticationList.size();i++){
//	 						
//	 			 
//	 						
//	 						//need to slow down a little bit to call all the POI names 
//	 						try {
//	 							Thread.sleep(1000);
//	 						 	this.mTts.speak(NofiticationList.get(i),
//	 	 								TextToSpeech.QUEUE_FLUSH, null);
//	   
//	 						 	
//	 						} catch (InterruptedException e11) {
//	 							e11.printStackTrace();
//	 						}
//	 					}
//	 				 
//
//	 			}
//	 			
//	 			this.recentlyDetectedPOIs.clear();
//	 			//flag = true;		
//	 		}
//	 		
//	 		for(int i=0;i<this.onlyPOInames.size();i++)
//	 		{
//	 			this.recentlyDetectedPOIs.add(this.onlyPOInames.get(i));
//	 		}
 
 
///////////////////////////////
  
 		
 		//	this.recentlyDetectedPOIs = this.onlyPOInames;
	 
		//Enalbe them when the user click the double .. 
		
 		 super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
 			 	@Override
				public boolean onDoubleTap(MotionEvent e) {
 			 				if(options.size()!=0){
								
 			 					releaseSoundEffect();
 								playSound(NEXT_PAGE);
 								
							   Intent intent = new Intent(DetectedLocations.this, POImenu.class);
						   //Insert the parser function 
 							   MacReader r = new MacReader(BTlist.getMacAddr()
 								.get(GestureUI.selected));  
							   intent.putExtra("MAC", r.getMacString());
							   intent.putExtra("tpid",BTlist.getTpids().get(GestureUI.selected));
							   intent.putExtra("POIname", BTlist.getPOInames()
								.get(GestureUI.selected)); 
							   
							   startActivity(intent);
							   
						   	}else if(options.size()==0)
						   	{
						   	 
							   sayPageName("nothing interesting detected yet");

						   	}
 			 	
					    
 			 	 
					return true;
				}
				
				

				@Override
				public boolean onDoubleTapEvent(MotionEvent e) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean onSingleTapConfirmed(MotionEvent e) {
					// TODO Auto-generated method stub
					countGesture++;
					
					if(countGesture==2)
					{	
 						countGesture=0;
 						if(options.size()!=0){
							
 							releaseSoundEffect();
 							playSound(NEXT_PAGE);
 							
							   Intent intent = new Intent(DetectedLocations.this, POImenu.class);
						   //Insert the parser function 
							   MacReader r = new MacReader(BTlist.getMacAddr()
								.get(GestureUI.selected));  
							   intent.putExtra("MAC", r.getMacString());
							   intent.putExtra("tpid",BTlist.getTpids().get(GestureUI.selected));
							   intent.putExtra("POIname", BTlist.getPOInames()
								.get(GestureUI.selected)); 
							   
							   startActivity(intent);
							   
						   	}else if(options.size()==0)
						   	{
						   	 
							   sayPageName("nothing interesting detected yet");

						   	}
					}
					return false;
				}
			});
 
	}
	@Override
	public boolean onTouchEvent(MotionEvent e) {

		int action = e.getAction();
    	//down 
		if(action == MotionEvent.ACTION_DOWN||action==MotionEvent.ACTION_MOVE)
		{	
			flagForScrolling=true;
			if(this.options.size()==0)
					this.mTts.speak("Nothing interesting detected yet", TextToSpeech.QUEUE_FLUSH,null);

		}
        if(action == MotionEvent.ACTION_DOWN)
        {
         	FirstX=e.getX();
        	FirstY=e.getY();
        }
    	else if(action == MotionEvent.ACTION_UP)
    	{
    		LastX=e.getX();
    		LastY=e.getY();
    		
     		
    		if(FirstX>0||FirstY>0)
    		{
    			final float xD=Math.abs(FirstX-LastX);
    			final float yD=Math.abs(FirstY-LastY);
    			
    			try{
    				if(FirstX-LastX>SWIPE_MIN_DISTANCE_RIGHT_LEFT&&yD< CHECK_DISTANCE)
    				{    // this.mTts.speak("LEFT MOTION", TextToSpeech.QUEUE_FLUSH,null);
		    				releaseSoundEffect();
							playSound(NEXT_PAGE);
								finish();

    				}
    				else if(LastX - FirstX >SWIPE_MIN_DISTANCE_RIGHT_LEFT&& yD< CHECK_DISTANCE) 
    				{	
    					vibrate();
    					this.sayPageName();

    				}
     				   //   this.mTts.speak("Right motion", TextToSpeech.QUEUE_FLUSH,null);
     				else if(FirstY - LastY > SWIPE_MIN_DISTANCE&& xD< CHECK_DISTANCE)  
     				{
     					 // this.mTts.speak("UP Motion", TextToSpeech.QUEUE_FLUSH,null);
     					 if(flag||flagForScrolling)
     					 {
     						 vibrate();
     						 upMotion();
     						flagForScrolling=false;
     					 }
     					
     				}
     				else if(LastY - FirstY > SWIPE_MIN_DISTANCE && xD< CHECK_DISTANCE)  
     				{	
     					//this.mTts.speak("down motion", TextToSpeech.QUEUE_FLUSH,null);
     					
     					 if(flag||flagForScrolling)
     					 {
     						 
     						vibrate();
     						downMotion();
     						flagForScrolling = false; 
     					 }
     				}//missed
     				else if(xD>CHECK_DISTANCE_2&&yD>CHECK_DISTANCE_2&&this.options.size()>0)
     				{
     					releaseSoundEffect();
						playSound(MISSED_IT);
     					this.mTts.speak("Please move your thumb in right direction", TextToSpeech.QUEUE_FLUSH,null);
     				}
     				 

    		 

    			}
    			catch (Exception e0) {
    				// nothing
    			}
    			
    		}
    	}
		
		gestureScanner.onTouchEvent(e);
		return true;

	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
//flag = true;
		
		final float xDistance = Math.abs(e1.getX() - e2.getX());
		final float yDistance = Math.abs(e1.getY() - e2.getY());
		
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE_RIGHT_LEFT
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY&&yDistance< CHECK_DISTANCE) {
			
			releaseSoundEffect();
			playSound(NEXT_PAGE);
			
			finish();
		}else if(e2.getX() - e1.getX() >SWIPE_MIN_DISTANCE_RIGHT_LEFT
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY&&yDistance< CHECK_DISTANCE) {
			this.sayPageName();
			
		}else if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY&&xDistance< CHECK_DISTANCE) {
		//	this.sayPageName("up");
			 if(flag)
			 {
				 upMotion();
			 }
			
	    //  viewA.setText("-" + "Fling up?" + "-");

		}else if(e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY&&xDistance< CHECK_DISTANCE) {
			 if(flag)
			 {
//					POINameWithDistance = getIntent().getStringArrayListExtra("POINameWithDistance");
//					POIName = getIntent().getStringArrayListExtra("POIName");
//					tpids = getIntent().getStringArrayListExtra("tpids");
//					MacAddr = getIntent().getStringArrayListExtra("MacAddr");
//					
//					for(int i=0;i<POINameWithDistance.size();i++)
//						this.options.add(POINameWithDistance.get(i));
				
				 this.options.clear();
				 for(int i=0;i<BTlist.getPOInamesWithDistance().size();i++)
 						this.options.add(BTlist.getPOInamesWithDistance().get(i));
				 
				 downMotion();
			 }
			 
 
 
			
			}
//		flag=true;
//		// TODO Auto-generated method stub
//		try {
//			// if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//			// return false;
//			// right to left swipe
//			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
//					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//				
//			 
//				finish();
//
//			}
//			else if(e2.getX() - e1.getX() >SWIPE_MIN_DISTANCE
//					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//				this.sayPageName();
//			}
//		} catch (Exception e) {
//			// nothing
//			Toast.makeText(this,"fling error!!",Toast.LENGTH_SHORT).show();
//		}
		
	 
		
		return false;
	}
	private void upMotion()
	{
		flag2=true;
		
		 if(options.size()!=0){
				if(flag3)
				{
					if(count1==1)
						count1=options.size()-1;
	//				else if(count==5)
	//					count=3;
					else 
					{
						if(count1!=0)
						  count1-=2;
					}
					flag3=false;
				}
				
				if(count1!=0)
				{
					if(count1==options.size()){
				
					
						count1=options.size()-2;
					}	
				}
	
				
				if(count1==0){
				//	this.sayPageName("0");
					
					message = options.get(count1);
					
	
					selected = count1;
					text.setText(message);
	
					this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
						null);
				    
					
					releaseSoundEffect();
					playSound(ITEM_BY_ITEM);
				
			    //	 viewA.setText("UP"+count1);
					count1=options.size()-1;
					
				}
				else if(count1<options.size())
					{
					
					
					message = options.get(count1);
				
	
					selected = count1;
					text.setText(message);
	
					this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
						null);
				
					releaseSoundEffect();
					playSound(ITEM_BY_ITEM);
				
	
//					if(count1==(options.size()-1)) 
//					{
//						
//						if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
//						{
//							try {
//								
//								Thread.sleep(1400);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e11){
//								e11.printStackTrace();
//							}
//				 
//						}
//						else if(options.get(count1).length()>16)
//						{
//							try {
//								
//								Thread.sleep(2100);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e12){
//								e12.printStackTrace();
//							}
//				 
//						}else 
//						{
//							try {
//								
//								Thread.sleep(700);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e13){
//								e13.printStackTrace();
//							}
//						}
//
//					} 
				//	 viewA.setText("Up"+count1);
			   
					count1--;
	
				}
			} 
			
		 

		 flag = false;
	}
	private void downMotion()
	{
		flag3=true; 
		 if(options.size()!=0){
			 
				if(flag2)
				{
					if(count1==options.size()-1)
						count1=1;
					else 
						count1+=2;
					
					flag2=false; 
				}
				
				if(count1==options.size()){
					
					count1=0;
					
				}
				
				if(count1<options.size()) //count<.size() 
				{
					
					// viewA.setText("Down"+count1);
	     			message = options.get(count1);
	 			
	 
	 				selected = count1;
	  				text.setText(message);
	 
	 				this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
	 					null);
	 			
	 				releaseSoundEffect();
	 				playSound(ITEM_BY_ITEM);
	 			
	 
//					if(count1==(options.size()-1)) 
//					{
//						
//						if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
//						{
//							try {
//								
//								Thread.sleep(1400);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e21){
//								e21.printStackTrace();
//							}
//				 
//						}
//						else if(options.get(count1).length()>16)
//						{
//							try {
//								
//								Thread.sleep(2100);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e22){
//								e22.printStackTrace();
//							}
//				 
//						}else 
//						{
//							try {
//								
//								Thread.sleep(700);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e23){
//								e23.printStackTrace();
//							}
//						}
//
//					} 
//			    
					count1++;

				}
		 }  

		 flag = false;
	}
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		flag = true; 
		return true; 
	}
	@Override
	public void vibrate()
	{
		String vibratorService = Context.VIBRATOR_SERVICE;
		Vibrator vibrator = (Vibrator)getSystemService(vibratorService);


		vibrator.vibrate(100);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
 
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
		
			releaseSoundEffect();
			playSound(NEXT_PAGE);
			
			
			AngleCalculator oc = new AngleCalculator(byCoordinateParser.getLatitude(), byCoordinateParser
					.getlongitude(),BTlist.LAC1,
					BTlist.LNG1);

		   		oc.getAngle();
 		 
			
			Intent intent0 = new Intent(DetectedLocations.this,POIsAhead.class);
// 			intent0.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

  			DetectedLocations.this.startActivity(intent0);
// 			DetectedLocations.this.finish();
			
		}
		else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
		
			
				flag3=true; 
			
				
				 this.options.clear();
				 for(int i=0;i<BTlist.getPOInamesWithDistance().size();i++)
						this.options.add(BTlist.getPOInamesWithDistance().get(i));
				 
				 
			if(options.size()!=0){
				
				if(flag2)
				{
					if(count1==options.size()-1)
						count1=1;
					else 
						count1+=2;
					
					flag2=false; 
				}
				
				if(count1==options.size()){
					
					count1=0;
					
				}
				
				if(count1<options.size()) //count<.size() 
				{
					
					// viewA.setText("Down"+count1);
	     			message = options.get(count1);
	 			
	 
	 				selected = count1;
	  				text.setText(message);
	 
	 				this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
	 					null);
	 			
	 				releaseSoundEffect();
	 				playSound(ITEM_BY_ITEM);
	 			
 	 
//	 				if(count1==(options.size()-1)) 
//					{
//						
//						if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
//						{
//							try {
//								
//								Thread.sleep(1400);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e51){
//								e51.printStackTrace();
//							}
//				 
//						}
//						else if(options.get(count1).length()>16)
//						{
//							try {
//								
//								Thread.sleep(2100);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e52){
//								e52.printStackTrace();
//							}
//				 
//						}else 
//						{
//							try {
//								
//								Thread.sleep(700);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e53){
//								e53.printStackTrace();
//							}
//						}
//	
//					} 
			    
					count1++;
	
				}
			}else if(options.size()==0)
			{
				   sayPageName("nothing interesting detected yet");

			}
 
			
		}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
		
			flag2=true;
			
			if(options.size()!=0)
			{
				if(flag3)
				{
					if(count1==1)
						count1=options.size()-1;
	//				else if(count==5)
	//					count=3;
					else 
					{	
						if(count1!=0)
							count1-=2;
					}
					flag3=false;
				}
				
				if(count1!=0)
				{
					if(count1==options.size()){
				
					
						count1=options.size()-2;
					}	
				}
	
				
				if(count1==0){
				//	this.sayPageName("0");
					
					message = options.get(count1);
					
	
					selected = count1;
					text.setText(message);
	
					this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
						null);
				    
					
					releaseSoundEffect();
					playSound(ITEM_BY_ITEM);
				
			    //	 viewA.setText("UP"+count1);
					count1=options.size()-1;
					
				}
				else if(count1<options.size())
					{
					
					 
					message = options.get(count1);
				
	
					selected = count1;
					text.setText(message);
	
					this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH,
						null);
				
					releaseSoundEffect();
					playSound(ITEM_BY_ITEM);
				
	
//					if(count1==(options.size()-1)) 
//					{
//						
//						if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
//						{
//							try {
//								
//								Thread.sleep(1400);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e61){
//								e61.printStackTrace();
//							}
//				 
//						}
//						else if(options.get(count1).length()>16)
//						{
//							try {
//								
//								Thread.sleep(2100);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e62){
//								e62.printStackTrace();
//							}
//				 
//						}else 
//						{
//							try {
//								
//								Thread.sleep(700);
//								releaseSoundEffect();
//								playSound(EDGE);
//								
//							}catch(InterruptedException e63){
//								e63.printStackTrace();
//							}
//						}
//	
//					} 
				//	 viewA.setText("Up"+count1);
			   
					count1--;
	
				}
			}else if(options.size()==0)
			{
				   sayPageName("nothing interesting detected yet");

			}
			
 
		}

		return true;// return super.onKeyDown(keyCode, event);
	}

}
