package robindecroon.homeviz.adapters;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.layout.UsageChartFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class UsageChartFragmentPagerAdapter extends HomeVizFragmentPagerAdapter {

	private int type;

	public UsageChartFragmentPagerAdapter(FragmentManager fm, HomeVizApplication app, int type) {
		super(fm, app);
		this.type = type;
	}

	@Override
	public Fragment getItem(int arg0) {
		app.setCurrentRoom(arg0);
		Fragment fragment = new UsageChartFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.FRAGMENT_BUNDLE_TYPE, type);
		fragment.setArguments(args);
		return fragment;
	}

}
