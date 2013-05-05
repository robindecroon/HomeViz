/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments.usage;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.adapters.pageradapters.ConsumerFragmentPagerAdapter;
import robindecroon.homeviz.adapters.pageradapters.UsageChartFragmentPagerAdapter;
import robindecroon.homeviz.adapters.pageradapters.UsageIconFragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

/**
 * The Class UsageContainerFragment.
 */
public class UsageContainerFragment extends Fragment {

	/** The pager. */
	private static ViewPager pager;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View usageContainerView = inflater.inflate(R.layout.simple_tabs, container, false);

		FragmentActivity activity = getActivity();
		HomeVizApplication app = (HomeVizApplication) activity.getApplication();
		FragmentManager manager = activity.getSupportFragmentManager();
		FragmentStatePagerAdapter adapter = null;
		Bundle args = getArguments();
		if (args != null) {
			switch (args.getInt(Constants.USAGE_TYPE)) {
			case 1:
				adapter = new UsageChartFragmentPagerAdapter(manager, app, 1);
				break;
			case 2:
				adapter = new UsageChartFragmentPagerAdapter(manager, app, 2);
				break;
			case 11:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 11);
				break;
			case 12:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 12);
				break;
			case 13:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 13);
				break;
			case 14:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 14);
				break;
			case 15:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 15);
				break;
			default:
				adapter = new UsageIconFragmentPagerAdapter(manager, app);
				break;
			}
		} else {
			Log.e(getClass().getSimpleName(), "No arguments were supplied!");
			adapter = new UsageIconFragmentPagerAdapter(manager, app);
		}
		pager = (ViewPager) usageContainerView.findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) usageContainerView
				.findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		indicator.setCurrentItem(args.getInt(Constants.USAGE_ROOM));

		return usageContainerView;
	}

	/**
	 * Gets the current selection.
	 *
	 * @return the current selection
	 */
	public static int getCurrentSelection() {
		if (pager != null)
			return pager.getCurrentItem();
		else
			return 0;
	}

	/**
	 * Reset views.
	 */
	public static void resetViews() {
		pager.getAdapter().notifyDataSetChanged();
	}

}
