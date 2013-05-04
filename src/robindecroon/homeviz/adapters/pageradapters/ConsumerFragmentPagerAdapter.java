/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.adapters.pageradapters;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.fragments.usage.UsageConsumerFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * The Class ConsumerFragmentPagerAdapter.
 */
public class ConsumerFragmentPagerAdapter extends HomeVizFragmentPagerAdapter {

	/** The usageType. */
	private int usageType;

	/**
	 * Instantiates a new usageType fragment pager adapter.
	 *
	 * @param fragmentManager the fragment manager
	 * @param app the app
	 * @param usageType the usageType
	 */
	public ConsumerFragmentPagerAdapter(FragmentManager fragmentManager,
			HomeVizApplication app, int usageType) {
		super(fragmentManager, app);
		this.usageType = usageType;
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.adapters.pageradapters.HomeVizFragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
		Bundle args = new Bundle();
		args.putInt(Constants.USAGE_ROOM, arg0);
		args.putInt(Constants.USAGE_TYPE, usageType);
		Fragment usageConsumerFragment = new UsageConsumerFragment();
		usageConsumerFragment.setArguments(args);
		return usageConsumerFragment;
	}
}
