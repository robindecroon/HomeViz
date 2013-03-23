package robindecroon.fragments.usage;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.adapters.pageradapters.ConsumerFragmentPagerAdapter;
import robindecroon.homeviz.adapters.pageradapters.UsageChartFragmentPagerAdapter;
import robindecroon.homeviz.adapters.pageradapters.UsageIconFragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

public class UsageContainerFragment extends Fragment {

	public static int currentPageNumber;

	private static ViewPager pager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.simple_tabs, container, false);

		FragmentActivity activity = getActivity();
		HomeVizApplication app = (HomeVizApplication) activity.getApplication();
		FragmentManager manager = activity.getSupportFragmentManager();
		FragmentStatePagerAdapter adapter = null;
		Bundle args = getArguments();
		if (args != null) {
			currentPageNumber = args.getInt("room");
			switch (args.getInt(Constants.USAGE_TYPE)) {
			case 1:
				adapter = new UsageChartFragmentPagerAdapter(manager, app, 1);
				break;
			case 2:
				adapter = new UsageChartFragmentPagerAdapter(manager, app, 2);
				break;
			case 11:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 11);
				break;
			case 12:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 12);
				break;
			case 13:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 13);
				break;
			case 14:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 14);
				break;
			case 15:
				adapter = new ConsumerFragmentPagerAdapter(manager, app, 15);
				break;
			default:
				adapter = new UsageIconFragmentPagerAdapter(manager, app);
				break;
			}
		} else {
			Log.e(getClass().getSimpleName(), "No arguments were supplied!");
			adapter = new UsageIconFragmentPagerAdapter(manager, app);
		}
		pager = (ViewPager) rootView.findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) rootView
				.findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		indicator.setCurrentItem(currentPageNumber);

		return rootView;
	}

	public static int getCurrentSelection() {
		if (pager != null)
			return pager.getCurrentItem();
		else
			return 0;
	}

	public static void resetViews() {
		pager.getAdapter().notifyDataSetChanged();
	}

}
