package robindecroon.homeviz.listeners;

import android.content.Context;

public interface HomeVizListener {

	public Context getApplicationContext();

	public void onZoomIn();

	public void onZoomOut();

	public void onSwypeToLeft();

	public void onSwypeToRight();

	public void onSwypeToUp();

	public void onSwypeToDown();

}
