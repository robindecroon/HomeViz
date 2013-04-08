package robindecroon.homeviz.xml;

import java.util.List;

public class XMLReturnObject {

	private final String name;
	private final List<Entry> entries;
	private final int nbOutputs;

	public XMLReturnObject(String name, List<Entry> entries, int nbOutputs) {
		this.name = name;
		this.entries = entries;
		this.nbOutputs = nbOutputs;
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
	public List<Entry> getEntries() {
		return entries;
	}

	/**
	 * @return the nbOutputs
	 */
	public int getNbOutputs() {
		return nbOutputs;
	}

}
