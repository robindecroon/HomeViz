package robindecroon.homeviz.room;

import robindecroon.homeviz.exceptions.ParseXMLException;
import robindecroon.homeviz.util.Amount;

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
		return waterPrice;
	}

	/**
	 * @param waterPrice the waterPrice to set
	 */
	public static void setWaterPrice(Amount waterPrice) {
		Water.waterPrice = waterPrice;
	}

}
