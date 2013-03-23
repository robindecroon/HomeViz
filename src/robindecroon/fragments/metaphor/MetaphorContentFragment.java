package robindecroon.fragments.metaphor;

import robindecroon.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.room.CO2;
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Fuel;
import robindecroon.homeviz.room.FuelKind;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.room.WeightUnit;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MetaphorContentFragment extends OptionSpinnerFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.metaphor_content_layout,
				container, false);

		initOptionSpinner(fragmentView, R.id.metaphor_spinner,
				R.id.metaphor_arrow_left, R.id.metaphor_arrow_right);

		TextView title = (TextView) fragmentView
				.findViewById(R.id.metaphor_title);
		title.setText(R.string.metaphor_co2);

		Bundle args = getArguments();
		if (args != null) {
			boolean consumerArg = args.getBoolean(Constants.METAPHOR_CONSUMER);
			int type = args.getInt(Constants.METAPHOR_TYPE);
			int picId = getDrawableID(type);
			String value = null;
			if (consumerArg) {
				value = args.getString(Constants.METAPHOR_VALUE);
				title.setText(args.getString(Constants.METAPHOR_CONSUMER_NAME));
			} else {
				for (Room room : ((HomeVizApplication) getActivity()
						.getApplication()).getRooms()) {
					try {
						switch (type) {
						case Constants.METAPHOR_TYPE_CO2:
							CO2 sum = new CO2(0, WeightUnit.GRAM);
							for (Consumer consumer : room.getElectrics()) {
								sum = sum.add(consumer.getCO2Value());
							}
							value = sum.toString();
							title.setText(R.string.metaphor_co2);
							break;
						case Constants.METAPHOR_TYPE_FUEL:
							Fuel fuelSum = new Fuel(Math.random() * 10, FuelKind.DIESEL);
							for (Consumer consumer : room.getHeatings()) {
								fuelSum = fuelSum.add(consumer.getFuel(), FuelKind.DIESEL);
								System.out.println("FuelSum: " + fuelSum);
							}
							value = fuelSum.toString();
							title.setText("Liters of Diesel");
							break;
						case Constants.METAPHOR_TYPE_WATER:
							double waterSum = 0;
							for (Consumer consumer : room.getWaters()) {
								waterSum += consumer.getLiter();
							}
							value = Math.round(waterSum/Constants.BOTTLE_CONTENT) + Constants.METAPHOR_WATER_TEXT;
							title.setText("Bottles of water");
							break;
						}
					} catch (NoSuchDevicesInRoom e) {
						// should not happen
						e.printStackTrace();
					}
				}
			}
			setValue(value, fragmentView, picId);
		}
		return fragmentView;
	}

	private int getDrawableID(int type) {
		int picId = 0;
		switch (type) {
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

	public void setValue(String text, View v, int id) {
		TextView value = (TextView) v.findViewById(R.id.metaphor_value);
		value.setCompoundDrawablesWithIntrinsicBounds(0, id, 0, 0);
		value.setText(text);
		value.invalidate();
	}

}
