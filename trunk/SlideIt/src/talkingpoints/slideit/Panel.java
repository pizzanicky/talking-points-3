package talkingpoints.slideit;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Panel extends SurfaceView implements SurfaceHolder.Callback {
    private drawThread _thread;
    private String message;
    public Panel(Context context) {
        super(context);
        getHolder().addCallback(this);
        _thread = new drawThread(getHolder(), this);
        setFocusable(true);
        message = new String("");
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (_thread.getSurfaceHolder()) {
             if (event.getAction() == MotionEvent.ACTION_DOWN) {
                message = "DOWN X = "+ event.getX() + " Y = " + event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            	message = "MOVE X = "+ event.getX() + " Y = " + event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
            	message = "UP X = "+ event.getX() + " Y = " + event.getY();
            }
            return true;
        }
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        Paint messagePaint = new Paint();
        messagePaint.setColor(Color.BLUE);
        messagePaint.setTextSize(20.f);
        canvas.drawText(message, 100, 100, messagePaint);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        _thread.setRunning(true);
        _thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // simply copied from sample application LunarLander:
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        _thread.setRunning(false);
        while (retry) {
            try {
                _thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }
}
