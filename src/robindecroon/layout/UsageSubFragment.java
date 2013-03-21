package robindecroon.layout;

import java.util.List;
import java.util.Locale;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.room.Light;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.usage.UsageActivityUtils;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UsageSubFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.light_usage_layout,
				container, false);

		Room currentRoom = ((HomeVizApplication) getActivity().getApplication())
				.getCurrentRoom();
		Period currentPeriod = ((HomeVizApplication) getActivity()
				.getApplication()).getCurrentPeriod();

		setAmounts(rootView, currentRoom, currentPeriod);

		setTotalAmount(rootView, currentRoom, currentPeriod);

		return rootView;
	}

	private void setTotalAmount(View v, Room room, Period period) {
		final TextView usageAmount = (TextView) v
				.findViewById(R.id.light_amount);
		usageAmount.setText(room.getLightPrice(period).toString());
	}

	private void setAmounts(View v, Room currentRoom, Period currentPeriod) {
		LinearLayout lightsLayout = (LinearLayout) v.findViewById(R.id.lights);
		lightsLayout.removeAllViews();
		Amount sum = new Amount(0);
		try {
			List<Light> lights = currentRoom.getLights();

			for (int i = 0; i < lights.size(); i++) {

				Light light = lights.get(i);
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

				String imagename = light.getName().toLowerCase(Locale.US);
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
				Amount price = light.getPrice(currentPeriod);
				sum = sum.add(price);
				text.setText(price.toString());

				layout.addView(text);

				// layout.setClickable(true);
				// layout.setOnClickListener(new ClickListener(this,
				// LightListActivity.class));

				lightsLayout.addView(layout);
			}
			TextView amount = (TextView) v.findViewById(R.id.light_amount);
			amount.setText(sum.toString());
		} catch (NoSuchDevicesInRoom e) {
			View noLights = UsageActivityUtils
					.getEmptyRoomLights(getActivity());
			lightsLayout.addView(noLights);
		}

	}

}
