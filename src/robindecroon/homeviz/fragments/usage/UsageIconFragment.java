/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments.usage;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.activities.MainActivity;
import robindecroon.homeviz.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.listeners.ConsumerOnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The Class UsageIconFragment.
 */
public class UsageIconFragment extends OptionSpinnerFragment {

	/** The current room. */
	private Room currentRoom;
	
	/** The context. */
	private MainActivity context;
	
	private View usageIconView;
	
	private int roomIndex;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		usageIconView = inflater.inflate(R.layout.usage_layout, container,	false);

		if (getArguments() != null) {
			context = (MainActivity) getActivity();
			roomIndex = getArguments().getInt(Constants.USAGE_ROOM);
			currentRoom = ((HomeVizApplication) context.getApplication()).getRooms().get(roomIndex);


			// Set the total amount
			final TextView usageAmount = (TextView) usageIconView.findViewById(R.id.usage_amount);
			usageAmount.setText(currentRoom.getTotalPrice());

			// Init the categories
			initLight();
			initWater();
			initHeating();
			initAppliances();
			initMultimedia();
			
			// Time
			initOptionSpinner(usageIconView, R.id.topSpinner, R.id.topArrowLeft, R.id.topArrowRight);

			return usageIconView;
		} else {
			throw new IllegalStateException(getClass().getSimpleName()
					+ " should have room arguments!");
		}
	}

	/**
	 * Initialize the multimedia.
	 *
	 * @param usageIconView the usage icon view
	 * @param roomIndex the room index
	 */
	private void initMultimedia() {
		LinearLayout multimedias = (LinearLayout) usageIconView.findViewById(R.id.home_cinema_layout);
		multimedias.setOnClickListener(new ConsumerOnClickListener(
				roomIndex, context, Constants.MULTIMEDIA));
		TextView multimedia = (TextView) usageIconView.findViewById(R.id.usage_tv_price);
		multimedia.setText(currentRoom.getHomeCinemaPrice().toString());
	}

	/**
	 * Initialize the appliances.
	 *
	 * @param usageIconView the usage icon view
	 * @param roomIndex the room index
	 */
	private void initAppliances() {
		LinearLayout appliances = (LinearLayout) usageIconView.findViewById(R.id.appliances_layout);
		appliances.setOnClickListener(new ConsumerOnClickListener(
				roomIndex, context, Constants.APPLIANCE));
		TextView appliance = (TextView) usageIconView.findViewById(R.id.usage_appliances_price);
		appliance.setText(currentRoom.getAppliancesPrice().toString());
	}

	/**
	 * Initialize the heating.
	 *
	 * @param usageIconView the usage icon view
	 */
	private void initHeating() {
		TextView heating = (TextView) usageIconView.findViewById(R.id.usage_heating_price);
		heating.setText(currentRoom.getHeating().toString());
	}

	/**
	 * Initialize the water.
	 *
	 * @param usageIconView the usage icon view
	 * @param roomIndex the room index
	 */
	private void initWater() {
		LinearLayout waters = (LinearLayout) usageIconView.findViewById(R.id.water_layout);
		waters.setOnClickListener(new ConsumerOnClickListener(roomIndex, context, Constants.WATER));
		TextView water = (TextView) usageIconView.findViewById(R.id.usage_water_price);
		water.setText(currentRoom.getWaterPrice().toString());
	}

	/**
	 * Initialize the light.
	 *
	 * @param usageIconView the usage icon view
	 * @param roomIndex the room index
	 */
	private void initLight() {
		LinearLayout lights = (LinearLayout) usageIconView.findViewById(R.id.light_layout);
		lights.setOnClickListener(new ConsumerOnClickListener(roomIndex, context, Constants.LIGHT));
		TextView light = (TextView) usageIconView.findViewById(R.id.usage_light_price);
		light.setText(currentRoom.getLightPrice().toString());
	}
}
