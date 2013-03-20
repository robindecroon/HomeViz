package robindecroon.layout;

import libraries.nielsbillen.ArrowButton;
import libraries.nielsbillen.OptionSpinner;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.PeriodListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UsageFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.usage_layout, container,
				false);

		Room currentRoom = ((HomeVizApplication) getActivity().getApplication())
				.getCurrentRoom();
		Period currentPeriod = ((HomeVizApplication) getActivity()
				.getApplication()).getCurrentPeriod();

		final TextView light = (TextView) rootView
				.findViewById(R.id.usage_light_price);
		light.setText(currentRoom.getLightPrice(currentPeriod).toString());

		final TextView water = (TextView) rootView
				.findViewById(R.id.usage_water_price);
		water.setText(currentRoom.getWaterPrice(currentPeriod).toString());

		final TextView heating = (TextView) rootView
				.findViewById(R.id.usage_heating_price);
		heating.setText(currentRoom.getHeating(currentPeriod).toString());

		final TextView appl = (TextView) rootView
				.findViewById(R.id.usage_appliances_price);
		appl.setText(currentRoom.getAppliancesPrice(currentPeriod).toString());

		final TextView tv = (TextView) rootView
				.findViewById(R.id.usage_tv_price);
		tv.setText(currentRoom.getHomeCinemaPrice(currentPeriod).toString());

		final TextView usageAmount = (TextView) rootView
				.findViewById(R.id.usage_amount);
		usageAmount.setText(currentRoom.getTotalPrice(currentPeriod));

		initSpinner(rootView);
		
		setTotalAmount(rootView, currentRoom, currentPeriod);

		return rootView;
	}
	
	private void setTotalAmount(View v, Room room, Period period) {
		final TextView usageAmount = (TextView) v.findViewById(R.id.usage_amount);
		usageAmount.setText(room.getTotalPrice(period));
	}

	/**
	 * Initialiseert de spinner.
	 */
	private void initSpinner(View v) {
		ArrowButton left = (ArrowButton) v.findViewById(R.id.topArrowLeft);
		left.setArrowDirection(ArrowButton.DIRECTION_LEFT);
		ArrowButton right = (ArrowButton) v.findViewById(R.id.topArrowRight);
		right.setArrowDirection(ArrowButton.DIRECTION_RIGHT);
		PeriodListener periodListener = new PeriodListener(getActivity());
		OptionSpinner spinner = (OptionSpinner) v.findViewById(R.id.topSpinner);
		spinner.setLeftButton(left);
		spinner.setRightButton(right);
		spinner.setOnClickListener(periodListener);
		spinner.setOnLongClickListener(periodListener);
		Period[] periods = Period.values();
		String[] namePeriods = new String[periods.length];
		for (int i = 0; i < periods.length; i++) {
			namePeriods[i] = periods[i].getName(getActivity());
		}
		spinner.setOptions(namePeriods);
	}
}
