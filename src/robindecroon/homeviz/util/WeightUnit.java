/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.util;

/**
 * The Enum WeightUnit.
 */
public enum WeightUnit {

	/** The gram. */
	GRAM {
		@Override
		public double getMultiplier() {
			return 1;
		}

		@Override
		public String toString() {
			return "g";
		}
	},
	
	/** The kilogram. */
	KILOGRAM {
		@Override
		public double getMultiplier() {
			return 0.001;
		}

		@Override
		public String toString() {
			return "kg";
		}
	};

	/**
	 * Gets the multiplier.
	 *
	 * @return the multiplier
	 */
	public abstract double getMultiplier();

}
