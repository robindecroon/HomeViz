/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.exceptions;

import robindecroon.homeviz.util.Period;

/**
 * The Class IllegalPeriodModification.
 *
 * @author Robin
 */
public class IllegalPeriodModification extends Exception {

	/** The period. */
	private Period period;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new illegal period modification.
	 *
	 * @param period the period
	 * @param method the method
	 */
	public IllegalPeriodModification(Period period, String method) {
		super("Illegal modification on: " + period + " for method: " + method);
		this.setPeriod(period);
	}

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	public Period getPeriod() {
		return period;
	}

	/**
	 * Sets the period.
	 *
	 * @param period the period to set
	 */
	public void setPeriod(Period period) {
		this.period = period;
	}

}
