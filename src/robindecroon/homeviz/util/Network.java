package robindecroon.homeviz.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {

	/**
	 * Methode die nagaat of de gebruiker met internet verbonden is.
	 * 
	 * @return True als de gebruiker met internet verbonden is.
	 */
	public static boolean isNetworkConnected(Context c) {
		ConnectivityManager connectivityManager = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
			// // wel actieve netwerken
			// return true;
		}

		return false;
	}

}
