package robindecroon.homeviz.util;

import android.os.Environment;

public abstract class Storage {
	
	/**
	 * Check if external storage is available.
	 *
	 * @return true, if successful
	 */
	public static boolean externalStorageIsAvailable() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

}
