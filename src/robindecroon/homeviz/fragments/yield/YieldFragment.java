package robindecroon.homeviz.fragments.yield;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.util.SolarPanel;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class YieldFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.yield_layout,
				container, false);
		TextView t = (TextView) rootView.findViewById(R.id.yield_solar_text);
		t.setText(prepareString());

		return rootView;
	}

	public String prepareString() {
		Context c = getActivity();
		SolarPanel solarPanel = ((HomeVizApplication) c.getApplicationContext()).getSolarPanel();
		String newLine = "\n";
		String total = c.getResources().getString(R.string.total) + ": ";
		String today = c.getResources().getString(R.string.period_day) + ": ";
		String current = c.getResources().getString(R.string.yield_current) + ": ";
		String yesterday = c.getResources().getString(R.string.period_yesterday) + ": ";
		String twoDays = c.getResources().getString(R.string.period_two_days_ago)+ ": ";
		String thisWeek = c.getResources().getString(R.string.period_week)+ ": ";
		String lastWeek = c.getResources().getString(R.string.period_last_week)+ ": ";
		String thisMonth = c.getResources().getString(R.string.period_month)+ ": ";
		String lastMonth = c.getResources().getString(R.string.period_last_month)+ ": ";
		String thisYear = c.getResources().getString(R.string.period_year)+ ": ";
		String lastYear = c.getResources().getString(R.string.period_last_year)+ ": ";
		
		return 
				total + solarPanel.getTotal() + newLine +
				current + solarPanel.getCurrent() + newLine +
				today + solarPanel.getToday() + newLine +
				yesterday + solarPanel.getYesterday() + newLine +
				twoDays + solarPanel.getTwoDays() + newLine +
				thisWeek + solarPanel.getThisWeek() + newLine +
				lastWeek + solarPanel.getLastWeek() + newLine +
				thisMonth + solarPanel.getThisMonth() + newLine +
				lastMonth + solarPanel.getLastMonth() + newLine +
				thisYear + solarPanel.getThisYear() + newLine +
				lastYear + solarPanel.getLastYear();
	}

}
