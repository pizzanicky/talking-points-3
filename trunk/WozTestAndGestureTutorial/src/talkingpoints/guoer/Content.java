package talkingpoints.guoer;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Content extends GestureUI implements OnInitListener {

	private TextView text;
	// private TextView title;
	private String content;
	private static final String TAG = "MAC = ";
	private TextToSpeech mTts;
	private GestureDetector gestureScanner;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private static boolean flag=false; 
	private static boolean flag2=false; 
	private static boolean flag3=false; 

    private static int count1=0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gestureScanner = new GestureDetector(this);
		setContentView(R.layout.poi_view);
		// get MAC address from picked device
		// MAC = getIntent().getStringExtra("MAC");
		content = getIntent().getStringExtra("content");

		// title = (TextView) findViewById(R.id.poi_detail);
		text = (TextView) findViewById(R.id.poi_info);

	//	count1=0;
	
		// retrieve data from server

		text.setText(content);
		// title.setText(p.getName());
		mTts = new TextToSpeech(this, this);
		// findViewById(R.id.poi_detail).setVisibility(View.VISIBLE);
		findViewById(R.id.poi_info).setVisibility(View.VISIBLE);
		setResult(Activity.RESULT_OK);
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (gestureScanner.onTouchEvent(event)) {
			Log.w(TAG, "HAHA = " + "touch");
			return true;
		} else
			return false;

	}

	 

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
//flag = true;
		
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			

			finish();
		}else if(e2.getX() - e1.getX() >SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			mTts.speak(content, TextToSpeech.QUEUE_FLUSH, null);
			
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
									count1-=2;
								
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
											
										}catch(InterruptedException e14){
											e14.printStackTrace();
										}
							 
									}
									else if(options.get(count1).length()>16)
									{
										try {
											
											Thread.sleep(2100);
											releaseSoundEffect();
											playSound(EDGE);
											
										}catch(InterruptedException e15){
											e15.printStackTrace();
										}
							 
									}else 
									{
										try {
											
											Thread.sleep(700);
											releaseSoundEffect();
											playSound(EDGE);
											
										}catch(InterruptedException e16){
											e16.printStackTrace();
										}
									}
	 
								} 
								count1++;
		
							}
					 }
	 
					 flag = false;
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
	
	@Override
	// disable scroll gesture
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		flag = true; 
		
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if we get any key, clear the Splash Screen
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			this.mTts.speak("Talking Points Home", TextToSpeech.QUEUE_FLUSH,
					null);

			Intent intent = new Intent(Content.this, GateWay.class);
			startActivity(intent);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			super.onPause();
			try {
				wait();
			} catch (InterruptedException e21) {
				// TODO Auto-generated catch block
				e21.printStackTrace();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e22) {
				e22.printStackTrace();
			}

			Toast
					.makeText(getApplicationContext(), "home!",
							Toast.LENGTH_SHORT).show();

			this.mTts.speak("home", TextToSpeech.QUEUE_FLUSH, null);

			onDestroy();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_SEARCH) {

			this.mTts.speak("Keyword Search", TextToSpeech.QUEUE_FLUSH, null);

			// Intent intent = new Intent(GestureUI.this, Search.class);
			// startActivity(intent);

			return true;
		}else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
 
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
								
							}catch(InterruptedException e31){
								e31.printStackTrace();
							}
				 
						}
						else if(options.get(count1).length()>16)
						{
							try {
								
								Thread.sleep(2100);
								releaseSoundEffect();
								playSound(EDGE);
								
							}catch(InterruptedException e32){
								e32.printStackTrace();
							}
				 
						}else 
						{
							try {
								
								Thread.sleep(700);
								releaseSoundEffect();
								playSound(EDGE);
								
							}catch(InterruptedException e33){
								e33.printStackTrace();
							}
						}

					} 
					count1++;
	
				}
			}
 
		}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
		
			flag2=true;
			
			if(options.size()!=0){
				if(flag3)
				{
					if(count1==1)
						count1=options.size()-1;
	//				else if(count==5)
	//					count=3;
					else 
						count1-=2;
					
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
								
							}catch(InterruptedException e41){
								e41.printStackTrace();
							}
				 
						}
						else if(options.get(count1).length()>16)
						{
							try {
								
								Thread.sleep(2100);
								releaseSoundEffect();
								playSound(EDGE);
								
							}catch(InterruptedException e42){
								e42.printStackTrace();
							}
				 
						}else 
						{
							try {
								
								Thread.sleep(700);
								releaseSoundEffect();
								playSound(EDGE);
								
							}catch(InterruptedException e43){
								e43.printStackTrace();
							}
						}

					} 
				//	 viewA.setText("Up"+count1);
			   
					count1--;
	
				}
			}
		}

		return true;// return super.onKeyDown(keyCode, event);
	}
	@Override
	// override onInit to read the content once start
	public void onInit(int status) {
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
		if (status == TextToSpeech.SUCCESS) {
			// Set preferred language to US english.
			// Note that a language may not be available, and the result will
			// indicate this.
			int result = mTts.setLanguage(Locale.US);
			// Try this someday for some interesting results.
			// int result mTts.setLanguage(Locale.FRANCE);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Lanuage data is missing or the language is not supported.
				Log.e(TAG, "Language is not available.");
			} else
				mTts.speak(content, TextToSpeech.QUEUE_FLUSH, null);
		} else {
			// Initialization failed.
			Log.e(TAG, "Could not initialize TextToSpeech.");
		}
	}

}
