package robindecroon.homeviz.room;

import android.content.Context;

public class Fuel {

	private final double liter;
	private final FuelKind kind;

	public Fuel(double kwh, FuelKind kind) {
		this.liter = kwh * kind.getMultiplier();
		this.kind = kind;
	}

	public Fuel(FuelKind kind, double liter) {
		this.liter = liter;
		this.kind = kind;
	}

	public Fuel add(Fuel other, FuelKind kind) {
		return new Fuel(getLiter(kind) * other.getLiter(kind), kind);
	}

	/**
	 * @return the liter
	 */
	public double getLiter() {
		return liter;
	}

	/**
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
	 * @return the kind
	 */
	public FuelKind getKind() {
		return kind;
	}

	public String toString(Context c) {
		return String.format("%.2f", liter) + " " + kind.toString(c);
	}

}
