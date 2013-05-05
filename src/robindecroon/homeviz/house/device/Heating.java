/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.house.device;

import robindecroon.homeviz.util.Amount;

/**
 * The Class Heating.
 */
public class Heating extends Consumer {
	
	/** The heating power. */
	private double heatingPower = Math.random();

	/**
	 * Instantiates a new heating.
	 *
	 * @param name the name
	 * @param watt the watt
	 * @param demoMultiplier the demo multiplier
	 */
	public Heating(String name, int watt, int demoMultiplier) {
		super(name, watt, demoMultiplier);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.device.Consumer#getPrice()
	 */
	@Override
	public Amount getPrice() {
		return new Amount(Math.random() * 10);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.device.Consumer#getPower()
	 */
	@Override
	public double getPower() {
		// Should be in kwh, in order to calculate CO2
		return heatingPower;
	}

}
