package robindecroon.homeviz.adapters.pageradapters;

import robindecroon.fragments.usage.UsageChartFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class UsageChartFragmentPagerAdapter extends HomeVizFragmentPagerAdapter {

	private int type;

	public UsageChartFragmentPagerAdapter(FragmentManager fm,
			HomeVizApplication app, int type) {
		super(fm, app);
		this.type = type;
	}

	@Override
	public Fragment getItem(int arg0) {
		Bundle args = new Bundle();
		args.putInt("room", arg0);
		args.putInt(Constants.FRAGMENT_BUNDLE_TYPE, type);
		Fragment fragment = new UsageChartFragment();
		fragment.setArguments(args);
		return fragment;
	}

}
