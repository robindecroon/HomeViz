package robindecroon.homeviz.fragments.yield;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.util.AYield;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class YieldFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.yield_layout, container,
				false);
		TextView t = (TextView) rootView.findViewById(R.id.yield_solar_text);
		ImageView i = (ImageView) rootView.findViewById(R.id.yield_image);
		Bundle args = getArguments();
		String text = null;
		HomeVizApplication app = (HomeVizApplication) getActivity()
				.getApplication();
		switch (args.getInt(Constants.YIELD_TYPE)) {
		case 0:
			i.setImageResource(R.drawable.solarpanels);
			text = prepareString(app.getSolarPanel());
			break;
		case 1:
			i.setImageResource(R.drawable.rainwater);
			text = prepareString(app.getRainWater());
			break;
		case 2:
			i.setImageResource(R.drawable.waterpump);
			text = prepareString(app.getGroundWater());
			break;
		}
		t.setText(text);

		return rootView;
	}

	public String prepareString(AYield yield) {
		Context c = getActivity();
		String newLine = "\n";
		String total = c.getResources().getString(R.string.total) + ": ";
		String today = c.getResources().getString(R.string.period_day) + ": ";
		String current = c.getResources().getString(R.string.yield_current)
				+ ": ";
		String yesterday = c.getResources()
				.getString(R.string.period_yesterday) + ": ";
		String twoDays = c.getResources().getString(
				R.string.period_two_days_ago)
				+ ": ";
		String thisWeek = c.getResources().getString(R.string.period_week)
				+ ": ";
		String lastWeek = c.getResources().getString(R.string.period_last_week)
				+ ": ";
		String thisMonth = c.getResources().getString(R.string.period_month)
				+ ": ";
		String lastMonth = c.getResources().getString(
				R.string.period_last_month)
				+ ": ";
		String thisYear = c.getResources().getString(R.string.period_year)
				+ ": ";
		String lastYear = c.getResources().getString(R.string.period_last_year)
				+ ": ";

		return total + yield.getTotal() + newLine + current
				+ yield.getCurrent() + newLine + today + yield.getToday()
				+ newLine + yesterday + yield.getYesterday() + newLine
				+ twoDays + yield.getTwoDays() + newLine + thisWeek
				+ yield.getThisWeek() + newLine + lastWeek
				+ yield.getLastWeek() + newLine + thisMonth
				+ yield.getThisMonth() + newLine + lastMonth
				+ yield.getLastMonth() + newLine + thisYear
				+ yield.getThisYear() + newLine + lastYear
				+ yield.getLastYear();
	}

}
