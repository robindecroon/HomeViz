package robindecroon.homeviz.xml;

import android.annotation.SuppressLint;
import java.sql.Date;
import java.text.SimpleDateFormat;

import robindecroon.homeviz.util.OutputType;

public class Entry  {

	private final long date;
	private final String value;
	private final OutputType type;

	public Entry(long date, String value, String type) {
		this.date = date;
		this.value = value;
		this.type = OutputType.getType(type);
	}

	public boolean getState() {
		return !value.equals("0.000");
	}

	/**
	 * @return the type
	 */
	public OutputType getType() {
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
	public String getValue() {
		return value;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat();
		return "T=" + df.format(new Date(date)) + " " + getType() + ": "
				+ value;
	}
}
