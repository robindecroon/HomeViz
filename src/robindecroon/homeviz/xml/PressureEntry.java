package robindecroon.homeviz.xml;

public class PressureEntry implements IEntry {

	private final long date;
	
	public PressureEntry(long date, long id) {
		this.date = date;
	}
	
	/**
	 * @return the date
	 */
	public long getDate() {
		return this.date;
	}
	
	@Override
	public String toString() {
		return "entry: " + getDate();
	}
}
