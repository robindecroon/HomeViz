package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Amount;
import android.content.Context;

public class Electric extends Consumer {

	public Electric(String name, int watt, Context context) {
		super(name, watt, context);
	}

	@Override
	public Amount getPrice() {
		return new Amount(getPower()).multiply(getKwhPrice());
	}

	@Override
	public double getPower() {
		return getWatt() * getAverageHoursOn() / 1000;
	}
}