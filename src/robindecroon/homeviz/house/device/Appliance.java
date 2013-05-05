/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.house.device;

import robindecroon.homeviz.util.Amount;

/**
 * The Class Appliance.
 */
public class Appliance extends Electric {

	/**
	 * Instantiates a new appliance.
	 *
	 * @param name the name
	 * @param watt the watt
	 * @param context the context
	 */
	public Appliance(String name, int watt, int demoMultiplier) {
		super(name, watt, demoMultiplier);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.device.Electric#getPrice()
	 */
	@Override
	public Amount getPrice() {
		Amount electricPrice = super.getPrice();
		Amount waterPrice = new Amount(getLiter() * getWaterPrice().getEuroValue());
		return electricPrice.add(waterPrice);
	}

}
