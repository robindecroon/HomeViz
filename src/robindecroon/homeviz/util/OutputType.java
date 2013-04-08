package robindecroon.homeviz.util;

import java.util.HashSet;
import java.util.Set;

import android.util.Log;


public enum OutputType {
	Input, Q, AQ, AQp, AQa, AQ1, AQ2, AQ3, AQ4, AQ5, AQ6, AQ7, AQ8, AQ9;

	private static Set<String> errors = new HashSet<String>();

	public static OutputType getType(String string) {
		for (OutputType type : values()) {
			if (type.toString().equals(string)) {
				return type;
			}
		}
		logErrorMessage(string);
		return Q;
	}
	
	private static void logErrorMessage(String string) {
		if(errors.contains(string)) {
			// do nothing
		} else {
			errors.add(string);
			Log.e("OutputType", "type: " + string + " is not supported!");
		}
	}
}
