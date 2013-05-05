/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.xml;

import android.annotation.SuppressLint;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * The Class Entry.
 */
public class Entry {

	/** The date. */
	private final long date;
	
	/** The value. */
	private final String value;
	
	/** The type. */
	private final LoxoneXMLOutputType type;

	/**
	 * Instantiates a new entry.
	 *
	 * @param date the date
	 * @param value the value
	 * @param type the type
	 */
	public Entry(long date, String value, String type) {
		this.date = date;
		this.value = value;
		this.type = LoxoneXMLOutputType.getType(type);
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public boolean getState() {
		return !value.equals("0.000");
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public LoxoneXMLOutputType getType() {
		return type;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public long getDate() {
		return date;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@SuppressLint("SimpleDateFormat")
	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat();
		return "T=" + df.format(new Date(date)) + " " + getType() + ": " + value;
	}
}
