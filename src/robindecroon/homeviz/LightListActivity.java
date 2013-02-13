package robindecroon.homeviz;

import robindecroon.homeviz.usage.UsageFullScreenActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * An activity representing a list of Lights. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link LightDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link LightListFragment} and the item details (if present) is a
 * {@link LightDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link LightListFragment.Callbacks} interface to listen for item selections.
 */
public class LightListActivity extends UsageFullScreenActivity implements
		LightListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light_list);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (findViewById(R.id.light_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.

			((LightListFragment) getSupportFragmentManager().findFragmentById(
					R.id.light_list)).setActivateOnItemClick(true);
			// onItemSelected("L1");
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Callback method from {@link LightListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(LightDetailFragment.ARG_ITEM_ID, id);
			LightDetailFragment fragment = new LightDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.light_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, LightDetailActivity.class);
			detailIntent.putExtra(LightDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	@Override
	public void onSwypeToUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSwypeToDown() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setPeriod() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setLocation() {
		// TODO Auto-generated method stub

	}
}
