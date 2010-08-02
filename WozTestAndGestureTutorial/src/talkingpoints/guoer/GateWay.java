package talkingpoints.guoer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;

public class GateWay extends GestureUI {
	private ArrayList<String> MenuOptions;
	private String message1;
	WozParser p;
	byCoordinateParser p1;
	private static String message;

	private static String GET_COORDINATE = "http://talkingpoints.dreamhosters.com/maps_test/point.xml";
	private static String BY_COORDINATE = "http://app.talking-points.org/locations/by_coordinates/";
	// private void sayPageName() {
	// this.mTts.speak(PageName, TextToSpeech.QUEUE_FLUSH, null);
	// }

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private static int count1 = 0;
	private static boolean flag = false;
	private static boolean flag2 = false;
	private static boolean flag3 = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MenuOptions = new ArrayList<String>();
		MenuOptions.add("Start Detection"); // Bert's calf ay
		MenuOptions.add("Quick Tutorial");
		// MenuOptions.add("Building Directory");
		// MenuOptions.add("Keyword Search");
		// MenuOptions.add("Flashlight");
		pageName = new String(
				"Talking Points Home. Swipe down to hear menu options. Double tap to select.");
		super.onCreate(savedInstanceState, MenuOptions);

		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
				switch (GestureUI.selected) {
				case 0:
					Intent intent = new Intent(GateWay.this, BTlist.class);
					startActivity(intent);
					break;
				case 1:
					Intent intent1 = new Intent(GateWay.this, Tutorial.class);
					startActivity(intent1);
					break;
				case 2:
					break;
				case 3:
					/*
					 * Intent intent1 = new Intent(GateWay.this,
					 * POIsAhead2.class); startActivity(intent1);
					 */
					break;
				}
				return true;
			}

			public boolean onDoubleTapEvent(MotionEvent e) {
				return false;
			}

			public boolean onSingleTapConfirmed(MotionEvent e) {
				return false;
			}

		});

	}

	@Override
	public boolean onFling(MotionEvent e3, MotionEvent e4, float velocityX,
			float velocityY) {
		// flag=true;
		// // TODO Auto-generated method stub
		// try {
		// // if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
		// // return false;
		// // right to left swipe
		// if (e3.getX() - e4.getX() > SWIPE_MIN_DISTANCE
		// && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		//				
		//				
		// message =
		// "please press the screen longer to finish the talking point";
		// say(message);
		// //finish();
		//
		// }else if(e4.getX() - e3.getX() >SWIPE_MIN_DISTANCE
		// && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		// this.sayPageName();
		// }
		// } catch (Exception e) {
		// // nothing
		// }

		// TODO Auto-generated method stub
		if (e3.getX() - e4.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

			// try {
			//				
			// Thread.sleep(2000);
			//	
			// }catch(InterruptedException e11){
			// e11.printStackTrace();
			// }
			this.sayPageName("Finish Talking Points");
			// finish();
		} else if (e4.getX() - e3.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			// viewA.setText("-" + "Fling Right" + "-");
			this.sayPageName();

		} else if (e3.getY() - e4.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			// this.sayPageName("up");
			if (flag) {
				flag2 = true;

				if (options.size() != 0) {
					if (flag3) {
						if (count1 == 1)
							count1 = options.size() - 1;
						// else if(count==5)
						// count=3;
						else {
							if (count1 != 0)
								count1 -= 2;
						}
						flag3 = false;
					}

					if (count1 != 0) {
						if (count1 == options.size()) {

							count1 = options.size() - 2;
						}
					}

					if (count1 == 0) {
						// this.sayPageName("0");

						message = options.get(count1);

						selected = count1;
						text.setText(message);

						this.mTts
								.speak(message, TextToSpeech.QUEUE_FLUSH, null);

						releaseSoundEffect();
						playSound(ITEM_BY_ITEM);

						// viewA.setText("UP"+count1);
						count1 = options.size() - 1;

					} else if (count1 < options.size()) {

						message = options.get(count1);

						selected = count1;
						text.setText(message);

						this.mTts
								.speak(message, TextToSpeech.QUEUE_FLUSH, null);

						releaseSoundEffect();
						playSound(ITEM_BY_ITEM);

						if (count1 == (options.size() - 1)) {

							if ((options.get(count1).length() > 8)
									&& (options.get(count1).length() < 16)) {
								try {

									Thread.sleep(1400);
									releaseSoundEffect();
									playSound(EDGE);

								} catch (InterruptedException e11) {
									e11.printStackTrace();
								}

							} else if (options.get(count1).length() > 16) {
								try {

									Thread.sleep(2100);
									releaseSoundEffect();
									playSound(EDGE);

								} catch (InterruptedException e12) {
									e12.printStackTrace();
								}

							} else {
								try {

									Thread.sleep(700);
									releaseSoundEffect();
									playSound(EDGE);

								} catch (InterruptedException e13) {
									e13.printStackTrace();
								}
							}

						}
						// viewA.setText("Up"+count1);

						count1--;

					}
				}

				flag = false;
			}

			// viewA.setText("-" + "Fling up?" + "-");

		} else if (e4.getY() - e3.getY() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			if (flag) {

				flag3 = true;
				if (options.size() != 0) {

					if (flag2) {
						if (count1 == options.size() - 1)
							count1 = 1;
						else
							count1 += 2;

						flag2 = false;
					}

					if (count1 == options.size()) {

						count1 = 0;

					}

					if (count1 < options.size()) // count<.size()
					{

						// viewA.setText("Down"+count1);
						message = options.get(count1);

						selected = count1;
						text.setText(message);

						this.mTts
								.speak(message, TextToSpeech.QUEUE_FLUSH, null);

						releaseSoundEffect();
						playSound(ITEM_BY_ITEM);

						if (count1 == (options.size() - 1)) {

							if ((options.get(count1).length() > 8)
									&& (options.get(count1).length() < 16)) {
								try {

									Thread.sleep(1400);
									releaseSoundEffect();
									playSound(EDGE);

								} catch (InterruptedException e21) {
									e21.printStackTrace();
								}

							} else if (options.get(count1).length() > 16) {
								try {

									Thread.sleep(2100);
									releaseSoundEffect();
									playSound(EDGE);

								} catch (InterruptedException e22) {
									e22.printStackTrace();
								}

							} else {
								try {

									Thread.sleep(700);
									releaseSoundEffect();
									playSound(EDGE);

								} catch (InterruptedException e23) {
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

	public void say(String message) {
		this.mTts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onLongPress(MotionEvent e5) {
		// TODO Auto-generated method stub

		// s if(getLongPressTimeout()
		/*
		 * try{
		 * 
		 * message = "bye"; say(message); finish();
		 * 
		 * }catch(Exception e1){ //nothing }
		 */
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		flag = true;
		return true;

	}

}
