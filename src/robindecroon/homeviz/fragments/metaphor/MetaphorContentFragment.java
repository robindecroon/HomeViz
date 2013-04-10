package robindecroon.homeviz.fragments.metaphor;

import java.util.Locale;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.room.CO2;
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Fuel;
import robindecroon.homeviz.room.FuelKind;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.room.WeightUnit;
import robindecroon.homeviz.util.ImageScaler;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

		Bundle args = getArguments();
		if (args != null) {
			boolean consumerArg = args.getBoolean(Constants.METAPHOR_CONSUMER);
			int type = args.getInt(Constants.METAPHOR_TYPE);
			int picId = getDrawableID(type);
			String value = null;
			if (consumerArg) {
				value = args.getString(Constants.METAPHOR_VALUE);
				String consumerName = args
						.getString(Constants.METAPHOR_CONSUMER_NAME);
				title.setText(consumerName);
				String imagename = consumerName.toLowerCase(Locale.US);
				int id = getResources().getIdentifier(imagename, "drawable",
						getActivity().getPackageName());
				ImageView consumerImage = (ImageView) fragmentView
						.findViewById(R.id.metaphor_consumer_image);
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp2.gravity = Gravity.CENTER;
				consumerImage.setAdjustViewBounds(true);
				consumerImage.setLayoutParams(lp2);
				consumerImage.setImageResource(id);
				ImageScaler.scaleImage(consumerImage, 120);

			} else {
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
								fuelSum = fuelSum.add(consumer.getFuel(),
										FuelKind.DIESEL);
							}
							value = fuelSum.toString();
							title.setText(R.string.metaphor_heating_title);
							break;
						case Constants.METAPHOR_TYPE_WATER:
							for (Consumer consumer : room.getWaters()) {
								waterSum += consumer.getLiter();
							}
							value = Math.round(waterSum
									/ Constants.BOTTLE_CONTENT)
									+ Constants.METAPHOR_WATER_TEXT;
							title.setText(R.string.metaphor_water_title);
							break;
						}
					} catch (NoSuchDevicesInRoom e) {
						// should not happen
						Log.e(getClass().getSimpleName(), "You should not see this message ;-)");
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
