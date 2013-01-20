package robindecroon.homeviz.usage.light;

import java.util.Map;

import robindecroon.homeviz.R;
import robindecroon.homeviz.usage.UsageDetailFullScreenActivity;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.PeriodListener;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.util.webviews.MyWebView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LightUsageDetailActivity extends UsageDetailFullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.light_usage_detail_layout);
	}
	
	@Override
	public void onSwypeToLeft() {
		super.onSwypeToLeft();
		Intent intent = new Intent(this, LightUsageDetailActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_enter, R.anim.left_leave);
		finish();
	}
	
	@Override
	public void onSwypeToRight() {
		super.onSwypeToRight();
		Intent intent = new Intent(this, LightUsageDetailActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.right_enter, R.anim.right_leave);
		finish();
	}

	@Override
	public void onSwypeToUp() {
		Intent intent = new Intent(this, LightUsageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.down_enter, R.anim.down_leave);
	}

	@Override
	public void onSwypeToDown() {
		ToastMessages.noMoreDetail();
	}

	@Override
	protected void setPeriod() {
		final TextView usagePeriod = (TextView) findViewById(R.id.light_usage_detail_period);
		usagePeriod.setText(currentPeriod.getName(this));
		usagePeriod.setOnClickListener(new PeriodListener(this));
	}

	@Override
	protected void setLocation() {
		final TextView usageLocation = (TextView) findViewById(R.id.light_usage_detail_location);
		usageLocation.setText(currentRoom.getName());
	}

	@Override
	protected MyWebView getWebView() {
		return (MyWebView) findViewById(R.id.light_usage_detail_webview);
	}

	@Override
	protected Map<String, Amount> getPriceMap() {
		return currentRoom.getLightsMap(currentPeriod);
	}

	@Override
	protected LinearLayout getBackupView() {
		return (LinearLayout) findViewById(R.id.light_detail_layout);
	}
}
