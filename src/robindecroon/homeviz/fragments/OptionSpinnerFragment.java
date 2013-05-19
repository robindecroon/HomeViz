/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments;

import libraries.optionspinner.ArrowButton;
import libraries.optionspinner.OptionSpinner;
import libraries.optionspinner.SpinnerListener;
import robindecroon.homeviz.activities.MainActivity;
import robindecroon.homeviz.listeners.PeriodListener;
import robindecroon.homeviz.util.FragmentResetter;
import robindecroon.homeviz.util.Period;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * The Class OptionSpinnerFragment.
 */
public abstract class OptionSpinnerFragment extends Fragment implements SpinnerListener {
	
	/** The periodSpinner. */
	private OptionSpinner periodSpinner;

	/**
	 * Initialize the option spinner.
	 *
	 * @param fragmentView the fragment view
	 * @param periodSpinnerId the period spinner id
	 * @param leftArrowId the left arrow id
	 * @param rightArrowId the right arrow id
	 */
	protected void initOptionSpinner(View fragmentView, int periodSpinnerId, 
			int leftArrowId, int rightArrowId) {
		periodSpinner = (OptionSpinner) fragmentView.findViewById(periodSpinnerId);
		
		initArrows(fragmentView, leftArrowId, rightArrowId);
		
		initListeners();
		
		periodSpinner.setIndex(MainActivity.currentPeriod.getId());
		String[] periodNames = initPeriodNames();
		periodSpinner.setOptions(periodNames);
		
		periodSpinner.addListener(this);
	}

	/**
	 * Initialize the period names.
	 *
	 * @return the string[]
	 */
	private String[] initPeriodNames() {
		Period[] periods = Period.values();
		String[] periodNames = new String[periods.length];
		for (int i = 0; i < periods.length; i++) {
			Period period = periods[i];
			periodNames[i] = period.getName(getActivity());
		}
		return periodNames;
	}


	/**
	 * Initialize the listeners.
	 */
	private void initListeners() {
		PeriodListener periodListener = new PeriodListener(getActivity());
		periodSpinner.setOnClickListener(periodListener);
		periodSpinner.setOnLongClickListener(periodListener);
	}

	/**
	 * Initialize the arrows.
	 *
	 * @param v the v
	 * @param leftId the left id
	 * @param rightId the right id
	 */
	private void initArrows(View v, int leftId, int rightId) {
		ArrowButton left = (ArrowButton) v.findViewById(leftId);
		left.setArrowDirection(ArrowButton.DIRECTION_LEFT);
		periodSpinner.setLeftButton(left);
		ArrowButton right = (ArrowButton) v.findViewById(rightId);
		right.setArrowDirection(ArrowButton.DIRECTION_RIGHT);
		periodSpinner.setRightButton(right);
	}

	/* (non-Javadoc)
	 * @see libraries.optionspinner.SpinnerListener#optionChanged(int, java.lang.String)
	 */
	@Override
	public void optionChanged(final int index, String name) {
		MainActivity.currentPeriod = Period.getPeriod(index);
		FragmentResetter.reset(getActivity());
	}
	
}
