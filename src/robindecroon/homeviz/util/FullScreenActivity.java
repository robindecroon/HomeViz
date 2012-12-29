/**
 * 
 */
package robindecroon.homeviz.util;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.room.Room;
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
public abstract class FullScreenActivity extends Activity implements
		HomeVizListener {

	protected Room currentRoom;
	protected Period currentPeriod;

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
		rootView.setOnTouchListener(new TouchListener(this));
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
