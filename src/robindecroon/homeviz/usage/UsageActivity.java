package robindecroon.homeviz.usage;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.util.FullScreenActivity;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.SystemUiHider;
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

		setTotalAmount();
		setPeriod();

		final TextView usageLocation = (TextView) findViewById(R.id.usage_location);
		usageLocation.setText(currentRoom.getName());
	}

	private void setTotalAmount() {
		final TextView usageAmount = (TextView) findViewById(R.id.usage_amount);
		usageAmount.setText(currentRoom.getTotalPrice(currentPeriod));
	}

	private void setPeriod() {
		final TextView usagePeriod = (TextView) findViewById(R.id.usage_period);
		usagePeriod.setText(currentPeriod.getName(this));
	}

	@Override
	public void onSwypeToLeft() {
		currentRoom = ((HomeVizApplication) getApplication()).previousRoom();
		Intent intent = new Intent(this, UsageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_enter, R.anim.left_leave);
		finish();
	}

	@Override
	public void onSwypeToRight() {
		currentRoom = ((HomeVizApplication) getApplication()).nextRoom();
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
		System.out.println("Swypt down");
	}

	@Override
	public void onZoomIn() {
		zoomEvent();
	}

	@Override
	public void onZoomOut() {
		zoomEvent();
	}

	private void zoomEvent() {
		setTotalAmount();
		setPeriod();
	}
}
