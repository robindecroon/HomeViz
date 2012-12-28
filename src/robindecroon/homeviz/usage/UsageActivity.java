package robindecroon.homeviz.usage;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.util.FullScreenActivity;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.SystemUiHider;
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
		
		final TextView usageAmount = (TextView) findViewById(R.id.usage_amount);
		final TextView usageLocation = (TextView) findViewById(R.id.usage_location);
		final TextView usagePeriod = (TextView) findViewById(R.id.usage_period);

		usageAmount.setText("€37");
		usageLocation.setText("Home");
		Period period = ((HomeVizApplication) getApplicationContext())
				.getCurrentPeriod();
		String periodText = period.getName(this);
		usagePeriod.setText(periodText);
	}
}
