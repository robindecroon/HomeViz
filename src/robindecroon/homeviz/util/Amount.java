package robindecroon.homeviz.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import android.util.Log;

public class Amount {

	private enum Currency {
		EURO {
			@Override
			public String toString() {
				return "€";
			}
		},
		DOLLAR {
			@Override
			public String toString() {
				return "$";
			}
		};

		@Override
		public abstract String toString();
	}

	private double valueInEuro;
	private Currency currentCurrency = Currency.EURO;

	public Amount(double euroValue) {
		this.valueInEuro = euroValue;
	}

	public Amount(Amount other) {
		this.valueInEuro = other.getEuroValue();
	}

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

	public double getEuroValue() {
		return this.valueInEuro;
	}

	@Override
	public String toString() {
		if (currentCurrency == Currency.EURO) {
			return currentCurrency + String.format("%.2f", valueInEuro);
		} else {
			throw new UnsupportedOperationException("Only euros!");
		}
	}

	public Amount add(Amount other) {
		return new Amount(getEuroValue() + other.getEuroValue());
	}

	public Amount multiply(int other) {
		return new Amount(getEuroValue() * other);
	}

	public Amount multiply(Amount other) {
		return new Amount(getEuroValue() * other.getEuroValue());
	}
}
