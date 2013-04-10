package robindecroon.homeviz.adapters.pageradapters;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.fragments.usage.UsageChartFragment;
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
		args.putInt(Constants.USAGE_ROOM, arg0);
		args.putInt(Constants.USAGE_BUNDLE_TYPE, type);
		Fragment fragment = new UsageChartFragment();
		fragment.setArguments(args);
		return fragment;
	}

}
