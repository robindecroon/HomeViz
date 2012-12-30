package robindecroon.homeviz.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;

public class MyWebView extends WebView implements OnTouchListener {

	private TouchListener listener;

	public MyWebView(Context context) {
		super(context);
	}

	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	};

	public void setListener(TouchListener listener) {
		this.listener = listener;
	}
	
	public MyWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs,defStyle);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return listener.onTouch(v, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean consumed = super.onTouchEvent(event);
		onTouch(this, event);
		return consumed;
	}

}
