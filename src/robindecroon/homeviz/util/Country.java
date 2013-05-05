/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.util;

/**
 * The Class Country.
 */
public class Country {

	/** The name. */
	private String name;
	
	/** The co2 value. */
	private double co2Value;
	
	/** The kwh. */
	private Amount kwh;
	
	/** The liter price. */
	private Amount literPrice;

	/**
	 * Instantiates a new country.
	 *
	 * @param name2 the name2
	 * @param co2Value2 the co2 value2
	 * @param kwh2 the kwh2
	 * @param liter the liter
	 */
	public Country(String name2, double co2Value2, Amount kwh2, Amount liter) {
		this.name = name2;
		this.co2Value = co2Value2;
		this.kwh = kwh2;
		this.literPrice = liter;
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the co2 value.
	 *
	 * @return the co2 value
	 */
	public double getCo2Value() {
		return co2Value;
	}

	/**
	 * Sets the co2 value.
	 *
	 * @param co2Value the new co2 value
	 */
	public void setCo2Value(double co2Value) {
		this.co2Value = co2Value;
	}

	/**
	 * Gets the kwh.
	 *
	 * @return the kwh
	 */
	public Amount getKwh() {
		return kwh;
	}

	/**
	 * Sets the kwh.
	 *
	 * @param kwh the new kwh
	 */
	public void setKwh(Amount kwh) {
		this.kwh = kwh;
	}

	/**
	 * Gets the liter price.
	 *
	 * @return the liter price
	 */
	public Amount getLiterPrice() {
		return this.literPrice;
	}

	/**
	 * Sets the liter price.
	 *
	 * @param literPrice the new liter price
	 */
	public void setLiterPrice(Amount literPrice) {
		this.literPrice = literPrice;
	}

}
