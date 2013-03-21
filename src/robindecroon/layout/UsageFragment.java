package robindecroon.layout;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.ConsumerOnClickListener;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.util.Period;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UsageFragment extends OptionSpinnerFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.usage_layout, container,
				false);

		HomeVizApplication app = (HomeVizApplication) getActivity()
				.getApplication();

		Room currentRoom = null;
		int roomIndex = 0;
		if (getArguments() != null) {
			roomIndex = getArguments().getInt("room");
			currentRoom = app.getRooms().get(roomIndex);
		} else {
			Log.e(getClass().getSimpleName(), "No room arguments");
		}
		final int finalRoomIndex = roomIndex;

		Period currentPeriod = app.getCurrentPeriod();

		// Lights
		LinearLayout lights = (LinearLayout) rootView
				.findViewById(R.id.light_layout);
		lights.setOnClickListener(new ConsumerOnClickListener(finalRoomIndex,
				getActivity(), Constants.LIGHT));
		TextView light = (TextView) rootView
				.findViewById(R.id.usage_light_price);
		light.setText(currentRoom.getLightPrice(currentPeriod).toString());

		// Water
		LinearLayout waters = (LinearLayout) rootView
				.findViewById(R.id.water_layout);
		waters.setOnClickListener(new ConsumerOnClickListener(finalRoomIndex,
				getActivity(), Constants.WATER));
		TextView water = (TextView) rootView
				.findViewById(R.id.usage_water_price);
		water.setText(currentRoom.getWaterPrice(currentPeriod).toString());

		// Heating
		LinearLayout heatings = (LinearLayout) rootView
				.findViewById(R.id.heating_layout);
		heatings.setOnClickListener(new ConsumerOnClickListener(finalRoomIndex,
				getActivity(), Constants.HEATING));
		TextView heating = (TextView) rootView
				.findViewById(R.id.usage_heating_price);
		heating.setText(currentRoom.getHeating(currentPeriod).toString());

		// Appliances
		LinearLayout appliances = (LinearLayout) rootView
				.findViewById(R.id.appliances_layout);
		appliances.setOnClickListener(new ConsumerOnClickListener(
				finalRoomIndex, getActivity(), Constants.APPLIANCE));
		TextView appliance = (TextView) rootView
				.findViewById(R.id.usage_appliances_price);
		appliance.setText(currentRoom.getAppliancesPrice(currentPeriod)
				.toString());

		// Home Cinema
		LinearLayout homeCinemas = (LinearLayout) rootView
				.findViewById(R.id.home_cinema_layout);
		homeCinemas.setOnClickListener(new ConsumerOnClickListener(
				finalRoomIndex, getActivity(), Constants.HOMECINEMA));
		TextView homeCinema = (TextView) rootView
				.findViewById(R.id.usage_tv_price);
		homeCinema.setText(currentRoom.getHomeCinemaPrice(currentPeriod)
				.toString());

		// Total
		final TextView usageAmount = (TextView) rootView
				.findViewById(R.id.usage_amount);
		usageAmount.setText(currentRoom.getTotalPrice(currentPeriod));

		// Time
		initOptionSpinner(rootView, R.id.topSpinner, R.id.topArrowLeft,
				R.id.topArrowRight);

		return rootView;
	}
}
