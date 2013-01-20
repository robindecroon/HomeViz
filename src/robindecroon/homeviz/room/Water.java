package robindecroon.homeviz.room;

import android.util.Log;
import robindecroon.homeviz.exceptions.ParseXMLException;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;

public class Water {
	
	private String name;
	
	private double liter;
	
	private static Amount waterPrice;

	/**
	 * @return the name
	 */
	public String getName() {
		if(name == null) {
			throw new ParseXMLException(this);
		}
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the liter
	 */
	public double getLiter() {
		return liter;
	}

	/**
	 * @param liter the liter to set
	 */
	public void setLiter(double liter) {
		this.liter = liter;
	}

	/**
	 * @return the waterPrice
	 */
	public static Amount getWaterPrice() {
		if (waterPrice == null) {
			Log.e("Light","Belgian prices are used!");
			return new Amount(0.0014);
		}
		return waterPrice;
	}

	/**
	 * @param waterPrice the waterPrice to set
	 */
	public static void setWaterPrice(Amount waterPrice) {
		Water.waterPrice = waterPrice;
	}

	public Amount getPrice(Period currentPeriod) {
		Amount price = new Amount(getLiter() * getWaterPrice().getEuroValue());
		return price.multiply(currentPeriod.getMultiplier()); 
	}

}
