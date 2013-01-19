package robindecroon.homeviz.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	
	public final static String FROM = "From";
	public final static String UNTIL = "Until";
	
	private Set<DatePickerListener> listeners = new HashSet<DatePickerListener>();

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
		
		try {
			addListener((DatePickerListener) getActivity());
		} catch (ClassCastException e1) {
			Log.e("DatePickerFragment", getActivity() + " is not an appropriate listener");
		}
		
		String title = null;
		try {
			title = getArguments().getString("title");
			dialog.setTitle(title);
			
		} catch (Exception e) {
			Log.e("DialogPickerFragment","No arguments");
		}
		
		return dialog;
		
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		updateListeners(new GregorianCalendar(year, month, day));
	}
	
	private void updateListeners(GregorianCalendar gregorianCalendar) {
		for(DatePickerListener listener : listeners ) {
			listener.update(gregorianCalendar, getTag());
		}
	}
	
	public void addListener(DatePickerListener listener) {
		this.listeners.add(listener);
	}
}