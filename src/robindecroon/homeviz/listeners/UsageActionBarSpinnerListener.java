package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.layout.UsageChartFragment;
import robindecroon.layout.UsageContainerFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class UsageActionBarSpinnerListener implements
		AdapterView.OnItemSelectedListener {

	private FragmentActivity context;

	public UsageActionBarSpinnerListener(FragmentActivity context) {
		this.context = context;
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {
		((TextView) parentView.getChildAt(0))
				.setTextColor(Constants.SPINNER_TEXT_COLOR);

		Fragment fragment = new UsageContainerFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.UsageType, position);
		args.putInt(Constants.FRAGMENT_BUNDLE_TYPE, position);
		fragment.setArguments(args);
		context.getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
