/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.house.device;

import robindecroon.homeviz.util.Amount;

/**
 * The Class Electric.
 */
public class Electric extends Consumer {

	/**
	 * Instantiates a new electric.
	 *
	 * @param name the name
	 * @param watt the watt
	 * @param demoMultiplier the demo multiplier
	 */
	public Electric(String name, int watt, int demoMultiplier) {
		super(name, watt, demoMultiplier);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.device.Consumer#getPrice()
	 */
	@Override
	public Amount getPrice() {
		return new Amount(getPower()).multiply(getKwhPrice());
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.device.Consumer#getPower()
	 */
	@Override
	public double getPower() {
		return getWatt() * getAverageHoursOn() / 1000;
	}
}