/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments.metaphor;

import java.util.Locale;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.house.device.Consumer;
import robindecroon.homeviz.metaphor.CO2;
import robindecroon.homeviz.metaphor.Fuel;
import robindecroon.homeviz.metaphor.FuelKind;
import robindecroon.homeviz.util.WeightUnit;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The Class MetaphorContentFragment.
 */
public class MetaphorContentFragment extends OptionSpinnerFragment {
	
	/** The views of this fragments	*/
	private TextView title;
	private View contentView;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.metaphor_content_layout, container, false);

		initOptionSpinner(contentView, 
				R.id.metaphor_spinner, R.id.metaphor_arrow_left, R.id.metaphor_arrow_right);

		title = (TextView) contentView.findViewById(R.id.metaphor_title);

		Bundle args = getArguments();
		if (args != null) {
			int type = args.getInt(Constants.METAPHOR_TYPE);
			int picId = getDrawableID(type);
			String value = "";
			if (args.getBoolean(Constants.METAPHOR_CONSUMER)) {
				value = generateConsumerMetaphorValue(args);
			} else {
				value = generateHouseMetaphorValue(type, value);
			}
			setValue(value, picId);
		}
		return contentView;
	}

	/**
	 * Generate consumer metaphor value.
	 *
	 * @param contentView the content view
	 * @param title the title
	 * @param args the args
	 * @return the string
	 */
	private String generateConsumerMetaphorValue(Bundle args) {
		String value;
		value = args.getString(Constants.METAPHOR_VALUE);
		String consumerName = args.getString(Constants.METAPHOR_CONSUMER_NAME);
		title.setText(consumerName);
		String imagename = consumerName.toLowerCase(Locale.US);
		int id = getResources().getIdentifier(imagename, "drawable",
				getActivity().getPackageName());
		ImageView consumerImage = (ImageView) contentView
				.findViewById(R.id.metaphor_consumer_image);
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp2.gravity = Gravity.CENTER;
		consumerImage.setAdjustViewBounds(true);
		consumerImage.setLayoutParams(lp2);
		consumerImage.setImageResource(id);
		return value;
	}

	/**
	 * Generate house metaphor value.
	 *
	 * @param title the title
	 * @param type the type
	 * @param value the value
	 * @return the string
	 */
	private String generateHouseMetaphorValue(int type,	String value) {
		CO2 sum = new CO2(0, WeightUnit.GRAM);
		Fuel fuelSum = new Fuel(Math.random() * 10, FuelKind.DIESEL);
		double waterSum = 0;
		for (Room room : ((HomeVizApplication) getActivity()
				.getApplication()).getRooms()) {
			try {
				switch (type) {
				case Constants.METAPHOR_TYPE_CO2:
					for (Consumer consumer : room.getElectrics()) {
						sum = sum.add(consumer.getCO2Value());
					}
					value = sum.toString();
					title.setText(R.string.metaphor_electricty_title);
					break;
				case Constants.METAPHOR_TYPE_FUEL:
					for (Consumer consumer : room.getHeatings()) {
						fuelSum = fuelSum.add(consumer.getFuel(), FuelKind.DIESEL);
					}
					value = fuelSum.toString(getActivity());
					title.setText(R.string.metaphor_heating_title);
					break;
				case Constants.METAPHOR_TYPE_WATER:
					for (Consumer consumer : room.getWaters()) {
						waterSum += consumer.getLiter();
					}
					value = Math.round(waterSum / Constants.BOTTLE_CONTENT)
							+ getResources().getString(R.string.metaphor_water_text);
					title.setText(R.string.metaphor_water_title);
					break;
				}
			} catch (NoSuchDevicesInRoom e) {
				// No consumer is this room
			}
		}
		return value;
	}


	/**
	 * Gets the drawable id.
	 *
	 * @param metaphorType the metaphor type
	 * @return the drawable id
	 */
	private int getDrawableID(int metaphorType) {
		int picId = 0;
		switch (metaphorType) {
		case Constants.METAPHOR_TYPE_CO2:
			picId = R.drawable.footprint;
			break;
		case Constants.METAPHOR_TYPE_WATER:
			picId = R.drawable.evian;
			break;
		case Constants.METAPHOR_TYPE_FUEL:
			picId = R.drawable.fuelgauge;
			break;
		}
		return picId;
	}


	/**
	 * Sets the value.
	 *
	 * @param metaphorValue the metaphor value
	 * @param contentView the content view
	 * @param imageId the image id
	 */
	public void setValue(String metaphorValue, int imageId) {
		TextView value = (TextView) contentView.findViewById(R.id.metaphor_value);
		value.setText(metaphorValue);
		value.invalidate();
		ImageView image = (ImageView) contentView.findViewById(R.id.metaphor_image);
		image.setImageResource(imageId);
	}
}
