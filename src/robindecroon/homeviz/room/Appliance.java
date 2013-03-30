package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Amount;
import android.content.Context;

public class Appliance extends Consumer {

	public Appliance(String name, int watt, Context context) {
		super(name, watt, context);
	}

	@Override
	public Amount getPrice() {
		double power = getWatt() * getAverageHoursOn() / 1000;
		Amount price1 = new Amount(power).multiply(getKwhPrice());
		Amount price2 = new Amount(getLiter() * getWaterPrice().getEuroValue());
		return price1.add(price2);
	}
}
