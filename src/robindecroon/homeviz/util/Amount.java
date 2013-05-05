/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import android.util.Log;

/**
 * The Class Amount.
 */
public class Amount {

	/**
	 * The Enum Currency.
	 */
	private enum Currency {
		
		/** The euro. */
		EURO {
			@Override
			public String toString() {
				return "€";
			}
		},
		
		/** The dollar. */
		DOLLAR {
			@Override
			public String toString() {
				return "$";
			}
		};

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public abstract String toString();
	}

	/** The value in euro. */
	private double valueInEuro;
	
	/** The current currency. */
	private Currency currentCurrency = Currency.EURO;

	/**
	 * Instantiates a new amount.
	 *
	 * @param euroValue the euro value
	 */
	public Amount(double euroValue) {
		this.valueInEuro = euroValue;
	}

	/**
	 * Instantiates a new amount.
	 *
	 * @param other the other
	 */
	public Amount(Amount other) {
		this.valueInEuro = other.getEuroValue();
	}

	/**
	 * Instantiates a new amount.
	 *
	 * @param stringValue the string value
	 */
	public Amount(String stringValue) {
		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
		Number number = null;
		try {
			number = format.parse(stringValue);
			double d = number.doubleValue();
			this.valueInEuro = d;
		} catch (ParseException e) {
			Log.e(getClass().getSimpleName(), "Error in country CSV file!");
		}
	}

	/**
	 * Gets the euro value.
	 *
	 * @return the euro value
	 */
	public double getEuroValue() {
		return this.valueInEuro;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (currentCurrency == Currency.EURO) {
			return currentCurrency + String.format("%.2f", valueInEuro);
		} else {
			throw new UnsupportedOperationException("Only euros!");
		}
	}

	/**
	 * Adds the.
	 *
	 * @param other the other
	 * @return the amount
	 */
	public Amount add(Amount other) {
		return new Amount(getEuroValue() + other.getEuroValue());
	}

	/**
	 * Multiply.
	 *
	 * @param multiplier the multiplier
	 * @return the amount
	 */
	public Amount multiply(int multiplier) {
		return new Amount(getEuroValue() * multiplier);
	}

	/**
	 * Multiply.
	 *
	 * @param other the other
	 * @return the amount
	 */
	public Amount multiply(Amount other) {
		return new Amount(getEuroValue() * other.getEuroValue());
	}
}
