package robindecroon.homeviz.xml;

import android.annotation.SuppressLint;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Entry implements IEntry {

	private final long date;
	private final String value;
	// private final String name;
	private final String type;

	public Entry(long date, String value, String type) {
		this.date = date;
		this.value = value;
		// this.name = name;
		this.type = type;
	}

	@Override
	public boolean getState() {
		return !value.equals("0.000");
	}

	// /**
	// * @return the name
	// */
	// public String getName() {
	// return name;
	// }

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the date
	 */
	@Override
	public long getDate() {
		return date;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	public boolean getBoolean() {
		return !value.equals("0.000");
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat();
		return "T=" + df.format(new Date(date)) + " " + getType() + ": "
				+ value;
	}
}
