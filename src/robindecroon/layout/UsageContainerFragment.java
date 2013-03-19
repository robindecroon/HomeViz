package robindecroon.layout;

import com.viewpagerindicator.TabPageIndicator;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UsageContainerFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.simple_tabs, container, false);
		
		FragmentPagerAdapter adapter = new UsageFragmentPagerAdapter(getActivity().getSupportFragmentManager(), (HomeVizApplication) getActivity().getApplication());

        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) rootView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
		
		return rootView;
	}

}
