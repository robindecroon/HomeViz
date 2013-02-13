package robindecroon.homeviz.usage.appliance;

import java.util.List;
import java.util.Locale;

import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.room.Appliance;
import robindecroon.homeviz.usage.UsageActivityUtils;
import robindecroon.homeviz.usage.UsageFullScreenActivity;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.PeriodListener;
import robindecroon.homeviz.util.ToastMessages;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ApplianceUsageActivity extends UsageFullScreenActivity {

	private boolean appliancePresent;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appliance_usage_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		refreshElements();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.appliance_usage_layout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSwypeToUp() {
		ToastMessages.swypeDownForDetail();		
	}

	@Override
	public void onSwypeToDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setLocation() {
		final TextView usageLocation = (TextView) findViewById(R.id.appliance_location);
		usageLocation.setText(currentRoom.getName());		
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setPeriod() {
		final TextView usagePeriod = (TextView) findViewById(R.id.appliance_period);
		usagePeriod.setText(currentPeriod.getName(this));
		PeriodListener periodListener = new PeriodListener(this);
		usagePeriod.setOnClickListener(periodListener);
		usagePeriod.setOnLongClickListener(periodListener);
	}
	
	@Override
	public void refreshElements() {
		super.refreshElements();
		setAmounts();
	}
	
	private void setAmounts() {
		LinearLayout applianceLayout = (LinearLayout) findViewById(R.id.appliances);
		applianceLayout.removeAllViews();
		Amount sum = new Amount(0);
		try {
			List<Appliance> appliances = currentRoom.getAppliances();

			for (int i = 0; i < appliances.size(); i++) {

				Appliance appliance = appliances.get(i);
				LinearLayout layout = new LinearLayout(this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, 1);
				layout.setLayoutParams(lp);
				layout.setOrientation(LinearLayout.VERTICAL);

				ImageView image = new ImageView(this);
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				lp2.gravity = Gravity.CENTER;
				image.setAdjustViewBounds(true);
				image.setLayoutParams(lp2);

				String imagename = appliance.getName().toLowerCase(Locale.US);
				int picId = getResources().getIdentifier(imagename, "drawable",
						getPackageName());
				image.setImageResource(picId);

				layout.addView(image);

				TextView text = new TextView(this);
				text.setTextColor(getResources().getColor(R.color.White));
				text.setGravity(Gravity.CENTER);
				text.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				text.setTextSize(40);
				Amount price = appliance.getPrice(currentPeriod);
				sum = sum.add(price);
				text.setText(price.toString());

				layout.addView(text);

				applianceLayout.addView(layout);
			}
			TextView amount = (TextView) findViewById(R.id.appliance_amount);
			amount.setText(sum.toString());
		} catch (NoSuchDevicesInRoom e) {
			View noAppliances = UsageActivityUtils.getEmptyRoomAppliance(this);
			applianceLayout.addView(noAppliances);
			appliancePresent = false;
		}
	}

}
