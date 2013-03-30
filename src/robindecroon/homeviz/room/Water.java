package robindecroon.homeviz.room;

import android.content.Context;
import robindecroon.homeviz.util.Amount;

public class Water extends Consumer {

	public Water(String name, Context context) {
		super(name, -1, context);
	}

	@Override
	public Amount getPrice() {
		return new Amount(getLiter() * getWaterPrice().getEuroValue());
	}
}
