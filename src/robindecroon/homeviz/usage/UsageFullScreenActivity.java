/**
 * 
 */
package robindecroon.homeviz.usage;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.listeners.HomeVizListener;
import robindecroon.homeviz.listeners.TouchListener;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.util.Period;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * @author Robin
 * 
 */
public abstract class UsageFullScreenActivity extends Activity implements
		HomeVizListener {

	protected Room currentRoom;
	protected Period currentPeriod;
	
	protected TouchListener  listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Haal de variabelen uit de applicatie op
		currentRoom = ((HomeVizApplication) getApplication()).getCurrentRoom();
		currentPeriod = ((HomeVizApplication) getApplication())
				.getCurrentPeriod();

		// Volledig scherm
		getActionBar().hide();
		View rootView = getWindow().getDecorView();
		rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		
		// Touch Listener
		listener = new TouchListener(this);
		rootView.setOnTouchListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 0, Menu.NONE, "Settings");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected abstract void setPeriod();
	protected abstract void setLocation();
	
	@Override
	public void onZoomIn() {
		refreshElements();
	}

	@Override
	public void onZoomOut() {
		refreshElements();
	}
	
	@Override
	public void onSwypeToLeft() {
		currentRoom = ((HomeVizApplication) getApplication()).previousRoom();
	}
	
	@Override
	public void onSwypeToRight() {
		currentRoom = ((HomeVizApplication) getApplication()).nextRoom();
	}
	
	public void refreshElements() {
		setPeriod();
		setLocation();
	}

	public void setCurrentPeriod(Period period) {
		((HomeVizApplication) getApplicationContext()).setCurrentPeriod(period);
		this.currentPeriod = period;
		refreshElements();
	}
}
