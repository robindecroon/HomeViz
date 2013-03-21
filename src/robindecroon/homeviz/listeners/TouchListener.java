///**
// * 
// */
//package robindecroon.homeviz.listeners;
//
//import robindecroon.homeviz.HomeVizApplication;
//import robindecroon.homeviz.activity.FullScreenActivity;
//import robindecroon.homeviz.util.Period;
//import android.graphics.PointF;
//import android.os.Handler;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//
///**
// * @author Robin
// * 
// */
//public class TouchListener implements View.OnTouchListener {
//
//	private final static int NONE = 0;
//	private final static int SWYPE = 1;
//	private final static int ZOOM = 2;
//	private final static int LONG_CLICK = 3;
//	// private final static int CLICK = 3;
//
//	private PointF start = new PointF();
//	private PointF mid = new PointF();
//	private double oldDist = 1d;
//
//	private double initialScale = 0;
//	private double lastScale;
//
//	private boolean actionBarShown = false;
//
//	private int mode = NONE;
//
//	private FullScreenActivity context;
//	private HomeVizApplication appContext;
//
//	private long lastClick;
//
//	/**
//	 * 
//	 */
//	public TouchListener(FullScreenActivity fullScreenActivity) {
//		context = fullScreenActivity;
//		try {
//			appContext = (HomeVizApplication) this.context
//					.getApplicationContext();
//		} catch (ClassCastException e) {
//			Log.e("TouchListener", "Incompatible Actitvity");
//		}
//	}
//
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		switch (event.getAction() & MotionEvent.ACTION_MASK) {
//		case MotionEvent.ACTION_DOWN:
//			lastClick = System.currentTimeMillis();
//			mode = SWYPE;
//			start.set(event.getX(), event.getY());
//			break;
//		case MotionEvent.ACTION_POINTER_DOWN:
//			oldDist = spacing(event);
//			if (oldDist > 10f) {
//				midPoint(mid, event);
//				mode = ZOOM;
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			int xDiff = (int) (start.x - event.getX());
//			int yDiff = (int) (start.y - event.getY());
//			if (Math.abs(xDiff) < 8 && Math.abs(yDiff) < 8 && mode != ZOOM) {
//				if (System.currentTimeMillis() - lastClick > 500)
//					mode = LONG_CLICK;
//			}
//
//			if (mode == SWYPE) {
//				if (xDiff < -80) {
//					context.onSwypeToLeft();
//					break;
//				} else if (xDiff > 80) {
//					context.onSwypeToRight();
//					break;
//				}
//				if (yDiff < -80) {
//					context.onSwypeToUp();
//					break;
//				} else if (yDiff > 80) {
//					context.onSwypeToDown();
//					break;
//				}
//			}
//			break;
//		case MotionEvent.ACTION_POINTER_UP:
//			mode = NONE;
//			Period period = appContext.getCurrentPeriod();
//			if (lastScale > initialScale) {
//				period = period.next();
//				context.onZoomOut();
//			} else {
//				period = period.previous();
//				context.onZoomIn();
//			}
//			context.setCurrentPeriod(period);
//			// break;
//			return true;
//		case MotionEvent.ACTION_MOVE:
//			if (mode == ZOOM) {
//				double newDist = spacing(event);
//				if (newDist > 200f) {
//					double scale = newDist / oldDist;
//					if (initialScale == 0) {
//						initialScale = scale;
//					}
//					lastScale = scale;
//				}
//			}
//			break;
//		}
//		switch (mode) {
//		case LONG_CLICK:
//			showActionBar();
//			return false;
//		default:
//			break;
//		}
//		return true;
//	}
//
//	private void showActionBar() {
//		final View rootView = context.getWindow().getDecorView();
//		rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//		context.getActionBar().show();
//		actionBarShown = true;
//
//		final Handler handler = new Handler();
//		handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				context.getActionBar().hide();
//				rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
//			}
//		}, 3000);
//	}
//
//	private void togglActionBar() {
//		View rootView = context.getWindow().getDecorView();
//		if (actionBarShown) {
//			context.getActionBar().hide();
//			rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
//			actionBarShown = false;
//		} else {
//			context.getActionBar().show();
//			rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//			actionBarShown = true;
//		}
//	}
//
//	/** Determine the space between the first two fingers */
//	private double spacing(MotionEvent event) {
//		// ...
//		double x = event.getX(0) - event.getX(1);
//		double y = event.getY(0) - event.getY(1);
//		return Math.sqrt(x * x + y * y);
//	}
//
//	/** Calculate the mid point of the first two fingers */
//	private void midPoint(PointF point, MotionEvent event) {
//		// ...
//		float x = event.getX(0) + event.getX(1);
//		float y = event.getY(0) + event.getY(1);
//		point.set(x / 2, y / 2);
//	}
//}
