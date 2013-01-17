/**
 * 
 */
package robindecroon.homeviz.usage;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.activity.FullScreenActivity;
import robindecroon.homeviz.listeners.HomeVizListener;
import robindecroon.homeviz.room.Room;

/**
 * FullScreenActivity die ook nog de locatie bijhoudt. Een horizontale swype
 * beweging verandert van kamer.
 * 
 * @author Robin
 * 
 */
public abstract class UsageFullScreenActivity extends FullScreenActivity
		implements HomeVizListener, TabListener {

	/**
	 * De huidige kamer.
	 */
	protected Room currentRoom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initRoom();
		initActionBar();
	}

	private void initActionBar() {
		HomeVizApplication app = (HomeVizApplication) getApplication();
		ActionBar actionBar = getActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.addTab(getActionBar().newTab().setText("Testtab1")
				.setTabListener(this));
		actionBar.addTab(getActionBar().newTab().setText("Testtab2")
				.setTabListener(this));
		// actionBar.setSelectedNavigationItem(app.getTabPosition());
	}

	void initRoom() {
		currentRoom = ((HomeVizApplication) getApplication()).getCurrentRoom();
	}

	@Override
	public void onSwypeToLeft() {
		currentRoom = ((HomeVizApplication) getApplication()).previousRoom();
	}

	@Override
	public void onSwypeToRight() {
		currentRoom = ((HomeVizApplication) getApplication()).nextRoom();
	}

	@Override
	public void refreshElements() {
		super.refreshElements();
		setLocation();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		System.out.println(tab.getPosition());
		HomeVizApplication app = (HomeVizApplication) getApplication();
		try {
			app.setCurrentRoom(tab
					.getPosition());
			currentRoom =  app.getCurrentRoom();
			refreshElements();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Intent intent = new Intent(this, UsageActivity.class);
		// startActivity(intent);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	protected abstract void setLocation();
}
