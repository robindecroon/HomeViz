package robindecroon.homeviz.usage;

import robindecroon.homeviz.R;
import robindecroon.homeviz.util.GoogleChartTools;
import robindecroon.homeviz.util.MyWebView;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.util.TouchListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.webkit.WebSettings;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class UsageDetailActivity extends UsageFullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usage_detail_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		delayedRefresh();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  setContentView(R.layout.usage_detail_layout);
	  delayedRefresh();
	}

	private void delayedRefresh() {
		final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					refreshElements();
				}
			}, 50);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usage_detail_layout, menu);
		return true;
	}

	@Override
	public void onSwypeToLeft() {
		super.onSwypeToLeft();
		Intent intent = new Intent(this, UsageDetailActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_enter, R.anim.left_leave);
		finish();
	}

	@Override
	public void onSwypeToRight() {
		super.onSwypeToRight();
		Intent intent = new Intent(this, UsageDetailActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.right_enter, R.anim.right_leave);
		finish();
	}

	@Override
	public void onSwypeToUp() {
		Intent intent = new Intent(this, UsageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.down_enter, R.anim.down_leave);
	}

	@Override
	public void onSwypeToDown() {
		ToastMessages.noMoreDetail();
	}

	@Override
	public void refreshElements() {
		super.refreshElements();
		MyWebView chart = (MyWebView) findViewById(R.id.usage_detail_webview);
		chart.setListener(new TouchListener(this));
		WebSettings webSettings = chart.getSettings();
		webSettings.setJavaScriptEnabled(true);

		String url = GoogleChartTools
				.getUsageViz(
						"Usage details",
						currentPeriod,
						this,
						new String[] {
								""
										+ currentRoom.getLightPrice(currentPeriod)
												.getEuroValue(),
								""
										+ currentRoom.getWater(currentPeriod)
												.getEuroValue(),
								""
										+ currentRoom.getHeating(currentPeriod)
												.getEuroValue(),
								""
										+ currentRoom.getAppliances(
												currentPeriod).getEuroValue(),
								""
										+ currentRoom.getTv(currentPeriod)
												.getEuroValue() },
						chart.getWidth(), chart.getHeight());
		System.out.println("WITDT: " + chart.getWidth());
		chart.loadDataWithBaseURL("x-data://base", url, "text/html", "UTF-8",
				null);
	}

	@Override
	protected void setPeriod() {
		final TextView usagePeriod = (TextView) findViewById(R.id.usage_detail_period);
		usagePeriod.setText(currentPeriod.getName(this));
	}

	@Override
	protected void setLocation() {
		final TextView usageLocation = (TextView) findViewById(R.id.usage_detail_location);
		usageLocation.setText(currentRoom.getName());
	}

}
