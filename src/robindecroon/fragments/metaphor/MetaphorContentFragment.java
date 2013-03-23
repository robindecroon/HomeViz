package robindecroon.fragments.metaphor;

import robindecroon.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.room.CO2;
import robindecroon.homeviz.room.Consumer;
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
			String value = args.getString(Constants.CO2_KEY);
			setValue(value, fragmentView);
		} else {
			CO2 sum = new CO2(0, WeightUnit.GRAM);
			for (Room room : ((HomeVizApplication) getActivity()
					.getApplication()).getRooms()) {
				try {
					for (Consumer consumer : room.getElectrics()) {
						sum = sum.add(consumer.getCO2Value());
					}
				} catch (NoSuchDevicesInRoom e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			setValue(sum.toString(), fragmentView);
		}

		return fragmentView;
	}

	public void setValue(String text, View v) {
		TextView value = (TextView) v.findViewById(R.id.metaphor_value);
		value.setText(text);
		value.invalidate();
	}

}
