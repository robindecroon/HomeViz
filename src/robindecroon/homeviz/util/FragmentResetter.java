/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.util;

import robindecroon.homeviz.fragments.metaphor.MetaphorContainerFragment;
import robindecroon.homeviz.fragments.total.TotalTreeMapFragment;
import robindecroon.homeviz.fragments.usage.UsageContainerFragment;
import android.app.Activity;
import android.util.Log;

/**
 * The Class FragmentResetter.
 */
public class FragmentResetter {

	/**
	 * Reset all OptionSpinnerFragements.
	 *
	 * @param optionSpinnerFragment the optionSpinnerFragment
	 */
	public static void reset(Activity optionSpinnerFragment) {
		try {
			optionSpinnerFragment.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						UsageContainerFragment.resetViews();
					} catch (Exception e) {}
					try {
						MetaphorContainerFragment.resetViews();
					} catch (Exception e) {}
					try {
						TotalTreeMapFragment.resetViews();
					} catch (Exception e) {}
				}
			});
		} catch (Exception e) {
			Log.e("FragmentResetter", "User scrolled the optionspinner too fast");
		}
	}

}
