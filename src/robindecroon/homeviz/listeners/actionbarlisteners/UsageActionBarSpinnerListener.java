/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.listeners.actionbarlisteners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.activities.MainActivity;
import android.view.View;
import android.widget.AdapterView;

/**
 * The listener for receiving usageActionBarSpinner events.
 *
 * @see UsageActionBarSpinnerEvent
 */
public class UsageActionBarSpinnerListener extends ActionBarSpinnerListener {

	/**
	 * Instantiates a new usage action bar spinner listener.
	 *
	 * @param context the context
	 */
	public UsageActionBarSpinnerListener(MainActivity context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.listeners.actionbarlisteners.ActionBarSpinnerListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {

		super.onItemSelected(parentView, selectedItemView, position, id);

		startIntent(position, Constants.USAGE);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.listeners.actionbarlisteners.ActionBarSpinnerListener#onNothingSelected(android.widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
}
