package robindecroon.homeviz.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PeriodListener implements OnClickListener{
	
	private Activity parentActivity;

	public PeriodListener(Activity activity) {
		this.parentActivity = activity;
	}

	@Override
	public void onClick(View v) {
		DatePickerFragment untilPicker = new DatePickerFragment();
		Bundle argsTo = new Bundle();
		argsTo.putString("title", "...Until");
		untilPicker.setArguments(argsTo);
		untilPicker.show(parentActivity.getFragmentManager(), DatePickerFragment.UNTIL);

		DatePickerFragment fromPicker = new DatePickerFragment();
		Bundle argsFrom = new Bundle();
		argsFrom.putString("title", "From...");
		fromPicker.setArguments(argsFrom);
		fromPicker.show(parentActivity.getFragmentManager(), DatePickerFragment.FROM);	}
}
