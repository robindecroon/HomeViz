/**
 * 
 */
package robindecroon.homeviz.util;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.room.Room;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * @author Robin
 * 
 */
public abstract class FullScreenActivity extends Activity implements
		ZoomListener, SwypeListener {

	protected Room currentRoom;
	protected Period currentPeriod;

	/**
	 * 
	 */
	public FullScreenActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentRoom = ((HomeVizApplication) getApplication()).getCurrentRoom();
		currentPeriod = ((HomeVizApplication) getApplication())
				.getCurrentPeriod();

		getActionBar().hide();
		View rootView = getWindow().getDecorView();
		rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		rootView.setOnTouchListener(new OnTouchListener() {

			PointF start = new PointF();
			PointF mid = new PointF();
			double oldDist = 1d;

			final static int NONE = 0;
			final static int SWYPE = 1;
			final static int ZOOM = 2;
			final static int CLICK = 3;
			int mode = NONE;

			double initialScale = 0;
			double lastScale;

			boolean actionBarShown = false;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					mode = SWYPE;
					start.set(event.getX(), event.getY());
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					if (oldDist > 10f) {
						midPoint(mid, event);
						mode = ZOOM;
					}
					break;
				case MotionEvent.ACTION_UP:
					int xDiff = (int) (start.x - event.getX());
					int yDiff = (int) (start.y - event.getY());
					if ((int) Math.abs(xDiff) < 8 && (int) Math.abs(yDiff) < 8
							&& mode != ZOOM) {
						mode = CLICK;
					}

					if (mode == SWYPE) {
						if (xDiff < -80) {
							onSwypeToLeft();
							break;
						} else if (xDiff > 80) {
							onSwypeToRight();
							break;
						}
						if (yDiff < -80) {
							onSwypeToUp();
							break;
						} else if (yDiff > 80) {
							onSwypeToDown();
							break;
						}
					}
					break;
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					if (lastScale > initialScale) {
						currentPeriod = currentPeriod.next();
						onZoomOut();
					} else {
						currentPeriod = currentPeriod.previous();
						onZoomIn();
					}
					((HomeVizApplication) getApplication())
							.setCurrentPeriod(currentPeriod);
					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == NONE) {
						// Drag?
					} else if (mode == ZOOM) {
						double newDist = spacing(event);
						if (newDist > 200f) {
							double scale = newDist / oldDist;
							if (initialScale == 0) {
								initialScale = scale;
							}
							lastScale = scale;
						}
					}
					break;
				}
				switch (mode) {
				case CLICK:
					View rootView = getWindow().getDecorView();
					if (actionBarShown) {
						getActionBar().hide();
						rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
						actionBarShown = false;
					} else {
						getActionBar().show();
						rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
						actionBarShown = true;
					}
					break;

				default:
					break;
				}
				return true;
			}
		});
	}

	/** Determine the space between the first two fingers */
	private double spacing(MotionEvent event) {
		// ...
		double x = event.getX(0) - event.getX(1);
		double y = event.getY(0) - event.getY(1);
		return Math.sqrt(x * x + y * y);
	}

	/** Calculate the mid point of the first two fingers */
	private void midPoint(PointF point, MotionEvent event) {
		// ...
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 0, Menu.NONE, "Settings");
		return true;
	}
}
