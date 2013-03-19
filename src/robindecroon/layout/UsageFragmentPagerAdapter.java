package robindecroon.layout;

import robindecroon.homeviz.HomeVizApplication;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class UsageFragmentPagerAdapter extends FragmentPagerAdapter {

	private HomeVizApplication app;

	public UsageFragmentPagerAdapter(FragmentManager fm, HomeVizApplication app) {
		super(fm);
		this.app = app;
	}

	@Override
	public Fragment getItem(int arg0) {
		app.setCurrentRoom(arg0);
		return new UsageFragment();
	}

	@Override
	public int getCount() {
		return app.getRooms().size();
	}
	
    @Override
    public CharSequence getPageTitle(int position) {
        return app.getRooms().get(position).getName();
    }

}
