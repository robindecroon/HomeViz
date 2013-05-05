/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.homeviz.activities.MainActivity;
import robindecroon.homeviz.util.FragmentResetter;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.ToastMessages;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

/**
 * The Class DatePickerFragment.
 */
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	/**
	 * The Enum DatePickerFragmentType.
	 */
	private enum DatePickerFragmentType { From, Until; }

	/** The date picker fragment type. */
	private DatePickerFragmentType datePickerFragmentType;

	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		Activity context = getActivity();
		String title = getArguments().getString(Constants.DATEPICKER_TITLE);
		Resources res = context.getResources();
		if (title.equals(res.getString(R.string.from) + "...")) {
			datePickerFragmentType = DatePickerFragmentType.From;
		} else if (title.equals("..." + res.getString(R.string.until))) {
			datePickerFragmentType = DatePickerFragmentType.Until;
		}

		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dialog = new DatePickerDialog(context, this, year, month, day);
		switch (datePickerFragmentType) {
		case From:
			dialog.getDatePicker().setMaxDate(new Date().getTime());
			break;
		case Until:
			dialog.getDatePicker().setMaxDate(new Date().getTime());
			break;
		}

		dialog.setTitle(title);

		return dialog;

	}

	/* (non-Javadoc)
	 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		GregorianCalendar cal = new GregorianCalendar(year, month, day);
		try {
			MainActivity.currentPeriod = Period.CUSTOM;
			switch (datePickerFragmentType) {
			case From:
				Log.i(getClass().getSimpleName(), "From date set!");
				MainActivity.currentPeriod.setBegin(cal);
				break;
			case Until:
				Log.i(getClass().getSimpleName(), "End date set!");
				Period p = MainActivity.currentPeriod;
				p.setEnd(cal);
				if (p.getEnd().before(p.getBegin())) {
					Log.e(getClass().getSimpleName(), "Invalid date range!");
					ToastMessages.invalidPeriod();
					MainActivity.currentPeriod = Period.WEEK;
				}
				try {
					FragmentResetter.reset(getActivity());
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
		}

	}
}