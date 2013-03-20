package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.layout.YouFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class TotalActionBarSpinnerListener implements
		AdapterView.OnItemSelectedListener {
	
	private FragmentActivity context;
	
	public TotalActionBarSpinnerListener(FragmentActivity context) {
		this.context = context;
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {
		((TextView) parentView.getChildAt(0))
				.setTextColor(Constants.SPINNER_TEXT_COLOR);
		if (position == 0) {
			Fragment fragment = new YouFragment();
			context.getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		} else if (position == 1) {

		} 
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

}
