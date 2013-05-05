/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
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

/**
 * The listener for receiving period events.
 *
 * @see PeriodEvent
 */
public class PeriodListener implements OnClickListener, OnLongClickListener {

	/** The context. */
	private Activity context;

	/**
	 * Instantiates a new period listener.
	 *
	 * @param activity the activity
	 */
	public PeriodListener(Activity activity) {
		this.context = activity;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		ToastMessages.longClickForTime();
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View v) {
		// Display the until picker
		String until = context.getResources().getString(R.string.until);
		DatePickerFragment untilPicker = new DatePickerFragment();
		Bundle argsTo = new Bundle();
		argsTo.putString(Constants.DATEPICKER_TITLE, "..." + until);
		untilPicker.setArguments(argsTo);
		untilPicker.show(context.getFragmentManager(), until);

		// Display the from picker above the until picker!
		String from = context.getResources().getString(R.string.from);
		DatePickerFragment fromPicker = new DatePickerFragment();
		Bundle argsFrom = new Bundle();
		argsFrom.putString(Constants.DATEPICKER_TITLE, from + "...");
		fromPicker.setArguments(argsFrom);
		fromPicker.show(context.getFragmentManager(), from);

		return true;
	}
}
