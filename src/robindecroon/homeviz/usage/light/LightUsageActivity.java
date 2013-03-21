//package robindecroon.homeviz.usage.light;
//
//import java.util.List;
//import java.util.Locale;
//
//import robindecroon.homeviz.LightListActivity;
//import robindecroon.homeviz.R;
//import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
//import robindecroon.homeviz.listeners.ClickListener;
//import robindecroon.homeviz.room.Light;
//import robindecroon.homeviz.usage.UsageActivityUtils;
//import robindecroon.homeviz.usage.UsageFullScreenActivity;
//import robindecroon.homeviz.util.Amount;
//import robindecroon.homeviz.util.PeriodListener;
//import robindecroon.homeviz.util.ToastMessages;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
///**
// * An example full-screen activity that shows and hides the system UI (i.e.
// * status bar and navigation/system bar) with user interaction.
// * 
// * @see SystemUiHider
// */
//public class LightUsageActivity extends UsageFullScreenActivity {
//
//	private boolean lampsPresent = true;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.light_usage_layout);
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
//		if (lampsPresent) {
//			Intent intent = new Intent(this, LightUsageDetailActivity.class);
//			startActivity(intent);
//			overridePendingTransition(R.anim.up_enter, R.anim.up_leave);
//		} else {
//			ToastMessages.noMoreDetail();
//		}
//	}
//
//	@Override
//	protected void setPeriod() {
//		final TextView usagePeriod = (TextView) findViewById(R.id.light_period);
//		usagePeriod.setText(currentPeriod.getName(this));
//		PeriodListener periodListener = new PeriodListener(this);
//		usagePeriod.setOnClickListener(periodListener);
//		usagePeriod.setOnLongClickListener(periodListener);
//	}
//
//	@Override
//	protected void setLocation() {
//		final TextView usageLocation = (TextView) findViewById(R.id.light_location);
//		usageLocation.setText(currentRoom.getName());
//	}
//
//	@Override
//	public void refreshElements() {
//		super.refreshElements();
//		setAmounts();
//	}
//
//	private void setAmounts() {
//		LinearLayout lightsLayout = (LinearLayout) findViewById(R.id.lights);
//		lightsLayout.removeAllViews();
//		Amount sum = new Amount(0);
//		try {
//			List<Light> lights = currentRoom.getLights();
//
//			for (int i = 0; i < lights.size(); i++) {
//
//				Light light = lights.get(i);
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
//				String imagename = light.getName().toLowerCase(Locale.US);
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
//				Amount price = light.getPrice(currentPeriod);
//				sum = sum.add(price);
//				text.setText(price.toString());
//
//				layout.addView(text);
//
//				layout.setClickable(true);
//				layout.setOnClickListener(new ClickListener(this,
//						LightListActivity.class));
//
//				lightsLayout.addView(layout);
//			}
//			TextView amount = (TextView) findViewById(R.id.light_amount);
//			amount.setText(sum.toString());
//		} catch (NoSuchDevicesInRoom e) {
//			View noLights = UsageActivityUtils.getEmptyRoomLights(this);
//			lightsLayout.addView(noLights);
//
//			lampsPresent = false;
//
//		}
//
//	}
//
//	@Override
//	protected void setListeners() {
//	}
//
//}
