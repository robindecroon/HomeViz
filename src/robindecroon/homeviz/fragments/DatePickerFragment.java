package robindecroon.homeviz.fragments;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.Main;
import robindecroon.homeviz.R;
import robindecroon.homeviz.util.FragmentResetter;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.ToastMessages;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	
	private enum Type {
		From, Until;
	}

	private Type type;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		String title = getArguments().getString(Constants.DATEPICKER_TITLE);
		Resources res = getActivity().getResources();
		if (title.equals(res.getString(R.string.from) + "...")) {
			type = Type.From;
		} else if (title.equals("..." + res.getString(R.string.until))) {
			type = Type.Until;
		}

		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dialog = new DatePickerDialog(getActivity(), this,
				year, month, day);
		switch (type) {
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

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		GregorianCalendar cal = new GregorianCalendar(year, month, day);
		try {
			Main.currentPeriod = Period.CUSTOM;
			switch (type) {
			case From:
				Log.i(getClass().getSimpleName(), "From date set!");
				Main.currentPeriod.setBegin(cal);
				break;
			case Until:
				Log.i(getClass().getSimpleName(), "End date set!");
				Period p = Main.currentPeriod;
				p.setEnd(cal);
				if(p.getEnd().before(p.getBegin())) {
					Log.e(getClass().getSimpleName(), "Invalid date range!");
					ToastMessages.invalidPeriod();
					Main.currentPeriod = Period.WEEK;
				}
				try {
					FragmentResetter.reset(getActivity());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
		}

	}
}