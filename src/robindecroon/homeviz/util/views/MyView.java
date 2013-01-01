package robindecroon.homeviz.util.views;

import robindecroon.homeviz.listeners.TouchListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public interface MyView extends OnTouchListener {
	
	public void setListener(TouchListener listener);
	
	public boolean onTouch(View v, MotionEvent event);

	public boolean onTouchEvent(MotionEvent event);

}
