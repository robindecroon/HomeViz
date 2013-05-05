/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.xml;

import java.util.HashSet;
import java.util.Set;

import android.util.Log;

/**
 * The Enum LoxoneXMLOutputType.
 */
public enum LoxoneXMLOutputType {

	/** The Input. */
	Input,
	/** The q. */
	Q,
	/** The aq. */
	AQ,
	/** The Aqp. */
	AQp,
	/** The Aqa. */
	AQa,
	/** The Aq1. */
	AQ1,
	/** The Aq2. */
	AQ2,
	/** The Aq3. */
	AQ3,
	/** The Aq4. */
	AQ4,
	/** The Aq5. */
	AQ5,
	/** The Aq6. */
	AQ6,
	/** The Aq7. */
	AQ7,
	/** The Aq8. */
	AQ8,
	/** The Aq9. */
	AQ9;

	/** The errors. */
	private static Set<String> errors = new HashSet<String>();

	/**
	 * Gets the type.
	 * 
	 * @param string
	 *            the string
	 * @return the type
	 */
	public static LoxoneXMLOutputType getType(String string) {
		for (LoxoneXMLOutputType type : values()) {
			if (type.toString().equals(string)) {
				return type;
			}
		}
		logErrorMessage(string);
		return Q;
	}

	/**
	 * Log error message.
	 * 
	 * @param string
	 *            the string
	 */
	private static void logErrorMessage(String string) {
		if (errors.contains(string)) {
			// do nothing
		} else {
			errors.add(string);
			Log.e("LoxoneXMLOutputType", "type: " + string + " is not supported!");
		}
	}
}
