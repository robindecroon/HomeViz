package robindecroon.fragments.usage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import robindecroon.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.room.Appliance;
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Heating;
import robindecroon.homeviz.room.HomeCinema;
import robindecroon.homeviz.room.Light;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.room.Water;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.ImageScaler;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.usage.UsageActivityUtils;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
		View rootView = inflater.inflate(R.layout.usage_consumer_layout, container,
				false);
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

		Period currentPeriod = app.getCurrentPeriod();

		initOptionSpinner(rootView, R.id.sub_spinner, R.id.sub_arrow_left,
				R.id.sub_arrow_right);

		setAmounts(rootView, currentRoom, currentPeriod);

		return rootView;
	}

	private void setAmounts(View v, Room currentRoom, Period currentPeriod) {
		LinearLayout subLayout = (LinearLayout) v
				.findViewById(R.id.sub_container);
		subLayout.removeAllViews();

		List<Consumer> consumers = new ArrayList<Consumer>();
		Bundle args = getArguments();
		try {
			switch (args.getInt(Constants.USAGE_TYPE)) {
			case Constants.LIGHT:
				for (Light light : currentRoom.getLights()) {
					System.out.println("new light");
					consumers.add(light);
				}
				break;
			case Constants.APPLIANCE:
				for (Appliance appliance : currentRoom.getAppliances()) {
					consumers.add(appliance);
				}
				break;
			case Constants.HOMECINEMA:
				for (HomeCinema homeCinema : currentRoom.getHomeCinemas()) {
					consumers.add(homeCinema);
				}
				break;
			case Constants.WATER:
				for (Water water : currentRoom.getWaters()) {
					consumers.add(water);
				}
				break;
			case Constants.HEATING:
				for (Heating heating : currentRoom.getHeatings()) {
					consumers.add(heating);
				}
				break;
			default:
				break;
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
				Amount price = consumer.getPrice(currentPeriod);
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
