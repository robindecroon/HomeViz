package robindecroon.homeviz.usage;

import robindecroon.homeviz.R;
import robindecroon.homeviz.util.FullScreenActivity;
import robindecroon.homeviz.util.GoogleChartTools;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.util.TouchListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class UsageDetailActivity extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usage_detail_layout);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		
		refreshElements();
	}
	
	@Override
	 public void onWindowFocusChanged(boolean hasFocus) {
	  // TODO Auto-generated method stub
	  super.onWindowFocusChanged(hasFocus);
	  
	  WebView chart = (WebView) findViewById(R.id.usage_detail_webview);
		chart.setOnTouchListener(new TouchListener(this));
		WebSettings webSettings = chart.getSettings();
		webSettings.setJavaScriptEnabled(true);

		String url = GoogleChartTools
				.getUsageViz(
						"Usage details",
						currentPeriod,
						this,
						new String[] {
								""
										+ currentRoom.getLight(currentPeriod)
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usage_detail_layout, menu);
		return true;
	}

	@Override
	public void onZoomIn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onZoomOut() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSwypeToLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSwypeToRight() {
		// TODO Auto-generated method stub

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

	// @Override
	// public void refreshElements() {
	// // TODO Auto-generated method stub
	//
	// }

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
