package robindecroon.homeviz.usage;

import java.util.Map;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.TouchListener;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.webviews.MyJavaScriptInterface;
import robindecroon.homeviz.util.webviews.MyWebView;
import robindecroon.homeviz.util.webviews.MyWebViewClient;
import robindecroon.homeviz.visualization.GoogleChartType;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
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
		Map<String, Amount> map = getPriceMap();
		
		String data = makeData(currentPeriod, this, map);
		
		final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(
				this);
		chart.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");

		MyWebViewClient client = new MyWebViewClient(chart, "ChartToolsClient");
		client.setString(data);
		chart.setWebViewClient(client);

		chart.getSettings().setJavaScriptEnabled(true);
		chart.getSettings().setUseWideViewPort(true);
		chart.getSettings().setLoadWithOverviewMode(true);


		if (!map.isEmpty()) {
//			String url = GoogleChartTools.getUsageViz("Usage details",currentPeriod, this, map, chart.getWidth(),chart.getHeight(), currentType);
//			chart.loadDataWithBaseURL("x-data://base", url, "text/html",
//					"UTF-8", null);
			chart.loadUrl("file:///android_asset/www/charttools.html");
			
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
	
	private static String makeData(Period currentPeriod, Context context,
			Map<String, Amount> map) {
		String data = "[['Period',";

		String[] values = new String[map.size()];
		int i = 0;
		for (String key : map.keySet()) {
			data += "'" + key + "',";
			values[i] = Double.toString(map.get(key).getEuroValue());
			i++;
		}
		data = data.substring(0, data.length() - 1);
		data += "],['" + currentPeriod.getName(context) + "', ";

		for (String value : values) {
			data += value + ",";
		}
		data = data.substring(0, data.length() - 1);
		data += "]]";
		return data;
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
