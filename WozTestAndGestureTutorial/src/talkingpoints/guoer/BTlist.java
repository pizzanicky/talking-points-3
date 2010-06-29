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
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class BTlist extends GestureUI {
	// fake POI
	// String[] exsitingPOI = { "sdf", "002608D712B9", "1234567890" };
	public static BTlist scanning_home = null;
	private Timer m_timerForSensorUpdate = null;
	// debug rssi
	private static final String TAG = "MAC = ";
	public static final int UPDATEIDENTIFIER = 0;
	public static final int CREATPANEL = 1;
	private RemoteService remoteService;
	// private boolean started = false;

	private List<String> cachList = null;
	public static String GET_NEARBY_BYLATLONG1 = "http://app.talking-points.org/locations/";
	public static String GET_NEARBY_BYLATLONG2 = "/get_nearby.xml";
	// private int tempRSSI;
	// private int currentIndex;
	ListComparer NewItemfilter;
	private List<String> NofiticationList;
	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTA_DEVICE_RSSI = "device_rssi";
	public static ArrayList<String[]> MasterTags;
	// private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	public static ArrayAdapter<String> mNewDevicesArrayAdapter;
	// private ArrayList<String> POIPOINameWithDistance;
	public static ArrayList<String> MacAddr;
	public int m_periodUpdate = 3000;
	// Background service scanner
	private BTScanner btScanner;
	public static boolean isRunning;
	public Thread t;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	public static boolean foundMasterTag;

	public static int index_MT = 0;
	public static ArrayList<String> nearbyPOIs;
	public static ArrayList<String> tpids;
	
	public static String LAC1="haha"; //changed from private to public static 
	public static String LNG1="haha"; // ''
	byCoordinateParser p2;
	private int countPOIs=0;
	
	private static final int NOTIFICATION=0;
	private ArrayList<String> recentlyDetectedPOIs;
	private ArrayList<String> oldDetectedPOIs;
	private static boolean flag=false;
	private ArrayList<String> newlyDetectedPOIS;
	
	private boolean onceflag=false;
	private ArrayList<String> newPOIs;
	public static ArrayList<String> onlyPOInames;
	public static ArrayList<String> POINameWithDistance;
	private ArrayList<String> MenuOptions;
	private String message1;
	WozParser p; 
	byCoordinateParser p1;
	private static String message; 
	
	private static String GET_COORDINATE = "http://talkingpoints.dreamhosters.com/maps_test/point.xml";
	private static String BY_COORDINATE = "http://app.talking-points.org/locations/by_coordinates/";

	
	   private static int count1=0;
	   private static int countGesture=0; 
	     private static boolean flag2=false;
	    private static boolean flag3=false;
	    private static boolean flag0=false; 
	/** Called when the activity is first created. */
//	public void onResume()
//	{
//		if(BTScanner.conn!=null)
//			BTScanner.conn = null;
//		
//		startService();
//		bindService();
//	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		BTScanner bt = new BTScanner();
		
		
		MenuOptions = new ArrayList<String>();
		MenuOptions.add("Detect locations within 20 feet"); //Bert's calf ay
		MenuOptions.add("Quick Tutorial");
		MenuOptions.add("Flashlight");
//		MenuPOINameWithDistance.add("Keyword Search");
//		MenuPOINameWithDistance.add("Flashlight");
		pageName = new String("Talking Points Home. Swipe down to hear menu options. Double tap to select.");
		
		BTlist.foundMasterTag = false;
 		 
		
		super.onCreate(savedInstanceState, MenuOptions);		

		//super.onCreate(savedInstanceState);
 		
		setResult(Activity.RESULT_CANCELED);
		// POIPOINameWithDistance = new ArrayList<String>();
		MacAddr = new ArrayList<String>();
		nearbyPOIs = new ArrayList<String>();
		
		tpids = new ArrayList<String>();
		
		foundMasterTag = false;
		MasterTags = new ArrayList<String[]>();
		String[] mt1 = new String[] { "00:1e:8d:19:83:24",
				"42.274863748954786", "-83.74122619628906" };
		MasterTags.add(mt1);

		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);

		cachList = new ArrayList<String>();

		if(BTScanner.conn!=null)
			BTScanner.conn = null;
		
		startService();
		bindService();
		
	
		
		onceflag=true;
		t = new Thread(new myThread());
		t.start();
		isRunning = true;
        
		
		scanning_home = this;
		
		POINameWithDistance = new ArrayList<String>();
		recentlyDetectedPOIs = new ArrayList<String>();
		oldDetectedPOIs = new ArrayList<String>();
		newlyDetectedPOIS = new ArrayList<String>();
	//	recentlyDetectedPOIs.clear();
	 //	Toast.makeText(this,"lac::"+bt.getLat() +"lng::"+bt.getLong(), Toast.LENGTH_SHORT).show();

		newPOIs = new ArrayList<String>();
		onlyPOInames = new ArrayList<String>();
		

		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
				if(flag0)
				{
					switch (GestureUI.selected) {
					case 0:
						Intent intent = new Intent(BTlist.this, DetectedLocations.class);
						 
	//					intent.putStringArrayListExtra("POINameWithDistance",POINameWithDistance);
	//					intent.putStringArrayListExtra("POIName",onlyPOInames);
	//					intent.putStringArrayListExtra("tpids", tpids);
	//					intent.putStringArrayListExtra("MacAddr", MacAddr);
						
						startActivity(intent);
						break;
					case 1:
						Intent intent1 = new Intent(BTlist.this, Tutorial.class);
						startActivity(intent1);
						break;
					case 2: //flashlight 
						{
	
						   		AngleCalculator oc = new AngleCalculator(byCoordinateParser.getLatitude(), byCoordinateParser
									.getlongitude(),LAC1,
									LNG1);
	
						   		oc.getAngle();
	
						   		Intent intent2 = new Intent(BTlist.this, POIsAhead.class);
						   		startActivity(intent2); 
		 
	
				 
						   } 
						break;
					}
				}
				return true;
			}

			public boolean onDoubleTapEvent(MotionEvent e) {
				return false;
			}

			public boolean onSingleTapConfirmed(MotionEvent e) {
		
				 
				countGesture++;
				
				if(countGesture==2)
				{	
//					Toast.makeText(getApplicationContext(),"countG: "+countGesture,Toast.LENGTH_SHORT).show();
					countGesture=0;
					switch (GestureUI.selected) {
					case 0:
						Intent intent = new Intent(BTlist.this, DetectedLocations.class);
						 
	//					intent.putStringArrayListExtra("POINameWithDistance",POINameWithDistance);
	//					intent.putStringArrayListExtra("POIName",onlyPOInames);
	//					intent.putStringArrayListExtra("tpids", tpids);
	//					intent.putStringArrayListExtra("MacAddr", MacAddr);
						
						startActivity(intent);
						break;
					case 1:
						Intent intent1 = new Intent(BTlist.this, Tutorial.class);
						startActivity(intent1);
						break;
					case 2: //flashlight 
						{
	
						   		AngleCalculator oc = new AngleCalculator(byCoordinateParser.getLatitude(), byCoordinateParser
									.getlongitude(),LAC1,
									LNG1);
	
						   		oc.getAngle();
	
						   		Intent intent2 = new Intent(BTlist.this, POIsAhead.class);
						   		startActivity(intent2); 
		 
	
				 
						   } 
						break;
					}
				}
				return false;
			}

		});

	}

//	protected void onResume(){
//		
//		flag0=false; 
//		
//		if(BTScanner.conn!=null)
//			BTScanner.conn = null;
//		
//		startService();
//		bindService();
//	}
	protected void onDestroy() {
		super.onDestroy();
		releaseService();
		stopService();
		isRunning = false;
		t.stop();
		// t.destroy();
	}
/*
	private void doClear() {

		mNewDevicesArrayAdapter.clear();

	}
	*/
//http://saigeethamn.blogspot.com/2009/09/android-developer-tutorial-part-9.html
	class RemoteServiceConnection implements ServiceConnection {
		public void onServiceConnected(ComponentName className,
				IBinder boundService) {
			remoteService = RemoteService.Stub
					.asInterface((IBinder) boundService);
			Log.d(getClass().getSimpleName(), "onServiceConnected()");
		}

		public void onServiceDisconnected(ComponentName className) {
			remoteService = null;
			// updateServiceStatus();
			Log.d(getClass().getSimpleName(), "onServiceDisconnected");
		}
		
	
	};


	private void startService() {
		if (BTScanner.started) {
			Toast.makeText(BTlist.this, "Service already started",
					Toast.LENGTH_SHORT).show();
		} else {
			Intent i = new Intent();
			i.setClassName("talkingpoints.guoer",
					"talkingpoints.guoer.BTScanner");
			startService(i);
			BTScanner.started = true;
			// updateServiceStatus();
			Log.d(getClass().getSimpleName(), "startService()");
		}


	}

	private void stopService() {
		t.stop();
		try {
			if (!BTScanner.started) {
				Toast.makeText(BTlist.this, "Service not yet started",
						Toast.LENGTH_SHORT).show();

			} else {
				Intent i = new Intent();
				i.setClassName("talkingpoints.guoer",
						"talkingpoints.guoer.BTScanner");
				stopService(i);
				BTScanner.started = false;
				// updateServiceStatus();
				Log.d(getClass().getSimpleName(), "stopService()");
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

	private void bindService() {
		if (BTScanner.conn == null) {
			BTScanner.conn = new RemoteServiceConnection();
			Log.d(getClass().getSimpleName(), "conn = " + BTScanner.conn);
			Intent i = new Intent(BTlist.this, BTScanner.class);
			// for some reason bindService doesn't work with child of
			// TabActivity, so we use getApplicationContext().bindService here
			getApplicationContext().bindService(i, BTScanner.conn,
					Context.BIND_AUTO_CREATE);
			// updateServiceStatus();
			Log.d(getClass().getSimpleName(), "bindService()");
		} else {
			Toast.makeText(BTlist.this, "Cannot bind - service already bound",
					Toast.LENGTH_SHORT).show();
			
		}	
	}

	private void releaseService() {
		try {
			if (BTScanner.conn != null) {
				getApplicationContext().unbindService(BTScanner.conn);
				BTScanner.conn = null;
				// updateServiceStatus();
				Log.d(getClass().getSimpleName(), "releaseService()");
			} else {
				// bindService();
				getApplicationContext().unbindService(BTScanner.conn);
				BTScanner.conn = null;
				Toast
						.makeText(BTlist.this,
								"Cannot unbind - service not bound",
								Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
		}
	}

	public void invokeService() {
		if (BTScanner.conn == null) {
			Toast.makeText(BTlist.this, "Cannot refresh - service not bound",
					Toast.LENGTH_SHORT).show();

		} else {
			try {

 	          

				   LAC1 = remoteService.getLac();
		           LNG1 = remoteService.getLng();
		            
		  
		            if(LAC1.length()>4)
		            {    
		            	
		            	
		            	Toast.makeText(BTlist.this,"BTList",Toast.LENGTH_SHORT).show();
		            	callAngleCalculator();
		            }
		            
			} catch (RemoteException re) {
				Log.e(getClass().getSimpleName(), "RemoteException");
			} catch (Exception e) {
				Log
						.e(getClass().getSimpleName(), "??Exception:"
								+ e.toString());
			}
		}
	}

	public void callAngleCalculator(){
 
		String lac2[]= LAC1.split("\\.");
		String lng2[]= LNG1.split("\\.");
	 

	 p2 = new byCoordinateParser("http://app.talking-points.org/locations/by_coordinates/"+lac2[0]+","+lac2[1]+";"+lng2[0]+","+lng2[1]+".xml");
		        
 
 
	 try {
		 callAngleCalculator2();
	 	} catch (InterruptedException e13) {
	// TODO Auto-generated catch block
	 		e13.printStackTrace();
	 	}
		
	}
	public void callAngleCalculator2() throws InterruptedException{
		
//		if(onceflag)
//		{ 
//		
// 			try
// 			{
// 				super.releaseSoundEffect();
// 	 			super.playSound(NOTIFICATION);
// 				Thread.sleep(2000);
// 			}catch(InterruptedException e11){
// 				
//				e11.printStackTrace();
//			}
// 			
// 			this.mTts.speak("Scroll to hear locations.",
//						TextToSpeech.QUEUE_FLUSH, null);       
// 			onceflag=false;
//		}
//		
 		NumberFormat formatter = new DecimalFormat("#0");
	 
 		this.POINameWithDistance.clear();
 		this.tpids.clear();
 		this.MacAddr.clear();
 		this.onlyPOInames.clear(); //only returns POInames not including distance 
 		
// 		this.POINameWithDistance.add(0,"Finding locations in a chosen direction");
// 		this.tpids.add(0,"Finding locations in a chosen direction");
// 		this.MacAddr.add(0,"Finding locations in a chosen direction");
// 		this.onlyPOInames.add(0,"Finding locations in a chosen direction");
 		                                                                 
 		for(int a=0;a<byCoordinateParser.getDistance().size();a++)
 		{	
// 			if(byCoordinateParser.floor.get(a)==1)
// 				Toast.makeText(this,"First floor!"+a,Toast.LENGTH_SHORT);
 	 
 			if((byCoordinateParser.floor.get(a)==-1)&&((byCoordinateParser.distance.get(a)*5280)<20.00))
 			{
		 
					this.POINameWithDistance.add(byCoordinateParser.name.get(a)+" within "+formatter.format(byCoordinateParser.distance.get(a)*5280)+"feet");
					this.MacAddr.add(byCoordinateParser.mac.get(a));
					this.tpids.add(byCoordinateParser.tpid.get(a));
					nearbyPOIs = this.POINameWithDistance;
					this.onlyPOInames.add(byCoordinateParser.name.get(a));

 			}
 		
 		}
 		
 	
   
// 		this.POINameWithDistance.add("location search using the compass");
// 		this.tpids.add("location search using the compass");
// 		this.MacAddr.add("location search using the compass");
// 		this.onlyPOInames.add("location search using the compass");
 		
 		if(this.recentlyDetectedPOIs.size()!=0)
 		{
 			NewItemfilter = new ListComparer(this.onlyPOInames,this.recentlyDetectedPOIs);
 			
 			NofiticationList = NewItemfilter.getNewItems();
 			
 			if(NofiticationList!=null 
 				&& NofiticationList.size()>0){
 			  	if(mTts.isSpeaking())
 			  	{
 			  	//	mTts.stop();
 			  		mTts.shutdown();
 			  	}
 				super.releaseSoundEffect();
	 			super.playSound(NOTIFICATION);
	 		 
 		 		for(int i=0;i<NofiticationList.size();i++){
 						
 			 
 						
 						//need to slow down a little bit to call all the POI names 
 						try {
 							Thread.sleep(1000);
 						 	this.mTts.speak(NofiticationList.get(i),
 	 								TextToSpeech.QUEUE_FLUSH, null);
   
 						 	
 						} catch (InterruptedException e11) {
 							e11.printStackTrace();
 						}
 					}
 				 

 			}
 			
 			this.recentlyDetectedPOIs.clear();
 			//flag = true;		
 		}
 		
 		for(int i=0;i<this.onlyPOInames.size();i++)
 		{
 			this.recentlyDetectedPOIs.add(this.onlyPOInames.get(i));
 		}
 	  
 		//	this.recentlyDetectedPOIs = this.onlyPOInames;
	 
		//Enalbe them when the user click the double .. 
//		
// 		 super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
// 			 	@Override
//				public boolean onDoubleTap(MotionEvent e) {
//				
//				 
// 			 	 	if(POINameWithDistance.size()!=0){
//					//compass function 
//					   if (GestureUI.selected == 0) {
//
//					   		AngleCalculator oc = new AngleCalculator(p2.getLatitude(), p2
//								.getlongitude(),LAC1,
//								LNG1);
//
//					   		oc.getAngle();
//
//					   		Intent intent = new Intent(BTlist.this, POIsAhead.class);
//					   		startActivity(intent); 
//	 
//
//			 
//					   } 
//					   else { 
////						   if(POINameWithDistance.size()==1)
////						   {
////							   mTts
////								.speak(
////										"Nothing interesting detected yet",
////										TextToSpeech.QUEUE_FLUSH, null);
////						   }
////						   else if(POINameWithDistance.size()>1){
// 						   	if(POINameWithDistance.size()>1){
//								
//							   Intent intent = new Intent(BTlist.this, POImenu.class);
//						   //Insert the parser function 
//							   MacReader r = new MacReader(BTlist.MacAddr
//								.get(GestureUI.selected)); 
//							   intent.putExtra("MAC", r.getMacString());
//							   intent.putExtra("tpid", BTlist.tpids.get(GestureUI.selected));
//							   intent.putExtra("POIname", onlyPOInames
//								.get(GestureUI.selected)); 
//				 
//							   startActivity(intent);
//							   
//						   	}else 
//						   	{
//						   		sayPageName("There are no POIs around you");
//						   	}
////						   }
//					    }
// 			 	 	}
//				 
//					return true;
//				}
//				
//				
//
//				@Override
//				public boolean onDoubleTapEvent(MotionEvent e) {
//					// TODO Auto-generated method stub
//					return false;
//				}
//
//				@Override
//				public boolean onSingleTapConfirmed(MotionEvent e) {
//					// TODO Auto-generated method stub
//					return false;
//				}
//			});
 /*	 	if (this.POINameWithDistance.size() == 0)
			this.mTts
					.speak(
							"Nothing interesting detected yet",
							TextToSpeech.QUEUE_FLUSH, null);  */
 		 
	/*	else if (this.POINameWithDistance.size() == 1)
			this.mTts.speak("There is" + this.POINameWithDistance.size()
					+ "location ahead of you, scroll to check it out",
					TextToSpeech.QUEUE_FLUSH, null);
		else
			this.mTts.speak("There are" + this.POINameWithDistance.size()
					+ "locations ahead of you, scroll to check them out",
					TextToSpeech.QUEUE_FLUSH, null); */
	}
	
	public boolean onCreatePOINameWithDistanceMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}


	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BTlist.UPDATEIDENTIFIER:
				invokeService();
				break;
			case BTlist.CREATPANEL:
				if (POINameWithDistance.size() == 0)
					// updateList();
					break; 
				else if(POINameWithDistance.size()!=0)
				{	
					flag0=false;
					break;
				}
			}
			// super.handleMessage(msg);
		}
	};

	public void updateList()
	{
		
	}

	

	class myThread implements Runnable {

		public void run() {
			while (!Thread.currentThread().isInterrupted() && BTlist.isRunning) {
				Message message = new Message();
				Message msg = new Message();
				message.what = BTlist.UPDATEIDENTIFIER;
				msg.what = BTlist.CREATPANEL;
				BTlist.this.myHandler.sendMessage(message);
			 	try {
					//Thread.sleep(5000); IT WAS,,,
			 		
			 		Thread.sleep(5000);
					if (// !panel_created&&
					BTlist.mNewDevicesArrayAdapter.getCount() > 0) {
						BTlist.this.myHandler.sendMessage(msg);
						// panel_created = true;
 					}
				} catch (InterruptedException e12) {
					Thread.currentThread().interrupt();
				} 
			}
		}
	 
	} 
	
	public static ArrayList<String> getPOInames()
	{
		return onlyPOInames;
	 
	}
	public static ArrayList<String> getPOInamesWithDistance()
	{
		return POINameWithDistance;
	}
	public static ArrayList<String> getTpids()
	{
		return tpids;
	}
	public static ArrayList<String> getMacAddr()
	{
		return MacAddr;
	}
	// @Override
	// protected void onRestart() {
	// super.onRestart();
	// pageInfo.setText(pageName);
	// sayPageName(pageName);
	// }
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
//flag = true;
		
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

 		}else if(e2.getX() - e1.getX() >SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			this.sayPageName();
			
		}else if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
		//	this.sayPageName("up");
			 if(flag)
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
						
			
							if(count1==(options.size()-1)) 
							{
								
								if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
								{
									try {
										
										Thread.sleep(1400);
										releaseSoundEffect();
										playSound(EDGE);
										
									}catch(InterruptedException e11){
										e11.printStackTrace();
									}
						 
								}
								else if(options.get(count1).length()>16)
								{
									try {
										
										Thread.sleep(2100);
										releaseSoundEffect();
										playSound(EDGE);
										
									}catch(InterruptedException e12){
										e12.printStackTrace();
									}
						 
								}else 
								{
									try {
										
										Thread.sleep(700);
										releaseSoundEffect();
										playSound(EDGE);
										
									}catch(InterruptedException e13){
										e13.printStackTrace();
									}
								}
 
							} 
						//	 viewA.setText("Up"+count1);
					   
							count1--;
			
						}
					} 
					
				 
 
				 flag = false;
			 }
			
	    //  viewA.setText("-" + "Fling up?" + "-");

		}else if(e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			 if(flag)
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
			 			
 			 
							if(count1==(options.size()-1)) 
							{
								
								if((options.get(count1).length()>8)&&(options.get(count1).length()<16))
								{
									try {
										
										Thread.sleep(1400);
										releaseSoundEffect();
										playSound(EDGE);
										
									}catch(InterruptedException e21){
										e21.printStackTrace();
									}
						 
								}
								else if(options.get(count1).length()>16)
								{
									try {
										
										Thread.sleep(2100);
										releaseSoundEffect();
										playSound(EDGE);
										
									}catch(InterruptedException e22){
										e22.printStackTrace();
									}
						 
								}else 
								{
									try {
										
										Thread.sleep(700);
										releaseSoundEffect();
										playSound(EDGE);
										
									}catch(InterruptedException e23){
										e23.printStackTrace();
									}
								}
 
							} 
					    
							count1++;
	
						}
				 }  
 
				 flag = false;
			 }
			 
 
 
			
			}

		
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		flag = true; 
		flag0 = true; 
		
		return true;

	}
	
   
}
