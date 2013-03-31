package robindecroon.homeviz.fragments;

import libraries.nielsbillen.ArrowButton;
import libraries.nielsbillen.OptionSpinner;
import libraries.nielsbillen.SpinnerListener;
import robindecroon.homeviz.Main;
import robindecroon.homeviz.fragments.metaphor.MetaphorContainerFragment;
import robindecroon.homeviz.fragments.total.TotalTreeMapFragment;
import robindecroon.homeviz.fragments.usage.UsageContainerFragment;
import robindecroon.homeviz.listeners.PeriodListener;
import robindecroon.homeviz.util.Period;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public abstract class OptionSpinnerFragment extends Fragment implements
		SpinnerListener {

	/**
	 * Initialiseert de spinner.
	 */
	protected void initOptionSpinner(View v, int id, int leftId, int rightId) {
		ArrowButton left = (ArrowButton) v.findViewById(leftId);
		left.setArrowDirection(ArrowButton.DIRECTION_LEFT);
		ArrowButton right = (ArrowButton) v.findViewById(rightId);
		right.setArrowDirection(ArrowButton.DIRECTION_RIGHT);
		PeriodListener periodListener = new PeriodListener(getActivity());
		OptionSpinner spinner = (OptionSpinner) v.findViewById(id);
		// spinner.setIndex(((HomeVizApplication)
		// getActivity().getApplication())
		// .getCurrentPeriod().getId());
		spinner.setIndex(Main.currentPeriod.getId());
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
		spinner.addListener(this);
	}

	@Override
	public void optionChanged(final int index, String name) {
		Main.currentPeriod = Period.getPeriod(index);
		try {
			// final HomeVizApplication app = ((HomeVizApplication)
			// getActivity().getApplication());
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// app.setCurrentPeriod(Period.values()[index]);
					// Log.i(getClass().getSimpleName(),
					// "Current period: " + app.getCurrentPeriod());
					try {
						UsageContainerFragment.resetViews();
					} catch (Exception e) {
					}
					try {
						MetaphorContainerFragment.resetViews();
					} catch (Exception e) {
					}
					try {
						TotalTreeMapFragment.resetViews();
					} catch (Exception e) {
					}
				}
			});
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(),
					"User scrolled the optionspinner too fast");
		}
	}

}
