package robindecroon.homeviz.fragments.usage;

import java.util.Map;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.Main;
import robindecroon.homeviz.R;
import robindecroon.homeviz.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Network;
import robindecroon.homeviz.util.UsageActivityUtils;
import robindecroon.homeviz.visualization.GoogleChartTools;
import robindecroon.homeviz.visualization.GoogleChartType;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.LinearLayout;

@SuppressLint("SetJavaScriptEnabled")
public class UsageChartFragment extends OptionSpinnerFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.usage_detail_layout,
				container, false);
		LinearLayout content = (LinearLayout) rootView
				.findViewById(R.id.detail_container);

		initOptionSpinner(rootView, R.id.chart_spinner, R.id.chart_arraw_left,
				R.id.chart_arrow_right);

		boolean online = Network.isNetworkConnected(getActivity());

		if (online) {
			HomeVizApplication app = (HomeVizApplication) getActivity()
					.getApplication();

			Room currentRoom = null;
			if (getArguments() != null) {
				int roomIndex = getArguments().getInt("room");
				currentRoom = app.getRooms().get(roomIndex);
			} else {
				throw new IllegalStateException(getClass().getSimpleName()
						+ " should have room arguments!");
			}

			WebView chart = new WebView(getActivity());
			content.addView(chart);
			chart.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			chart.setBackgroundColor(0x00000000);
			chart.getSettings().setJavaScriptEnabled(true);
			chart.getSettings().setUseWideViewPort(true);
			chart.getSettings().setLoadWithOverviewMode(true);
			chart.getSettings().setLayoutAlgorithm(
					LayoutAlgorithm.SINGLE_COLUMN);
			chart.setInitialScale(1);

			Map<String, Amount> map = currentRoom.getPricesMap();
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
					throw new IllegalStateException(
							"Wrong google chart element: "
									+ getArguments().getInt(
											Constants.FRAGMENT_BUNDLE_TYPE));
				}
				String url = GoogleChartTools.getUsageViz("Usage details",
						Main.currentPeriod, getActivity(), map,
						chart.getWidth(), chart.getHeight(), type);
				chart.loadDataWithBaseURL("x-data://base", url, "text/html",
						"UTF-8", null);

				// content.addView(chart);
			} else {
				throw new IllegalStateException();
			}
		} else {
			Log.e(getClass().getSimpleName(), "No internet connection!");
			content.addView(UsageActivityUtils
					.getNoNetworkConnection(getActivity()));
		}

		return rootView;
	}

	@Override
	public String toString() {
		return "Usage (chart)";
	}

}
