package robindecroon.homeviz;

import java.sql.Date;

public class Entry {
	
	public final long date;
	public final boolean value;

	public Entry(long date, boolean value) {
		this.date = date;
		this.value = value;
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
		return "T=" + new Date(date).toLocaleString() + " Q=" + value;
	}
}
