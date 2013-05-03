package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.homeviz.fragments.DatePickerFragment;
import robindecroon.homeviz.util.ToastMessages;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class PeriodListener implements OnClickListener, OnLongClickListener {

	private Activity context;

	public PeriodListener(Activity activity) {
		this.context = activity;
	}

	@Override
	public void onClick(View v) {
		ToastMessages.longClickForTime();
	}

	@Override
	public boolean onLongClick(View v) {
		String from = context.getResources().getString(R.string.from);
		String until = context.getResources().getString(R.string.until);

		DatePickerFragment untilPicker = new DatePickerFragment();
		Bundle argsTo = new Bundle();
		argsTo.putString(Constants.DATEPICKER_TITLE, "..." + until);
		untilPicker.setArguments(argsTo);
		untilPicker.show(context.getFragmentManager(), until);

		DatePickerFragment fromPicker = new DatePickerFragment();
		Bundle argsFrom = new Bundle();
		argsFrom.putString(Constants.DATEPICKER_TITLE, from + "...");
		fromPicker.setArguments(argsFrom);
		fromPicker.show(context.getFragmentManager(), from);

		return true;
	}
}
