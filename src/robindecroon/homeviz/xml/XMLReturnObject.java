package robindecroon.homeviz.xml;

import java.util.List;

public class XMLReturnObject {

	private final String name;
	private final List<IEntry> entries;

	public XMLReturnObject(String name, List<IEntry> entries) {
		this.name = name;
		this.entries = entries;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the entries
	 */
	public List<IEntry> getEntries() {
		return entries;
	}

}
