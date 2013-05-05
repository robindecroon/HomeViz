/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.xml;

import java.util.List;

/**
 * The Class XMLReturnObject.
 */
public class XMLReturnObject {

	/** The name. */
	private final String name;
	
	/** The entries. */
	private final List<Entry> entries;
	
	/** The nb outputs. */
	private final int nbOutputs;

	/**
	 * Instantiates a new xML return object.
	 *
	 * @param name the name
	 * @param entries the entries
	 * @param nbOutputs the nb outputs
	 */
	public XMLReturnObject(String name, List<Entry> entries, int nbOutputs) {
		this.name = name;
		this.entries = entries;
		this.nbOutputs = nbOutputs;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the entries.
	 *
	 * @return the entries
	 */
	public List<Entry> getEntries() {
		return entries;
	}

	/**
	 * Gets the nb outputs.
	 *
	 * @return the nb outputs
	 */
	public int getNbOutputs() {
		return nbOutputs;
	}

}
