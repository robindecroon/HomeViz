package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class YieldActionBarSpinnerListener implements
		AdapterView.OnItemSelectedListener {

	private FragmentActivity context;

	public YieldActionBarSpinnerListener(FragmentActivity context) {
		this.context = context;
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {
		((TextView) parentView.getChildAt(0))
				.setTextColor(Constants.SPINNER_TEXT_COLOR);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

}