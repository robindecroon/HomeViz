/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.util;

import robindecroon.homeviz.R;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The Class UsageActivityUtils.
 */
public abstract class UsageActivityUtils {

	/**
	 * Gets the empty room lights.
	 *
	 * @param context the context
	 * @return the empty room lights
	 */
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

		TextView text = getTextView(context);
		text.setText(context.getResources().getString(R.string.no_lights));

		layout.addView(text);

		return layout;
	}

	/**
	 * Gets the empty room water.
	 *
	 * @param context the context
	 * @return the empty room water
	 */
	public static View getEmptyRoomWater(Context context) {
		TextView text = getTextView(context);
		text.setText(context.getResources().getString(R.string.no_water_in_room));
		return text;
	}

	/**
	 * Gets the empty room home cinema.
	 *
	 * @param context the context
	 * @return the empty room home cinema
	 */
	public static View getEmptyRoomHomeCinema(Context context) {
		TextView text = getTextView(context);
		text.setText(context.getResources().getString(R.string.no_home_cinema_in_room));
		return text;
	}

	/**
	 * Gets the empty room appliance.
	 *
	 * @param context the context
	 * @return the empty room appliance
	 */
	public static View getEmptyRoomAppliance(Context context) {
		TextView text = getTextView(context);
		text.setText(context.getResources().getString(R.string.no_appliance_in_room));
		return text;
	}

	/**
	 * Gets the empty room heating.
	 *
	 * @param context the context
	 * @return the empty room heating
	 */
	public static View getEmptyRoomHeating(Context context) {
		TextView text = getTextView(context);
		text.setText(context.getResources().getString(R.string.no_heating_in_room));
		return text;
	}

	/**
	 * Gets the no network connection.
	 *
	 * @param context the context
	 * @return the no network connection
	 */
	public static View getNoNetworkConnection(Context context) {
		TextView text = getTextView(context);
		text.setText(context.getResources().getString(R.string.no_network));
		return text;
	}

	/**
	 * Gets the text view.
	 *
	 * @param context the context
	 * @return the text view
	 */
	private static TextView getTextView(Context context) {
		TextView text = new TextView(context);
		text.setTextColor(context.getResources().getColor(R.color.White));
		text.setGravity(Gravity.CENTER);
		text.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		text.setTextSize(40);
		return text;
	}

}
