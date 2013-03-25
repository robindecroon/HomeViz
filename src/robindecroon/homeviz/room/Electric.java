package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;
import android.content.Context;
import android.util.Log;

public class Electric extends Consumer {

	public Electric(String name, int watt, Context context) {
		super(name, watt, context);
	}

	@Override
	public Amount getPrice(Period currentPeriod) {
		Amount price = new Amount(getPower()).multiply(getKwhPrice());
		return price.multiply(currentPeriod.getMultiplier());
	}

	public double getPower() {
		return getWatt() * getAverageHoursOn() / 1000;
	}

	public double getPowerUsage(Period period) {
		return getPower() * period.getMultiplier();
	}

}