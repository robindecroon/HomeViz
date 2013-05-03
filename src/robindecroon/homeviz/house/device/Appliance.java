package robindecroon.homeviz.house.device;

import robindecroon.homeviz.util.Amount;
import android.content.Context;

public class Appliance extends Electric {

	public Appliance(String name, int watt, Context context) {
		super(name, watt, context);
	}

	@Override
	public Amount getPrice() {
		Amount electricPrice = super.getPrice();
		Amount price2 = new Amount(getLiter() * getWaterPrice().getEuroValue());
		return electricPrice.add(price2);
	}

}
