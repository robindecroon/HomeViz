package robindecroon.homeviz.adapters;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.layout.UsageFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class UsageIconFragmentPagerAdapter extends
		robindecroon.homeviz.adapters.HomeVizFragmentPagerAdapter {

	public UsageIconFragmentPagerAdapter(FragmentManager fm,
			HomeVizApplication app) {
		super(fm, app);
	}

	@Override
	public Fragment getItem(int arg0) {
		Bundle args = new Bundle();
		args.putInt("room", arg0);
		Fragment fragment = new UsageFragment();
		fragment.setArguments(args);
		return fragment;
	}

}