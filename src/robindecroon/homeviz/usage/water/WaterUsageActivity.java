//package robindecroon.homeviz.usage.water;
//
//import java.util.List;
//import java.util.Locale;
//
//import robindecroon.homeviz.R;
//import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
//import robindecroon.homeviz.room.Water;
//import robindecroon.homeviz.usage.UsageActivityUtils;
//import robindecroon.homeviz.usage.UsageFullScreenActivity;
//import robindecroon.homeviz.util.Amount;
//import robindecroon.homeviz.util.PeriodListener;
//import robindecroon.homeviz.util.ToastMessages;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.NavUtils;
//import android.view.Gravity;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class WaterUsageActivity extends UsageFullScreenActivity {
//
//	private boolean waterPresent;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.water_usage_layout);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		refreshElements();
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.water_usage_layout, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			// This ID represents the Home or Up button. In the case of this
//			// activity, the Up button is shown. Use NavUtils to allow users
//			// to navigate up one level in the application structure. For
//			// more details, see the Navigation pattern on Android Design:
//			//
//			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
//			//
//			NavUtils.navigateUpFromSameTask(this);
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	@Override
//	public void onSwypeToUp() {
//		ToastMessages.swypeDownForDetail();
//	}
//
//	@Override
//	public void onSwypeToDown() {
//		// TODO
//		// if (waterPresent) {
//		// Intent intent = new Intent(this, WaterUsageDetailActivity.class);
//		// startActivity(intent);
//		// overridePendingTransition(R.anim.up_enter, R.anim.up_leave);
//		// } else {
//		// ToastMessages.noMoreDetail();
//		// }
//	}
//
//	@Override
//	public void onSwypeToLeft() {
//		super.onSwypeToLeft();
//		Intent intent = new Intent(this, WaterUsageActivity.class);
//		startActivity(intent);
//		overridePendingTransition(R.anim.left_enter, R.anim.left_leave);
//		finish();
//	}
//
//	@Override
//	public void onSwypeToRight() {
//		super.onSwypeToRight();
//		Intent intent = new Intent(this, WaterUsageActivity.class);
//		startActivity(intent);
//		overridePendingTransition(R.anim.right_enter, R.anim.right_leave);
//		finish();
//	}
//
//	@Override
//	protected void setLocation() {
//		final TextView usageLocation = (TextView) findViewById(R.id.water_location);
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
//		final TextView usagePeriod = (TextView) findViewById(R.id.water_period);
//		usagePeriod.setText(currentPeriod.getName(this));
//		usagePeriod.setOnClickListener(new PeriodListener(this));
//	}
//
//	@Override
//	public void refreshElements() {
//		super.refreshElements();
//		setAmounts();
//	}
//
//	private void setAmounts() {
//		LinearLayout waterLayout = (LinearLayout) findViewById(R.id.waters);
//		waterLayout.removeAllViews();
//		Amount sum = new Amount(0);
//		try {
//			List<Water> waters = currentRoom.getWaters();
//
//			for (int i = 0; i < waters.size(); i++) {
//
//				Water water = waters.get(i);
//				LinearLayout layout = new LinearLayout(this);
//				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
//				layout.setLayoutParams(lp);
//				layout.setOrientation(LinearLayout.VERTICAL);
//
//				ImageView image = new ImageView(this);
//				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
//						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//				lp2.gravity = Gravity.CENTER;
//				image.setAdjustViewBounds(true);
//				image.setLayoutParams(lp2);
//
//				String imagename = water.getName().toLowerCase(Locale.US);
//				int picId = getResources().getIdentifier(imagename, "drawable",
//						getPackageName());
//				image.setImageResource(picId);
//
//				layout.addView(image);
//
//				TextView text = new TextView(this);
//				text.setTextColor(getResources().getColor(R.color.White));
//				text.setGravity(Gravity.CENTER);
//				text.setLayoutParams(new LinearLayout.LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//				text.setTextSize(40);
//				Amount price = water.getPrice(currentPeriod);
//				sum = sum.add(price);
//				text.setText(price.toString());
//
//				layout.addView(text);
//
//				waterLayout.addView(layout);
//			}
//			TextView amount = (TextView) findViewById(R.id.water_amount);
//			amount.setText(sum.toString());
//		} catch (NoSuchDevicesInRoom e) {
//			View noWaters = UsageActivityUtils.getEmptyRoomWater(this);
//			waterLayout.addView(noWaters);
//			waterPresent = false;
//		}
//	}
//
//}
