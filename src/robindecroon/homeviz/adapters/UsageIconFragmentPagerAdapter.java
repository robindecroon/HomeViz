package robindecroon.homeviz.adapters;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.layout.UsageFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class UsageIconFragmentPagerAdapter extends robindecroon.homeviz.adapters.HomeVizFragmentPagerAdapter {

	public UsageIconFragmentPagerAdapter(FragmentManager fm, HomeVizApplication app) {
		super(fm, app);
	}

	@Override
	public Fragment getItem(int arg0) {
		app.setCurrentRoom(arg0);
		return new UsageFragment();
	}

}
