package robindecroon.homeviz.house.device;

import android.content.Context;
import robindecroon.homeviz.activities.MainActivity;
import robindecroon.homeviz.util.Amount;

public class Water extends Consumer {

	public Water(String name, Context context) {
		super(name, -1, context);
	}

	@Override
	public Amount getPrice() {
		// return new Amount(getLiter() * getWaterPrice().getEuroValue());
		return new Amount(Math.random()
				* MainActivity.currentPeriod.getMultiplier());
	}

	@Override
	public double getPower() {
		return 0;
	}
}
