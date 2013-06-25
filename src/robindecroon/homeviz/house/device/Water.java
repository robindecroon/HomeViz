/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.house.device;

import robindecroon.homeviz.activities.MainActivity;
import robindecroon.homeviz.util.Amount;

/**
 * The Class Water.
 */
public class Water extends Consumer {
	
	/** The liter. */
	private double liter = Math.random();

	/**
	 * Instantiates a new water.
	 *
	 * @param name the name
	 * @param demoMultiplier the demo multiplier
	 */
	public Water(String name, int demoMultiplier) {
		super(name, -1, demoMultiplier);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.device.Consumer#getPrice()
	 */
	@Override
	public Amount getPrice() {
		return new Amount(liter	* MainActivity.currentPeriod.getMultiplier());
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.device.Consumer#getPower()
	 */
	@Override
	public double getPower() {
		return 0;
	}
	
	@Override
	public double getLiter() {
		return liter * MainActivity.currentPeriod.getMultiplier();
	}
}
