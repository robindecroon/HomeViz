/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments.yield;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.yield.AYield;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The Class YieldFragment.
 */
public class YieldFragment extends Fragment {
	
	/** The fragment view. */
	private View fragmentView;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.yield_layout, container,
				false);
		ImageView i = (ImageView) fragmentView.findViewById(R.id.yield_image);
		HomeVizApplication app = (HomeVizApplication) getActivity().getApplication();
		switch (getArguments().getInt(Constants.YIELD_TYPE)) {
		case 0:
			i.setImageResource(R.drawable.solarpanels);
			setValues(app.getSolarPanel());
			break;
		case 1:
			i.setImageResource(R.drawable.rainwater);
			setValues(app.getRainWater());
			break;
		case 2:
			i.setImageResource(R.drawable.waterpump);
			setValues(app.getGroundWater());
			break;
		}

		return fragmentView;
	}

	/**
	 * Sets the values.
	 *
	 * @param yield the new values
	 */
	private void setValues(AYield yield) {		
		TextView total = (TextView) fragmentView.findViewById(R.id.yield_total);
		total.setText(yield.getTotal());
		
		TextView current = (TextView) fragmentView.findViewById(R.id.yield_current);
		current.setText(yield.getCurrent());
		
		TextView today = (TextView) fragmentView.findViewById(R.id.yield_today);
		today.setText(yield.getToday());
		
		TextView yesterday = (TextView) fragmentView.findViewById(R.id.yield_yesterday);
		yesterday.setText(yield.getYesterday());
		
		TextView two_days = (TextView) fragmentView.findViewById(R.id.yield_two_days_ago);
		two_days.setText(yield.getTwoDays());
		
		TextView week = (TextView) fragmentView.findViewById(R.id.yield_week);
		week.setText(yield.getThisWeek());
		
		TextView lastWeek = (TextView) fragmentView.findViewById(R.id.yield_last_week);
		lastWeek.setText(yield.getLastWeek());
		
		TextView month = (TextView) fragmentView.findViewById(R.id.yield_month);
		month.setText(yield.getThisMonth());
		
		TextView lastMonth = (TextView) fragmentView.findViewById(R.id.yield_last_month);
		lastMonth.setText(yield.getLastMonth());
		
		TextView year = (TextView) fragmentView.findViewById(R.id.yield_year);
		year.setText(yield.getThisYear());
		
		TextView lastYear = (TextView) fragmentView.findViewById(R.id.yield_last_year);
		lastYear.setText(yield.getLastYear());
	}

}
