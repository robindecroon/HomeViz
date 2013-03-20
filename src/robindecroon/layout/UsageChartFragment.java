package robindecroon.layout;

import java.util.Map;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.visualization.GoogleChartTools;
import robindecroon.homeviz.visualization.GoogleChartType;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class UsageChartFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.usage_detail_layout,
				container, false);

		Room currentRoom = ((HomeVizApplication) getActivity().getApplication())
				.getCurrentRoom();
		Period currentPeriod = ((HomeVizApplication) getActivity()
				.getApplication()).getCurrentPeriod();

		WebView chart = (WebView) rootView.findViewById(R.id.usage_detail_webview);
		chart.setBackgroundColor(0x00000000);
		chart.getSettings().setJavaScriptEnabled(true);
		chart.getSettings().setUseWideViewPort(true);
		chart.getSettings().setLoadWithOverviewMode(true);

		Map<String, Amount> map = currentRoom.getPricesMap(currentPeriod);
		if (!map.isEmpty()) {
			GoogleChartType type;
			switch (getArguments().getInt(Constants.FRAGMENT_BUNDLE_TYPE)) {
			case 1:
				type = GoogleChartType.BarChart;
				break;
			case 2:
				type = GoogleChartType.ColumnChart;
				break;
			default:
				throw new IllegalStateException("Wrong google chart element: "
						+ getArguments().getInt(Constants.FRAGMENT_BUNDLE_TYPE));
			}
			String url = GoogleChartTools.getUsageViz("Usage details",
					currentPeriod, getActivity(), map, chart.getWidth(),
					chart.getHeight(), type);
			chart.loadDataWithBaseURL("x-data://base", url, "text/html",
					"UTF-8", null);
		} else {
			throw new IllegalStateException();
		}

		return rootView;
	}
	
	
	@Override
	public String toString() {
		return "Usage (chart)";
	}

}
