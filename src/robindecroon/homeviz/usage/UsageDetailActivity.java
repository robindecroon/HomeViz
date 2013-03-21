//package robindecroon.homeviz.usage;
//
//import java.util.Map;
//
//import robindecroon.homeviz.R;
//import robindecroon.homeviz.util.Amount;
//import robindecroon.homeviz.util.ToastMessages;
//import robindecroon.homeviz.util.webviews.MyWebView;
//import android.app.ActionBar;
//import android.content.Intent;
//import android.os.Bundle;
//
//public class UsageDetailActivity extends UsageDetailFullScreenActivity
//		implements ActionBar.OnNavigationListener {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.usage_detail_layout);
//	}
//
//	@Override
//	public void onSwypeToUp() {
//		Intent intent = new Intent(this, UsageActivity.class);
//		startActivity(intent);
//		overridePendingTransition(R.anim.down_enter, R.anim.down_leave);
//	}
//
//	@Override
//	public void onSwypeToDown() {
//		ToastMessages.noMoreDetail();
//	}
//
//	@Override
//	protected void setPeriod() {
////		final TextView usagePeriod = (TextView) findViewById(R.id.usage_detail_period);
////		usagePeriod.setText(currentPeriod.getName(this));
////		PeriodListener periodListener = new PeriodListener(this);
////		usagePeriod.setOnClickListener(periodListener);
////		usagePeriod.setOnLongClickListener(periodListener);
//	}
//
//	@Override
//	protected void setLocation() {
////		final TextView usageLocation = (TextView) findViewById(R.id.usage_detail_location);
////		usageLocation.setText(currentRoom.getName());
//	}
//
//	@Override
//	protected MyWebView getWebView() {
//		return (MyWebView) findViewById(R.id.usage_detail_webview);
//	}
//
//	@Override
//	protected Map<String, Amount> getPriceMap() {
//		return currentRoom.getPricesMap(currentPeriod);
//	}
//
////	@Override
////	protected LinearLayout getBackupView() {
//////		return (LinearLayout) findViewById(R.id.usage_detail_layout);
////	}
//}
