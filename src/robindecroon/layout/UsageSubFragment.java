package robindecroon.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import robindecroon.homeviz.usage.UsageActivityUtils;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;
import android.app.ActionBar;
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

public class UsageSubFragment extends OptionSpinnerFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sub_usage_layout, container,
				false);
		ActionBar ab = getActivity().getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setIcon(R.drawable.up_arrow);

		HomeVizApplication app = (HomeVizApplication) getActivity()
				.getApplication();

		Room currentRoom = null;
		if (getArguments() != null) {
			int roomIndex = getArguments().getInt("room");
			currentRoom = app.getRooms().get(roomIndex);
			// app.setCurrentRoom(roomIndex);
		} else {
			Log.e(getClass().getSimpleName(), "No room arguments");
			// currentRoom = app.getCurrentRoom();
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
		Amount sum = new Amount(0);
		try {
			List<Consumer> consumers = new ArrayList<Consumer>();
			Bundle args = getArguments();
			switch (args.getInt(Constants.USAGE_TYPE)) {
			case 11:
				for (Light light : currentRoom.getLights()) {
					System.out.println("new light");
					consumers.add(light);
				}
				break;
			case 12:
				for (Appliance appliance : currentRoom.getAppliances()) {
					consumers.add(appliance);
				}
				break;
			case 13:
				for (HomeCinema homeCinema : currentRoom.getHomeCinemas()) {
					consumers.add(homeCinema);
				}
				break;
			case 14:
				for (Water water : currentRoom.getWaters()) {
					consumers.add(water);
				}
				break;
			case 15:
				for (Heating heating : currentRoom.getHeatings()) {
					consumers.add( heating);
				}
				break;
			default:
				break;
			}
			args.get(Constants.USAGE_TYPE);

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
			View noConsumer = UsageActivityUtils
					.getEmptyRoomLights(getActivity());
			subLayout.addView(noConsumer);
		}

	}

}
