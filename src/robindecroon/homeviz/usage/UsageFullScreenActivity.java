/**
 * 
 */
package robindecroon.homeviz.usage;

import java.util.List;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.activity.FullScreenActivity;
import robindecroon.homeviz.listeners.HomeVizListener;
import robindecroon.homeviz.room.Room;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * FullScreenActivity die ook nog de locatie bijhoudt. Een horizontale swype
 * beweging verandert van kamer.
 * 
 * @author Robin
 * 
 */
public abstract class UsageFullScreenActivity extends FullScreenActivity
		implements HomeVizListener, TabListener {

	public static boolean init = true;

	/**
	 * De huidige kamer.
	 */
	protected Room currentRoom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init = true;
		initRoom();
		initActionBar();
	}

	protected void initActionBar() {
		HomeVizApplication app = (HomeVizApplication) getApplication();
		ActionBar actionBar = getActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		fillTabs(actionBar);
		actionBar.setSelectedNavigationItem(app.getTabPosition());
	}

	private void fillTabs(ActionBar actionBar) {
		HomeVizApplication app = (HomeVizApplication) getApplication();

		List<Room> rooms = app.getRooms();
//		if (hasHouse()) {
//			app.addHouse();
//		} else {
//			app.removeHouse();
//		}
		for (int i = 0; i < rooms.size(); i++) {
			actionBar.addTab(getActionBar().newTab()
					.setText(rooms.get(i).getName()).setTabListener(this));
		}
	}

	protected boolean hasHouse() {
		return false;
	}

	void initRoom() {
		currentRoom = ((HomeVizApplication) getApplication()).getCurrentRoom();
	}

	@Override
	public void onSwypeToLeft() {
		currentRoom = ((HomeVizApplication) getApplication()).previousRoom();
		Intent intent = new Intent(this, getClass());
		startActivity(intent);
		overridePendingTransition(R.anim.left_enter, R.anim.left_leave);
		finish();
	}

	@Override
	public void onSwypeToRight() {
		currentRoom = ((HomeVizApplication) getApplication()).nextRoom();
		Intent intent = new Intent(this, getClass());
		startActivity(intent);
		overridePendingTransition(R.anim.right_enter, R.anim.right_leave);
		finish();
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
		HomeVizApplication app = (HomeVizApplication) getApplication();
		if (!init) {
			try {
				app.setCurrentRoom(tab.getPosition());
				currentRoom = app.getCurrentRoom();
				refreshElements();
			} catch (Exception e) {
				Log.i("UsageFullScreenActivity",
						"tabSelectedTooSoon: " + e.getMessage());
			}
		} else {
			init = false;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	protected abstract void setLocation();
}
