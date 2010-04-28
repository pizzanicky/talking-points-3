package talkingpoints.guoer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;

public class POImenu extends GestureUI {

	private static String GET_POI_INFO = "http://app.talking-points.org/locations/";
	private String ID;
	private String MAC;
	private String content;
	private MsgParser p;
	private Intent intent;

	public void onCreate(Bundle savedInstanceState) {
		pageName = new String();
		pageName = getIntent().getStringExtra("POIname");
		super.onCreate(savedInstanceState);
		this.options.add("Type");
		this.options.add("Description");
		this.options.add("What's around" + pageName);
		ID = "";
		MAC = "";
		ID = getIntent().getStringExtra("tpid");
		MAC = getIntent().getStringExtra("MAC");
		if (ID != null)
			p = new MsgParser(GET_POI_INFO + ID + ".xml");
		else if (MAC != null)
			p = new MsgParser(GET_POI_INFO + MAC + ".xml");

		// add up to 5 sections to the menu option list
		for (int i = 0; i < p.getSetionNameValue().size();) {
			if (!p.getSectionTextValue().get(i).equalsIgnoreCase("")) {
				options.add(p.getSetionNameValue().get(i));
				i++;
			}
		}
		if (p.getCommentId().size() >= 1)
			options.add("Comments");

		// TODO: get poi menu from xml parser

		super.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onDoubleTap(MotionEvent e) {
				switch (GestureUI.selected) {
				case 0:
					content = p.getLocationType();
					intent = new Intent(POImenu.this, Content.class);
					intent.putExtra("content", content);
					startActivity(intent);
					break;
				case 1:
					content = p.getDescription();
					intent = new Intent(POImenu.this, Content.class);
					intent.putExtra("content", content);
					startActivity(intent);
					break;
				case 2:
					// what's around
					break;
				}
				for (int i = 3; i < (options.size() - 1); i++) {
					if (GestureUI.selected == i) {
						intent = new Intent(POImenu.this, Content.class);
						intent.putExtra("content", p.getSectionTextValue().get(
								i - 3));
						// intent.putExtra("MAC", MAC);
						startActivity(intent);
					}
				}
				if (p.getCommentId().size() >= 1
						&& GestureUI.selected == (options.size() - 1)) {
					// get comments
					ArrayList<String> CommentsTitles = new ArrayList<String>();
					for (int i = 0; i < p.getCommentId().size(); i++) {
						CommentsTitles.add(p.getCommentTitle().get(i));
					}
					Intent intent = new Intent(POImenu.this, Comments.class);
					intent.putStringArrayListExtra("CommentsTitles",
							CommentsTitles);
					intent.putStringArrayListExtra("Comments", p
							.getCommentText());
					startActivity(intent);
				}
				// Intent intent = new Intent(POImenu.this, POIdetail.class);
				// intent.putExtra("content", content);
				// intent.putExtra("MAC", MAC);
				// startActivity(intent);
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
}
