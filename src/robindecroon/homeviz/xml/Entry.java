package robindecroon.homeviz.xml;

import java.sql.Date;

public class Entry {

	private final long date;
	private final boolean value;
	private final String name;
	private final String type;

	public Entry(long date, boolean value, String name, String type) {
		this.date = date;
		this.value = value;
		this.name = name;
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}

	/**
	 * @return the value
	 */
	public boolean isValue() {
		return value;
	}

	@Override
	public String toString() {
		return "T=" + new Date(date).toLocaleString() + " " + getType() + ": "
				+ value + " in: " + getName();
	}
}
