package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;

public class Heating extends Consumer {

	public Heating(String name, int watt) {
		super(name, watt);
	}

	@Override
	public Amount getPrice(Period currentPeriod) {
		// TODO
		return new Amount(Math.random() * 10);
	}

}
