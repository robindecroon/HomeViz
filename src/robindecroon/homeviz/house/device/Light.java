/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.house.device;

/**
 * The Class Light.
 */
public class Light extends Electric {

	/**
	 * Instantiates a new light.
	 *
	 * @param name the name
	 * @param watt the watt
	 * @param demoMultiplier the demo multiplier
	 */
	public Light(String name, int watt, int demoMultiplier) {
		super(name, watt, demoMultiplier);
	}

}
