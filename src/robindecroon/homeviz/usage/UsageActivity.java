package robindecroon.homeviz.usage;

import robindecroon.homeviz.FullScreenActivity;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.Period;
import robindecroon.homeviz.R;
import robindecroon.homeviz.util.SystemUiHider;
import android.os.Bundle;
import android.view.Menu;
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
		
		final TextView usageAmount = (TextView) findViewById(R.id.usage_amount);
		final TextView usageLocation = (TextView) findViewById(R.id.usage_location);
		final TextView usagePeriod = (TextView) findViewById(R.id.usage_period);

		usageAmount.setText("€37");
		usageLocation.setText("Home");
		Period period = ((HomeVizApplication) getApplicationContext())
				.getCurrentPeriod();
		String periodText = period.getCurrentName(this);
		usagePeriod.setText(periodText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "Settings");
		return true;
	}
}
