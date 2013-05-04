/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.adapters.pageradapters;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.fragments.usage.UsageChartFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * The Class UsageChartFragmentPagerAdapter.
 */
public class UsageChartFragmentPagerAdapter extends HomeVizFragmentPagerAdapter {

	/** The usageChartType. */
	private int usageChartType;

	/**
	 * Instantiates a new usage chart fragment pager adapter.
	 *
	 * @param fm the fm
	 * @param app the app
	 * @param usageChartType the usageChartType
	 */
	public UsageChartFragmentPagerAdapter(FragmentManager fm,
			HomeVizApplication app, int usageChartType) {
		super(fm, app);
		this.usageChartType = usageChartType;
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.adapters.pageradapters.HomeVizFragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
		Bundle args = new Bundle();
		args.putInt(Constants.USAGE_ROOM, arg0);
		args.putInt(Constants.USAGE_BUNDLE_TYPE, usageChartType);
		Fragment usageChartFragment = new UsageChartFragment();
		usageChartFragment.setArguments(args);
		return usageChartFragment;
	}

}
