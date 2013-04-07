package robindecroon.homeviz.fragments.usage;

import java.util.List;
import java.util.Locale;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.room.Room.ConsumerType;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.ImageScaler;
import robindecroon.homeviz.util.UsageActivityUtils;
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

public class UsageConsumerFragment extends OptionSpinnerFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.usage_consumer_layout,
				container, false);
		// ActionBar ab = getActivity().getActionBar();
		// ab.setHomeButtonEnabled(true);

		HomeVizApplication app = (HomeVizApplication) getActivity()
				.getApplication();

		Room currentRoom = null;
		if (getArguments() != null) {
			int roomIndex = getArguments().getInt("room");
			currentRoom = app.getRooms().get(roomIndex);
		} else {
			Log.e(getClass().getSimpleName(), "No room arguments");
		}

		initOptionSpinner(rootView, R.id.sub_spinner, R.id.sub_arrow_left,
				R.id.sub_arrow_right);

		setAmounts(rootView, currentRoom);

		return rootView;
	}

	private void setAmounts(View v, Room currentRoom) {
		LinearLayout subLayout = (LinearLayout) v
				.findViewById(R.id.sub_container);
		subLayout.removeAllViews();

		List<Consumer> consumers = null;
		Bundle args = getArguments();
		try {
			switch (args.getInt(Constants.USAGE_TYPE)) {
			case Constants.LIGHT:
				consumers = currentRoom.getConsumersOfType(ConsumerType.Light);
				break;
			case Constants.APPLIANCE:
				consumers = currentRoom
						.getConsumersOfType(ConsumerType.Appliance);
				break;
			case Constants.HOMECINEMA:
				consumers = currentRoom
						.getConsumersOfType(ConsumerType.HomeCinema);
				break;
			case Constants.WATER:
				consumers = currentRoom.getConsumersOfType(ConsumerType.Water);
				break;
			case Constants.HEATING:
				consumers = currentRoom
						.getConsumersOfType(ConsumerType.Heating);
				break;
			default:
				throw new IllegalStateException(
						"default case should not be reached!");
			}

			Amount sum = new Amount(0);
			for (int i = 0; i < consumers.size(); i++) {

				Consumer consumer = consumers.get(i);
				LinearLayout layout = new LinearLayout(getActivity());
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
				layout.setLayoutParams(lp);
				layout.setOrientation(LinearLayout.VERTICAL);

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
			TextView amount = (TextView) v.findViewById(R.id.sub_amount);
			amount.setText(sum.toString());
		} catch (NoSuchDevicesInRoom e) {
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
			case Constants.HOMECINEMA:
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

}
