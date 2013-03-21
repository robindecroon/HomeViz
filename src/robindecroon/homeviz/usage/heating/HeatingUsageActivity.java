//package robindecroon.homeviz.usage.heating;
//
//import robindecroon.homeviz.R;
//import robindecroon.homeviz.usage.UsageFullScreenActivity;
//import robindecroon.homeviz.util.PeriodListener;
//import robindecroon.homeviz.util.ToastMessages;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class HeatingUsageActivity extends UsageFullScreenActivity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.heating_layout);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		refreshElements();
//	}
//
//	@Override
//	public void onSwypeToUp() {
//		ToastMessages.swypeDownForDetail();
//	}
//
//	@Override
//	public void onSwypeToDown() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onSwypeToLeft() {
//		super.onSwypeToLeft();
//		Intent intent = new Intent(this, HeatingUsageActivity.class);
//		startActivity(intent);
//		overridePendingTransition(R.anim.left_enter, R.anim.left_leave);
//		finish();
//	}
//
//	@Override
//	public void onSwypeToRight() {
//		super.onSwypeToRight();
//		Intent intent = new Intent(this, HeatingUsageActivity.class);
//		startActivity(intent);
//		overridePendingTransition(R.anim.right_enter, R.anim.right_leave);
//		finish();
//	}
//
//	@Override
//	protected void setLocation() {
//		final TextView usageLocation = (TextView) findViewById(R.id.heating_location);
//		usageLocation.setText(currentRoom.getName());
//	}
//
//	@Override
//	protected void setListeners() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	protected void setPeriod() {
//		final TextView usagePeriod = (TextView) findViewById(R.id.heating_period);
//		usagePeriod.setText(currentPeriod.getName(this));
//		PeriodListener periodListener = new PeriodListener(this);
//		usagePeriod.setOnClickListener(periodListener);
//		usagePeriod.setOnLongClickListener(periodListener);
//	}
//
//	@Override
//	public void refreshElements() {
//		super.refreshElements();
//		setAmounts();
//	}
//
//	private void setAmounts() {
//		LinearLayout heatingsLayout = (LinearLayout) findViewById(R.id.heatings);
//		heatingsLayout.removeAllViews();
//		TextView text = new TextView(this);
//		text.setTextColor(getResources().getColor(R.color.White));
//		text.setGravity(Gravity.CENTER);
//		text.setLayoutParams(new LinearLayout.LayoutParams(
//				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		text.setTextSize(40);
//		text.setText(getResources().getString(R.string.not_implemented_yet));
//
//		heatingsLayout.addView(text);
//	}
//
//}
