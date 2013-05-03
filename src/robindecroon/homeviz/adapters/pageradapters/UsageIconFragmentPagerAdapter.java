package robindecroon.homeviz.adapters.pageradapters;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.fragments.usage.UsageIconFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class UsageIconFragmentPagerAdapter extends
		robindecroon.homeviz.adapters.pageradapters.HomeVizFragmentPagerAdapter {

	public UsageIconFragmentPagerAdapter(FragmentManager fm,
			HomeVizApplication app) {
		super(fm, app);
	}

	@Override
	public Fragment getItem(int arg0) {
		Bundle args = new Bundle();
		args.putInt(Constants.USAGE_ROOM, arg0);
		Fragment fragment = new UsageIconFragment();
		fragment.setArguments(args);
		return fragment;
	}

}
