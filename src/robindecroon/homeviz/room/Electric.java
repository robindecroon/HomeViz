package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;


public class Electric extends Consumer {
	
	@Override
	public Amount getPrice(Period currentPeriod) {
		double power = getWatt() * getAverageHoursOn() / 1000;
		Amount price = new Amount(power).multiply(getKwhPrice());
		return price.multiply(currentPeriod.getMultiplier()); 
	}

}