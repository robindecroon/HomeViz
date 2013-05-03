package robindecroon.homeviz.util;

import robindecroon.homeviz.R;
import android.content.Context;
import android.widget.Toast;

public class ToastMessages {

	private static Context context;

	public static void setContext(Context c) {
		context = c;
	}

	public static void noFileManager() {
		Toast.makeText(
				context,
				context.getResources()
						.getString(R.string.toast_no_file_manager),
				Toast.LENGTH_LONG).show();
	}

	public static void xmlError() {
		Toast.makeText(context,
				context.getResources().getString(R.string.toast_wrong_file),
				Toast.LENGTH_LONG).show();
	}

	public static void noLocationResource() {
		Toast.makeText(
				context,
				context.getResources().getString(
						R.string.toast_no_location_resource), Toast.LENGTH_LONG)
				.show();
	}

	public static void enableLocation() {
		Toast.makeText(
				context,
				context.getResources()
						.getString(R.string.toast_enable_location),
				Toast.LENGTH_LONG).show();
	}

	public static void noSmallerPeriod() {
		Toast.makeText(
				context,
				context.getResources().getString(
						R.string.toast_no_smaller_period), Toast.LENGTH_LONG)
				.show();
	}

	public static void noLargerPeriod() {
		Toast.makeText(
				context,
				context.getResources().getString(
						R.string.toast_no_larger_period), Toast.LENGTH_LONG)
				.show();
	}

	public static void longClickForTime() {
		Toast.makeText(
				context,
				context.getResources().getString(
						R.string.toast_long_click_for_time), Toast.LENGTH_LONG)
				.show();
	}

	public static void connectionError() {
		Toast.makeText(
				context,
				context.getResources().getString(
						R.string.toast_connection_error), Toast.LENGTH_LONG)
				.show();
	}

	public static void notTooFast() {
		Toast.makeText(context,
				context.getResources().getString(R.string.toast_too_fast),
				Toast.LENGTH_LONG).show();
	}

	public static void dataLoaded() {
		Toast.makeText(context,
				context.getResources().getString(R.string.toast_data_loaded),
				Toast.LENGTH_LONG).show();
	}

	public static void noShareProviders() {
		Toast.makeText(
				context,
				context.getResources().getString(
						R.string.toast_no_share_providers), Toast.LENGTH_LONG)
				.show();
	}

	public static void noExternalStorage() {
		Toast.makeText(
				context,
				context.getResources().getString(
						R.string.toast_no_external_storage), Toast.LENGTH_LONG)
				.show();
	}

	public static void community() {
		Toast.makeText(context,
				context.getResources().getString(R.string.toast_community),
				Toast.LENGTH_LONG).show();
	}

	public static void invalidPeriod() {
		Toast.makeText(context,
				context.getResources().getString(R.string.invalid_period),
				Toast.LENGTH_LONG).show();
	}

	public static void enterRoomName() {
		Toast.makeText(context,
				context.getResources().getString(R.string.enter_room_name),
				Toast.LENGTH_LONG).show();
	}

	public static void refreshingStatistics() {
		Toast.makeText(
				context,
				context.getResources()
						.getString(R.string.refreshing_statistics),
				Toast.LENGTH_LONG).show();
	}
}
