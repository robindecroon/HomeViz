/**
 * 
 */
package robindecroon.homeviz.usage;

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
		implements HomeVizListener {

	/**
	 * De huidige kamer.
	 */
	protected Room currentRoom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initRoom();
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

	protected abstract void setLocation();
}
