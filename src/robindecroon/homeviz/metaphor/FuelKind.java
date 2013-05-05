/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.metaphor;

import robindecroon.homeviz.R;
import android.content.Context;

/**
 * The Enum FuelKind.
 */
public enum FuelKind {

	/** The diesel. */
	DIESEL {
		@Override
		public double getMultiplier() {
			return 1 / 11.1;
		}

		@Override
		public String toString(Context c) {
			return c.getResources().getString(R.string.metaphor_fuel_diesel);
		}
	},
	
	/** The gas. */
	GAS {

		@Override
		public double getMultiplier() {
			return 1 / 11.4;
		}

		@Override
		public String toString(Context c) {
			return c.getResources().getString(R.string.metaphor_fuel_gas);
		}

	},
	
	/** The oil. */
	OIL {

		@Override
		public double getMultiplier() {
			return 1 / 12.2;
		}

		@Override
		public String toString(Context c) {
			return c.getResources().getString(R.string.metaphor_fuel_oil);
		}

	},
	
	/** The gasoline. */
	GASOLINE {

		@Override
		public double getMultiplier() {
			return 1 / 9.4;
		}

		@Override
		public String toString(Context c) {
			return c.getResources().getString(R.string.metaphor_fuel_gasoline);
		}

	};

	/**
	 * Gets the multiplier.
	 *
	 * @return the multiplier
	 */
	public abstract double getMultiplier();

	/**
	 * To string.
	 *
	 * @param c the c
	 * @return the string
	 */
	public abstract String toString(Context c);

}
