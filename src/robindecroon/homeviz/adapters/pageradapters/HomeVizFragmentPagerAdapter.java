package robindecroon.homeviz.adapters.pageradapters;

import robindecroon.homeviz.HomeVizApplication;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public abstract class HomeVizFragmentPagerAdapter extends
		FragmentStatePagerAdapter {

	protected HomeVizApplication app;

	public HomeVizFragmentPagerAdapter(FragmentManager fm,
			HomeVizApplication app) {
		super(fm);
		this.app = app;
	}

	// TODO
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return app.getRooms().size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return app.getRooms().get(position).getName();
	}

	@Override
	public abstract Fragment getItem(int arg0);

}