package talkingpoints.guoer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;

public class Comments extends GestureUI {

	private String pageName = "Comments";
	private ArrayList<String> Comments = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.options = getIntent().getStringArrayListExtra("CommentsTitles");
		// p = new MsgParser(GET_INFO_BYMAC + MAC + ".xml");
		// text.setText("POI menu");
		this.Comments = getIntent().getStringArrayListExtra("Comments");
		pageInfo.setText(pageName);
		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
				for (int i = 0; i < Comments.size(); i++) {
					if (GestureUI.selected == i) {
						Intent intent = new Intent(Comments.this, Content.class);
						intent.putExtra("content", Comments.get(i));
						// intent.putExtra("MAC", MAC);
						
						
						startActivity(intent);
					}
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
	protected void onRestart() {
		super.onRestart();
		pageInfo.setText(pageName);
		sayPageName(pageName);
	}
	
	
}
