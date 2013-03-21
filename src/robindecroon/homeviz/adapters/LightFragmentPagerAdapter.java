package robindecroon.homeviz.adapters;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.layout.UsageSubFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class LightFragmentPagerAdapter extends HomeVizFragmentPagerAdapter {

	public LightFragmentPagerAdapter(FragmentManager fm, HomeVizApplication app) {
		super(fm, app);
	}

	@Override
	public Fragment getItem(int arg0) {
		app.setCurrentRoom(arg0);
		Fragment fragment = new UsageSubFragment();
		return fragment;
	}

}
