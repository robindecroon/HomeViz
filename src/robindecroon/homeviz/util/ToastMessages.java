package robindecroon.homeviz.util;

import robindecroon.homeviz.R;
import android.content.Context;
import android.widget.Toast;

public class ToastMessages {
	
	private static Context context;
	
	public static void setContext(Context c) {
		context = c;
	}

	public static void noMoreDetail() {
		Toast.makeText(context,context.getResources().getString(
						R.string.toast_no_more_detail),
				Toast.LENGTH_SHORT).show();
	}
	
	public static void noFileManager() {
		Toast.makeText(context,context.getResources().getString(
						R.string.toast_no_file_manager),
				Toast.LENGTH_LONG).show();
	}
	
	public static void xmlError() {
		Toast.makeText(context,context.getResources().getString(
						R.string.toast_wrong_file),
				Toast.LENGTH_LONG).show();
	}
	
	public static void noSmallerPeriod() {
		Toast.makeText(context,context.getResources().getString(
				R.string.toast_no_smaller_period),
		Toast.LENGTH_LONG).show();
	}
	
	public static void noLargerPeriod() {
		Toast.makeText(context,context.getResources().getString(
				R.string.toast_no_larger_period),
		Toast.LENGTH_LONG).show();
	}

}
