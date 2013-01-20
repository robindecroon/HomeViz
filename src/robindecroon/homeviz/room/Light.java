package robindecroon.homeviz.room;

import robindecroon.homeviz.exceptions.ParseXMLException;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;
import android.util.Log;

public class Light {
	
	private String id;
		
	private int watt;
	
	private double averageHoursOn;
	
	private static Amount kwhPrice;
	/**
	 * @return the id
	 */
	public String getId() {
		if(id == null) {
			throw new ParseXMLException(this);
		}
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Amount getPrice(Period currentPeriod) {
		double power = getWatt() * getAverageHoursOn() / 1000;
		Amount price = new Amount(power).multiply(getKwhPrice());
		return price.multiply(currentPeriod.getMultiplier()); 
	}

	/**
	 * @return the watt
	 */
	public int getWatt() {
		return watt;
	}

	/**
	 * @param watt the watt to set
	 */
	public void setWatt(int watt) {
		this.watt = watt;
	}

	/**
	 * @return the averageMinOn
	 */
	public double getAverageHoursOn() {
		return averageHoursOn;
	}

	/**
	 * @param averageMinOn the averageMinOn to set
	 */
	public void setAverageHoursOn(int averageMinOn) {
		this.averageHoursOn = (double) averageMinOn / 60;
	}

	/**
	 * @return the kwhPrice
	 */
	public static Amount getKwhPrice() {
		if (kwhPrice == null) {
			Log.e("Light","Belgian prices are used!");
			return new Amount(0.2289);
		}
		return kwhPrice;
	}

	/**
	 * @param kwhPrice the kwhPrice to set
	 */
	public static void setKwhPrice(Amount kwhPrice) {
		Light.kwhPrice = kwhPrice;
	}

}
