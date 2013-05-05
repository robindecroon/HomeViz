/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments.usage;

import java.util.List;
import java.util.Locale;

import libraries.stackoverflow.ImageScaler;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.house.device.Consumer;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.UsageActivityUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The Class UsageConsumerFragment.
 */
public class UsageConsumerFragment extends OptionSpinnerFragment {
	
	/** The current room. */
	private Room currentRoom;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View usageConsumerView = inflater.inflate(R.layout.usage_consumer_layout, container, false);

		initOptionSpinner(usageConsumerView, 
				R.id.sub_spinner, R.id.sub_arrow_left, R.id.sub_arrow_right);
		
		initRoom();
		
		initContent(usageConsumerView);

		return usageConsumerView;
	}

	/**
	 * Initialize the room.
	 *
	 * @return the room
	 */
	private Room initRoom() {
		HomeVizApplication app = (HomeVizApplication) getActivity().getApplication();
		if (getArguments() != null) {
			int roomIndex = getArguments().getInt(Constants.USAGE_ROOM);
			currentRoom = app.getRooms().get(roomIndex);
		} else {
			Log.e(getClass().getSimpleName(), "No room arguments");
		}
		return currentRoom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem home = menu.getItem(android.R.id.home);
		home.setIcon(R.drawable.up_arrow);
	}

	/**
	 * Initialize the content.
	 *
	 * @param usageConsumerView the usage consumer view
	 */
	private void initContent(View usageConsumerView) {
		LinearLayout subLayout = (LinearLayout) usageConsumerView.findViewById(R.id.sub_container);
		subLayout.removeAllViews();

		List<Consumer> consumers = null;
		Bundle args = getArguments();
		try {
			consumers = initConsumers(consumers, args);

			Amount sum = new Amount(0);
			for (int i = 0; i < consumers.size(); i++) {
				Consumer consumer = consumers.get(i);
				
				LinearLayout layout = getWrapContentVerticalLayout();
				
				ImageView image = initConsumerImage(consumer);
				layout.addView(image);

				TextView text = new TextView(getActivity());
				text.setTextColor(getResources().getColor(R.color.White));
				text.setGravity(Gravity.CENTER);
				text.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				text.setTextSize(40);
				Amount price = consumer.getPrice();
				sum = sum.add(price);
				text.setText(price.toString());

				layout.addView(text);

				subLayout.addView(layout);
			}
			TextView amount = (TextView) usageConsumerView.findViewById(R.id.sub_amount);
			amount.setText(sum.toString());
		} catch (NoSuchDevicesInRoom e) {
			setEmptyView(subLayout, args);
		}

	}

	/**
	 * Gets the wrap content vertical layout.
	 *
	 * @return the wrap content vertical layout
	 */
	private LinearLayout getWrapContentVerticalLayout() {
		LinearLayout layout = new LinearLayout(getActivity());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
		layout.setLayoutParams(lp);
		layout.setOrientation(LinearLayout.VERTICAL);
		return layout;
	}

	/**
	 * Initialize the consumer image.
	 *
	 * @param consumer the consumer
	 * @return the image view
	 */
	private ImageView initConsumerImage(Consumer consumer) {
		ImageView image = new ImageView(getActivity());
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp2.gravity = Gravity.CENTER;
		image.setAdjustViewBounds(true);
		image.setLayoutParams(lp2);

		String imagename = consumer.getName().toLowerCase(Locale.US);
		int picId = getResources().getIdentifier(imagename, "drawable",
				getActivity().getPackageName());
		image.setImageResource(picId);
		ImageScaler.scaleImage(image, Constants.IMAGE_SCALE);
		return image;
	}

	/**
	 * Initialize the consumers.
	 *
	 * @param consumers the consumers
	 * @param args the args
	 * @return the list
	 * @throws NoSuchDevicesInRoom the no such devices in room
	 */
	private List<Consumer> initConsumers(List<Consumer> consumers, Bundle args) 
			throws NoSuchDevicesInRoom {
		switch (args.getInt(Constants.USAGE_TYPE)) {
		case Constants.LIGHT:
			consumers = currentRoom.getLights();
			break;
		case Constants.APPLIANCE:
			consumers = currentRoom.getAppliances();
			break;
		case Constants.MULTIMEDIA:
			consumers = currentRoom.getHomeCinemas();
			break;
		case Constants.WATER:
			consumers = currentRoom.getWaters();
			break;
		case Constants.HEATING:
			consumers = currentRoom.getHeatings();
			break;
		default:
			throw new IllegalStateException("default case should not be reached!");
		}
		return consumers;
	}


	/**
	 * Sets the empty view.
	 *
	 * @param subLayout the sub layout
	 * @param args the args
	 */
	private void setEmptyView(LinearLayout subLayout, Bundle args) {
		View noConsumer = null;
		switch (args.getInt(Constants.USAGE_TYPE)) {
		case Constants.LIGHT:
			noConsumer = UsageActivityUtils
					.getEmptyRoomLights(getActivity());
			break;
		case Constants.APPLIANCE:
			noConsumer = UsageActivityUtils
					.getEmptyRoomAppliance(getActivity());
			break;
		case Constants.MULTIMEDIA:
			noConsumer = UsageActivityUtils
					.getEmptyRoomHomeCinema(getActivity());
			break;
		case Constants.WATER:
			noConsumer = UsageActivityUtils
					.getEmptyRoomWater(getActivity());
			break;
		case Constants.HEATING:
			noConsumer = UsageActivityUtils
					.getEmptyRoomHeating(getActivity());
			break;
		default:
			break;
		}
		subLayout.addView(noConsumer);
	}

}
