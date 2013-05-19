/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.house.device;

import java.util.List;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.activities.MainActivity;
import robindecroon.homeviz.metaphor.CO2;
import robindecroon.homeviz.metaphor.Fuel;
import robindecroon.homeviz.metaphor.FuelKind;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.WeightUnit;
import robindecroon.homeviz.xml.Entry;
import robindecroon.homeviz.xml.XMLSerializable;

/**
 * The Class Consumer.
 */
public abstract class Consumer implements XMLSerializable {

	/** The kwh price. */
	private static Amount kwhPrice = Constants.DEFAULT_KWH_PRICE;
	
	/** The water price. */
	private static Amount waterPrice = Constants.DEFAULT_WATER_PRICE;
	
	/** The co2 value. */
	private static double co2Value = Constants.DEFAULT_CO2;

	/** The consumer name. */
	private String consumerName;

	/** The watt. */
	private int watt = 0;
	
	/** The liter. */
	private double liter = 0;
	
	/** The demo multiplier. */
	private int demoMultiplier = 1;

	/** The entries. */
	private List<Entry> entries;
	
	/** The random average hours on. */
	// should be determined only once for each consumer
	private double randomAverageHoursOn = Math.random();

	/**
	 * Instantiates a new consumer.
	 *
	 * @param name the name
	 * @param watt the watt
	 * @param demoMultiplier the demo multiplier
	 */
	public Consumer(String name, int watt, int demoMultiplier) {
		this.consumerName = name;
		this.watt = watt;
		this.demoMultiplier = demoMultiplier;
	}

	/**
	 * To xml.
	 *
	 * @return the string
	 */
	@Override
	public String toXML() {
		return "<" + getClass().getSimpleName() + " consumerName=\"" + getName() 
				+ "\" watt=\"" + getWatt() + "\" />";
	}

	/**
	 * Gets the liter.
	 *
	 * @return the liter
	 */
	public double getLiter() {
		return liter * MainActivity.currentPeriod.getMultiplier();
	}

	/**
	 * Sets the liter.
	 *
	 * @param liter the new liter
	 */
	public void setLiter(double liter) {
		this.liter = liter;
	}

	/**
	 * Gets the watt.
	 *
	 * @return the watt
	 */
	public int getWatt() {
		return watt;
	}

	/**
	 * Sets the watt.
	 *
	 * @param watt the new watt
	 */
	public void setWatt(int watt) {
		this.watt = watt;
	}

	/**
	 * Gets the average hours on.
	 *
	 * @return the average hours on
	 */
	public double getAverageHoursOn() {
		Period period = MainActivity.currentPeriod;
		try {
			// Determine the first entry for the chosen period
			long begin = period.getBegin().getTimeInMillis();
			int startI = 0;
			for (int i = 1; i < entries.size(); i++) {
				Entry entry = entries.get(i);
				if (entry.getDate() >= begin) {
					startI = i;
					break;
				}
			}
			// This consumer was not on in the chosen time period
			if (startI == 0) return 0;
			
			// Determine the last entry for the chosen period
			long end = period.getEnd().getTimeInMillis();
			int endI = 0;
			for (int j = startI; j < entries.size(); j++) {
				Entry entry = entries.get(j);
				if (entry.getDate() > end) {
					endI = j - 1;
					break;
				}
			}
			// If no last entry is found, calculate all entries until the last one
			if (endI == 0) endI = entries.size() - 1;

			// calculate the total milliseconds this device was on
			long start = entries.get(startI).getDate();
			long totalMillisOn = 0;
			for (int k = startI + 1; k <= endI; k++) {
				Entry entry = entries.get(k);
				if (!entry.getState()) {
					totalMillisOn += entry.getDate() - start;
				} else {
					start = entry.getDate();
				}
			}
			
			// return the calculate time this consumer was on
			return (totalMillisOn * demoMultiplier) / 3600000.0;
		} catch (Exception e) {
			// no entries were found for this consumer, a random value is returned.
			return randomAverageHoursOn * period.getMultiplier();
		}
	}

	/**
	 * Gets the kwh price.
	 *
	 * @return the kwh price
	 */
	public static Amount getKwhPrice() {
		return kwhPrice;
	}

	/**
	 * Sets the kwh price.
	 *
	 * @param kwhPrice the new kwh price
	 */
	public static void setKwhPrice(Amount kwhPrice) {
		Consumer.kwhPrice = kwhPrice;
	}
	
	/**
	 * Gets the water price.
	 *
	 * @return the water price
	 */
	public static Amount getWaterPrice() {
		return waterPrice;
	}

	/**
	 * Sets the water price.
	 *
	 * @param waterPrice the new water price
	 */
	public static void setWaterPrice(Amount waterPrice) {
		Consumer.waterPrice = waterPrice;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return consumerName;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.consumerName = name;
	}

	/**
	 * Sets the c o2 value.
	 *
	 * @param co2Value the new c o2 value
	 */
	public static void setCO2Value(double co2Value) {
		Consumer.co2Value = co2Value;
	}

	/**
	 * Gets the c o2 value.
	 *
	 * @return the c o2 value
	 */
	public CO2 getCO2Value() {
		return new CO2(getPower() * co2Value, WeightUnit.GRAM);
	}

	/**
	 * Gets the fuel.
	 *
	 * @return the fuel
	 */
	public Fuel getFuel() {
		return new Fuel(getPower(), FuelKind.DIESEL);
	}

	/**
	 * Put entries.
	 *
	 * @param list the list
	 */
	public void putEntries(List<Entry> list) {
		this.entries = list;
	}
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public abstract Amount getPrice();

	/**
	 * Gets the power.
	 *
	 * @return the power
	 */
	public abstract double getPower();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}
}