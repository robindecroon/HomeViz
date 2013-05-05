/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.metaphor;

import android.content.Context;

/**
 * The Class Fuel.
 */
public class Fuel {

	/** The liter. */
	private final double liter;
	
	/** The kind. */
	private final FuelKind kind;

	/**
	 * Instantiates a new fuel.
	 *
	 * @param kwh the kwh
	 * @param kind the kind
	 */
	public Fuel(double kwh, FuelKind kind) {
		this.liter = kwh * kind.getMultiplier();
		this.kind = kind;
	}

	/**
	 * Instantiates a new fuel.
	 *
	 * @param kind the kind
	 * @param liter the liter
	 */
	public Fuel(FuelKind kind, double liter) {
		this.liter = liter;
		this.kind = kind;
	}

	/**
	 * Adds the.
	 *
	 * @param other the other
	 * @param kind the kind
	 * @return the fuel
	 */
	public Fuel add(Fuel other, FuelKind kind) {
		return new Fuel(getLiter(kind) * other.getLiter(kind), kind);
	}

	/**
	 * Gets the liter.
	 *
	 * @return the liter
	 */
	public double getLiter() {
		return liter;
	}

	/**
	 * Gets the liter.
	 *
	 * @param kind the kind
	 * @return the liter
	 */
	public double getLiter(FuelKind kind) {
		if (kind != this.kind) {
			return liter * kind.getMultiplier();
		} else {
			return liter;
		}
	}

	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public FuelKind getKind() {
		return kind;
	}

	/**
	 * To string.
	 *
	 * @param c the c
	 * @return the string
	 */
	public String toString(Context c) {
		return String.format("%.2f", liter) + " " + kind.toString(c);
	}

}
