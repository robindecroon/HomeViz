package robindecroon.homeviz.xml;

import java.sql.Date;

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

	@Override
	public String toString() {
		return "T=" + new Date(date).toLocaleString() + " " + getType() + ": "
				+ value;
	}
}
