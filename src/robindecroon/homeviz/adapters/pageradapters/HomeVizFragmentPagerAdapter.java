/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.adapters.pageradapters;

import robindecroon.homeviz.HomeVizApplication;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * The Class HomeVizFragmentPagerAdapter.
 */
public abstract class HomeVizFragmentPagerAdapter extends
		FragmentStatePagerAdapter {

	/** The app. */
	protected HomeVizApplication app;

	/**
	 * Instantiates a new home viz fragment pager adapter.
	 *
	 * @param fragmentManager the fragment manager
	 * @param app the app
	 */
	public HomeVizFragmentPagerAdapter(FragmentManager fragmentManager,
			HomeVizApplication app) {
		super(fragmentManager);
		this.app = app;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
	 */
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return app.getRooms().size();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		return app.getRooms().get(position).getName();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
	 */
	@Override
	public abstract Fragment getItem(int arg0);

}