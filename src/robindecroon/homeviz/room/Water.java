package robindecroon.homeviz.room;

import android.content.Context;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;

public class Water extends Consumer {

	public Water(String name, Context context) {
		super(name, -1, context);
	}

	@Override
	public Amount getPrice(Period currentPeriod) {
		Amount price = new Amount(getLiter() * getWaterPrice().getEuroValue());
		return price.multiply(currentPeriod.getMultiplier());
	}
}
