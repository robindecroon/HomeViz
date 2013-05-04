/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.adapters.pageradapters;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.fragments.usage.UsageIconFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * The Class UsageIconFragmentPagerAdapter.
 */
public class UsageIconFragmentPagerAdapter extends
		robindecroon.homeviz.adapters.pageradapters.HomeVizFragmentPagerAdapter {

	/**
	 * Instantiates a new usage icon fragment pager adapter.
	 *
	 * @param fragmentManager the fragment manager
	 * @param app the app
	 */
	public UsageIconFragmentPagerAdapter(FragmentManager fragmentManager,
			HomeVizApplication app) {
		super(fragmentManager, app);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.adapters.pageradapters.HomeVizFragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
		Bundle args = new Bundle();
		args.putInt(Constants.USAGE_ROOM, arg0);
		Fragment usageIconFragment = new UsageIconFragment();
		usageIconFragment.setArguments(args);
		return usageIconFragment;
	}

}
