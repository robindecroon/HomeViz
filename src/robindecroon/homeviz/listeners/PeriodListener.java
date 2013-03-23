package robindecroon.homeviz.listeners;

import robindecroon.fragments.DatePickerFragment;
import robindecroon.homeviz.util.ToastMessages;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class PeriodListener implements OnClickListener, OnLongClickListener {

	private Activity parentActivity;

	public PeriodListener(Activity activity) {
		this.parentActivity = activity;
	}

	@Override
	public void onClick(View v) {
		ToastMessages.longClickForTime();
	}

	@Override
	public boolean onLongClick(View v) {
		DatePickerFragment untilPicker = new DatePickerFragment();
		Bundle argsTo = new Bundle();
		argsTo.putString("title", "...Until");
		untilPicker.setArguments(argsTo);
		untilPicker.show(parentActivity.getFragmentManager(),
				DatePickerFragment.UNTIL);

		DatePickerFragment fromPicker = new DatePickerFragment();
		Bundle argsFrom = new Bundle();
		argsFrom.putString("title", "From...");
		fromPicker.setArguments(argsFrom);
		fromPicker.show(parentActivity.getFragmentManager(),
				DatePickerFragment.FROM);

		return true;
	}
}
