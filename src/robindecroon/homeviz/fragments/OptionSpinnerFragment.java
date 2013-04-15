package robindecroon.homeviz.fragments;

import libraries.nielsbillen.ArrowButton;
import libraries.nielsbillen.OptionSpinner;
import libraries.nielsbillen.SpinnerListener;
import robindecroon.homeviz.Main;
import robindecroon.homeviz.listeners.PeriodListener;
import robindecroon.homeviz.util.FragmentResetter;
import robindecroon.homeviz.util.Period;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class OptionSpinnerFragment extends Fragment implements
		SpinnerListener {

	/**
	 * Initialiseer de spinner.
	 */
	protected void initOptionSpinner(View v, int id, int leftId, int rightId) {
		ArrowButton left = (ArrowButton) v.findViewById(leftId);
		left.setArrowDirection(ArrowButton.DIRECTION_LEFT);
		ArrowButton right = (ArrowButton) v.findViewById(rightId);
		right.setArrowDirection(ArrowButton.DIRECTION_RIGHT);
		PeriodListener periodListener = new PeriodListener(getActivity());
		OptionSpinner spinner = (OptionSpinner) v.findViewById(id);
		spinner.setIndex(Main.currentPeriod.getId());
		spinner.setLeftButton(left);
		spinner.setRightButton(right);
		spinner.setOnClickListener(periodListener);
		spinner.setOnLongClickListener(periodListener);
		Period[] periods = Period.values();
		String[] namePeriods = new String[periods.length];
		for (int i = 0; i < periods.length; i++) {
			Period period = periods[i];
				namePeriods[i] = period.getName(getActivity());
		}
		spinner.setOptions(namePeriods);
		spinner.addListener(this);
	}

	@Override
	public void optionChanged(final int index, String name) {
		Main.currentPeriod = Period.getPeriod(index);
		FragmentResetter.reset(getActivity());
	}

}
