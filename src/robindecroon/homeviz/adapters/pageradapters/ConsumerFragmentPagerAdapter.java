package robindecroon.homeviz.adapters.pageradapters;

import robindecroon.fragments.usage.UsageConsumerFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ConsumerFragmentPagerAdapter extends HomeVizFragmentPagerAdapter {

	private int consumer;

	public ConsumerFragmentPagerAdapter(FragmentManager fm,
			HomeVizApplication app, int consumer) {
		super(fm, app);
		this.consumer = consumer;
	}

	@Override
	public Fragment getItem(int arg0) {
		Bundle args = new Bundle();
		args.putInt("room", arg0);
		args.putInt(Constants.USAGE_TYPE, consumer);
		Fragment fragment = new UsageConsumerFragment();
		fragment.setArguments(args);
		return fragment;
	}

}