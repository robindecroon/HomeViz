package robindecroon.homeviz.usage;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class UsageActivity extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usage_layout);

		refreshElements();
		
		setAmounts();
	}

	private void setAmounts() {
		final TextView light = (TextView) findViewById(R.id.usage_light_price);
		light.setText(currentRoom.getLight(currentPeriod).toString());
		final TextView water = (TextView) findViewById(R.id.usage_water_price);
		water.setText(currentRoom.getWater(currentPeriod).toString());
		final TextView heating = (TextView) findViewById(R.id.usage_heating_price);
		heating.setText(currentRoom.getHeating(currentPeriod).toString());
		final TextView appl = (TextView) findViewById(R.id.usage_appliances_price);
		appl.setText(currentRoom.getAppliances(currentPeriod).toString());
		final TextView tv = (TextView) findViewById(R.id.usage_tv_price);
		tv.setText(currentRoom.getTv(currentPeriod).toString());
		setTotalAmount();
		
	}

	private void setTotalAmount() {
		final TextView usageAmount = (TextView) findViewById(R.id.usage_amount);
		usageAmount.setText(currentRoom.getTotalPrice(currentPeriod));
	}
	
	@Override
	public void setPeriod() {
		final TextView usagePeriod = (TextView) findViewById(R.id.usage_period);
		usagePeriod.setText(currentPeriod.getName(this));
	}

	@Override
	public void onSwypeToLeft() {
		super.onSwypeToLeft();
		Intent intent = new Intent(this, UsageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_enter, R.anim.left_leave);
		finish();
	}

	@Override
	public void onSwypeToRight() {
		super.onSwypeToRight();
		Intent intent = new Intent(this, UsageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.right_enter, R.anim.right_leave);
		finish();
	}

	@Override
	public void onSwypeToUp() {
		System.out.println("Swypt up");
	}

	@Override
	public void onSwypeToDown() {
		Intent intent = new Intent(this, UsageDetailActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.up_enter, R.anim.up_leave);
	}

	@Override
	public void refreshElements() {
		super.refreshElements();
		setAmounts();
	}

	@Override
	protected void setLocation() {
		final TextView usageLocation = (TextView) findViewById(R.id.usage_location);
		usageLocation.setText(currentRoom.getName());	
	}
}
