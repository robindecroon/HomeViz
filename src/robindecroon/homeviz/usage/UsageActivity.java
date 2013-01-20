package robindecroon.homeviz.usage;

import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.ClickListener;
import robindecroon.homeviz.listeners.TouchListener;
import robindecroon.homeviz.usage.light.LightUsageActivity;
import robindecroon.homeviz.util.PeriodListener;
import robindecroon.homeviz.util.SystemUiHider;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.util.views.MyLinearLayout;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class UsageActivity extends UsageFullScreenActivity implements ActionBar.TabListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usage_layout);
		refreshElements();
	}

	private void setAmounts() {
		final TextView light = (TextView) findViewById(R.id.usage_light_price);
		light.setText(currentRoom.getLightPrice(currentPeriod).toString());

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
		usagePeriod.setOnClickListener(new PeriodListener(this));
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
		ToastMessages.swypeDownForDetail();
	}

	@Override
	public void onSwypeToDown() {
		Intent intent = new Intent(this, UsageDetailActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.up_enter, R.anim.up_leave);
		finish();
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

	@Override
	protected void setListeners() {
		TouchListener listener = new TouchListener(this);
		final MyLinearLayout lightLayout = (MyLinearLayout) findViewById(R.id.light_layout);
		lightLayout.setListener(listener);
		lightLayout.setOnClickListener(new ClickListener(this,
				LightUsageActivity.class));
		final MyLinearLayout waterLayout = (MyLinearLayout) findViewById(R.id.water_layout);
		waterLayout.setListener(listener);
		// waterLayout.setOnClickListener(new ClickListener(this,
		// WaterUsageActivity.class));
		final MyLinearLayout heatingLayout = (MyLinearLayout) findViewById(R.id.heating_layout);
		heatingLayout.setListener(listener);
		// heatingLayout.setOnClickListener(new ClickListener(this,
		// HeatingUsageActivity.class));
		final MyLinearLayout appliancesLayout = (MyLinearLayout) findViewById(R.id.appliances_layout);
		appliancesLayout.setListener(listener);
		// appliciancesLayout.setOnClickListener(new ClickListener(this,
		// AppliancesUsageActivity.class));
		final MyLinearLayout homeCinemaLayout = (MyLinearLayout) findViewById(R.id.home_cinema_layout);
		homeCinemaLayout.setListener(listener);
		// homeCinemaLayout.setOnClickListener(new ClickListener(this,
		// HomeCineamUsageActivity.class));
	}
	
}
