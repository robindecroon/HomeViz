package robindecroon.homeviz.usage;

import robindecroon.homeviz.R;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class UsageActivityUtils {

	public static View getEmptyRoomLights(Context context) {
		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
		layout.setLayoutParams(lp);
		layout.setOrientation(LinearLayout.VERTICAL);

		ImageView image = new ImageView(context);
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp2.gravity = Gravity.CENTER;
		image.setAdjustViewBounds(true);
		image.setLayoutParams(lp2);

		image.setImageResource(R.drawable.no_lights);

		layout.addView(image);

		TextView text = new TextView(context);
		text.setTextColor(context.getResources().getColor(R.color.White));
		text.setGravity(Gravity.CENTER);
		text.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize(40);
		text.setText(context.getResources().getString(R.string.no_lights));

		layout.addView(text);

		return layout;
	}

	public static View getEmptyRoomWater(Context context) {

		TextView text = new TextView(context);
		text.setTextColor(context.getResources().getColor(R.color.White));
		text.setGravity(Gravity.CENTER);
		text.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize(40);
		text.setText(context.getResources()
				.getString(R.string.no_water_in_room));

		return text;
	}

	public static View getEmptyRoomHomeCinema(Context context) {

		TextView text = new TextView(context);
		text.setTextColor(context.getResources().getColor(R.color.White));
		text.setGravity(Gravity.CENTER);
		text.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize(40);
		text.setText(context.getResources().getString(
				R.string.no_home_cinema_in_room));

		return text;
	}

	public static View getEmptyRoomAppliance(Context context) {
		TextView text = new TextView(context);
		text.setTextColor(context.getResources().getColor(R.color.White));
		text.setGravity(Gravity.CENTER);
		text.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize(40);
		text.setText(context.getResources().getString(
				R.string.no_appliance_in_room));

		return text;
	}

	public static View getEmptyRoomHeating(Context context) {
		TextView text = new TextView(context);
		text.setTextColor(context.getResources().getColor(R.color.White));
		text.setGravity(Gravity.CENTER);
		text.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize(40);
		text.setText(context.getResources().getString(
				R.string.no_heating_in_room));

		return text;
	}

}
