package robindecroon.homeviz.xml;

public class PressureEntry implements IEntry {

	private final long date;
	private boolean value;

	public PressureEntry(long date, boolean value) {
		this.date = date;
		this.value = value;
	}

	@Override
	public boolean getState() {
		return this.value;
	}

	/**
	 * @return the date
	 */
	@Override
	public long getDate() {
		return this.date;
	}

	@Override
	public String toString() {
		return "entry: " + getDate();
	}
}
