/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.util;

import robindecroon.homeviz.R;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class ToastMessages.
 */
public class ToastMessages {

	/** The context. */
	private static Context context;

	/**
	 * Sets the context.
	 *
	 * @param c the new context
	 */
	public static void setContext(Context c) {
		context = c;
	}
	
	/**
	 * Shortcut to resources (readability).
	 *
	 * @return the resources
	 */
	private static Resources res() {
		return context.getResources();
	}

	/**
	 * No file manager.
	 */
	public static void noFileManager() {
		Toast.makeText(context, res().getString(R.string.toast_no_file_manager),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Xml error.
	 */
	public static void xmlError() {
		Toast.makeText(context, res().getString(R.string.toast_wrong_file), 
				Toast.LENGTH_LONG).show();
	}

	/**
	 * No location resource.
	 */
	public static void noLocationResource() {
		Toast.makeText(context, res().getString(R.string.toast_no_location_resource), 
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Enable location.
	 */
	public static void enableLocation() {
		Toast.makeText(context, res().getString(R.string.toast_enable_location),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * No smaller period.
	 */
	public static void noSmallerPeriod() {
		Toast.makeText(context,res().getString(R.string.toast_no_smaller_period), 
				Toast.LENGTH_LONG).show();
	}

	/**
	 * No larger period.
	 */
	public static void noLargerPeriod() {
		Toast.makeText(context, res().getString(R.string.toast_no_larger_period), 
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Long click for time.
	 */
	public static void longClickForTime() {
		Toast.makeText(context, res().getString(R.string.toast_long_click_for_time), 
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Connection error.
	 */
	public static void connectionError() {
		Toast.makeText(context, res().getString(R.string.toast_connection_error), 
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Not too fast.
	 */
	public static void notTooFast() {
		Toast.makeText(context,res().getString(R.string.toast_too_fast),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Data loaded.
	 */
	public static void dataLoaded() {
		Toast.makeText(context,res().getString(R.string.toast_data_loaded),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * No share providers.
	 */
	public static void noShareProviders() {
		Toast.makeText(context, res().getString(R.string.toast_no_share_providers), 
				Toast.LENGTH_LONG).show();
	}

	/**
	 * No external storage.
	 */
	public static void noExternalStorage() {
		Toast.makeText(context, res().getString(R.string.toast_no_external_storage), 
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Community.
	 */
	public static void community() {
		Toast.makeText(context, res().getString(R.string.toast_community),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Invalid period.
	 */
	public static void invalidPeriod() {
		Toast.makeText(context,res().getString(R.string.invalid_period),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Enter room name.
	 */
	public static void enterRoomName() {
		Toast.makeText(context, res().getString(R.string.enter_room_name),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Refreshing statistics.
	 */
	public static void refreshingStatistics() {
		Toast.makeText(context, res().getString(R.string.refreshing_statistics),
				Toast.LENGTH_LONG).show();
	}
}
