package robindecroon.homeviz.usage;

import java.util.Map;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.TouchListener;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.views.MyWebView;
import robindecroon.homeviz.visualization.GoogleChartTools;
import robindecroon.homeviz.visualization.GoogleChartType;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public abstract class UsageDetailFullScreenActivity extends
		UsageFullScreenActivity implements ActionBar.OnNavigationListener {

	private GoogleChartType[] types = new GoogleChartType[] {
			GoogleChartType.BarChart, GoogleChartType.ColumnChart };
	private GoogleChartType currentType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentType = types[0];
		makeActionBar();
		delayedRefresh();

	}

	private void makeActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		actionBar.setDisplayHomeAsUpEnabled(true);

		actionBar.setListNavigationCallbacks(
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1, new String[] {
								types[0].toString(), types[1].toString() }),
				this);
	}

	protected abstract MyWebView getWebView();

	protected abstract Map<String, Amount> getPriceMap();
	
	@Override
	protected void setListeners() {
		MyWebView chart = getWebView();
		chart.setListener(new TouchListener(this));
	}

	@Override
	public void refreshElements() {
		super.refreshElements();
		MyWebView chart = getWebView();
		WebSettings webSettings = chart.getSettings();
		webSettings.setJavaScriptEnabled(true);

		Map<String, Amount> map = getPriceMap();

		if (!map.isEmpty()) {
			String url = GoogleChartTools.getUsageViz("Usage details",currentPeriod, this, map, chart.getWidth(),chart.getHeight(), currentType);
			chart.loadDataWithBaseURL("x-data://base", url, "text/html",
					"UTF-8", null);
		} else {
			LinearLayout layout = getBackupView();
			layout.removeAllViews();

			TextView noLights = new TextView(this);
			noLights.setText(R.string.no_lights);
			noLights.setTextSize(100);
			noLights.setTextColor(getResources().getColor(R.color.Black));
			layout.addView(noLights);

		}

	}

	protected abstract LinearLayout getBackupView();

	protected void delayedRefresh() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				refreshElements();
			}
		}, 75);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.usage_detail_layout);
		delayedRefresh();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		HomeVizApplication app = (HomeVizApplication) getApplicationContext();
		app.setType(types[itemPosition]);
		currentType=types[itemPosition];
		delayedRefresh();
		return true;
	}
}
