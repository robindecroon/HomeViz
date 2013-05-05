/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.metaphor;

import java.text.NumberFormat;

import robindecroon.homeviz.util.WeightUnit;

/**
 * The Class CO2.
 */
public class CO2 {

	/** The value. */
	private final double value;
	
	/** The unit. */
	private final WeightUnit unit;

	/**
	 * Instantiates a new c o2.
	 *
	 * @param value the value
	 * @param unit the unit
	 */
	public CO2(double value, WeightUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	/**
	 * Adds the.
	 *
	 * @param other the other
	 * @return the co2
	 */
	public CO2 add(CO2 other) {
		return new CO2((value * unit.getMultiplier())
				+ (other.getValue() * other.getUnit().getMultiplier()), WeightUnit.GRAM);
	}

	/**
	 * Convert to.
	 *
	 * @param unit the unit
	 * @return the co2
	 */
	public CO2 convertTo(WeightUnit unit) {
		return new CO2(value * unit.getMultiplier(), unit);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return NumberFormat.getInstance().format(value) + " " + unit.toString();
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public WeightUnit getUnit() {
		return unit;
	}

}
