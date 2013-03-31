package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Amount;
import android.content.Context;

public class Heating extends Consumer {

	public Heating(String name, int watt, Context context) {
		super(name, watt, context);
	}

	@Override
	public Amount getPrice() {
		// TODO
		return new Amount(Math.random() * 10);
	}

	@Override
	public double getPower() {
		// TODO in kwh!! Voor fuel te berekenen
		return Math.random();
	}

}
