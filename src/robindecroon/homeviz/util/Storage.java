/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.util;

import android.os.Environment;

/**
 * The Class Storage.
 */
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
