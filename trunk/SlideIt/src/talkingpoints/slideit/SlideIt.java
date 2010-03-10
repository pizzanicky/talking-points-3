package talkingpoints.slideit;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SlideIt extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
    }
   
}