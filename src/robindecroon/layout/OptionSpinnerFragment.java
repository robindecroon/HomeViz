package robindecroon.layout;

import libraries.nielsbillen.ArrowButton;
import libraries.nielsbillen.OptionSpinner;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.PeriodListener;
import android.support.v4.app.Fragment;
import android.view.View;

public class OptionSpinnerFragment extends Fragment {

	/**
	 * Initialiseert de spinner.
	 */
	protected void initSpinner(View v, int id, int leftId, int rightId) {
		ArrowButton left = (ArrowButton) v.findViewById(leftId);
		left.setArrowDirection(ArrowButton.DIRECTION_LEFT);
		ArrowButton right = (ArrowButton) v.findViewById(rightId);
		right.setArrowDirection(ArrowButton.DIRECTION_RIGHT);
		PeriodListener periodListener = new PeriodListener(getActivity());
		OptionSpinner spinner = (OptionSpinner) v.findViewById(id);
		spinner.setLeftButton(left);
		spinner.setRightButton(right);
		spinner.setOnClickListener(periodListener);
		spinner.setOnLongClickListener(periodListener);
		Period[] periods = Period.values();
		String[] namePeriods = new String[periods.length - 1];
		for (int i = 0; i < periods.length; i++) {
			Period period = periods[i];
			if (period != Period.CUSTOM)
				namePeriods[i] = period.getName(getActivity());
		}
		spinner.setOptions(namePeriods);
	}

}
