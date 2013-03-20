package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;

public class Appliance extends Consumer {

	public Appliance(String name, int watt) {
		super(name, watt);
	}

	@Override
	public Amount getPrice(Period currentPeriod) {
		double power = getWatt() * getAverageHoursOn() / 1000;
		Amount price1 = new Amount(power).multiply(getKwhPrice());
		Amount price2 = new Amount(getLiter() * getWaterPrice().getEuroValue());
		Amount price = price1.add(price2);

		return price.multiply(currentPeriod.getMultiplier());
	}
}
