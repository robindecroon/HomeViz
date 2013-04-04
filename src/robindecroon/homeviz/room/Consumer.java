package robindecroon.homeviz.room;

import java.util.List;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.Main;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.xml.IEntry;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public abstract class Consumer {

	private static Amount kwhPrice;
	private static Amount waterPrice;
	private static double co2Value;

	private String name;

	private int watt;
	private double averageHoursOn;
	private double liter;

	private List<IEntry> entries;
	private Context context;

	public Consumer(String name, int watt, Context context) {
		this.name = name;
		this.watt = watt;
		this.context = context;
		this.averageHoursOn = Math.random();

	}

	/**
	 * @return the liter
	 */
	public double getLiter() {
		return liter * Main.currentPeriod.getMultiplier();
	}

	/**
	 * @param liter
	 *            the liter to set
	 */
	public void setLiter(double liter) {
		this.liter = liter;
	}

	/**
	 * @return the watt
	 */
	public int getWatt() {
		return watt;
	}

	/**
	 * @param watt
	 *            the watt to set
	 */
	public void setWatt(int watt) {
		this.watt = watt;
	}

	/**
	 * @return the averageMinOn
	 */
	public double getAverageHoursOn() {
		Period period = Main.currentPeriod;
		try {
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(context);
			int multiplier = Integer.valueOf(sp.getString("demo_multiplier",
					"1"));

			long totalMillisOn = 0;

			long end = period.getEnd().getTimeInMillis();
			long begin = period.getBegin().getTimeInMillis();

			int startI = 0;
			int endI = 0;
			for (int i = 1; i < entries.size(); i++) {
				IEntry entry = entries.get(i);
				if (entry.getDate() >= begin) {
					startI = i;
					break;
				}
			}
			if (startI == 0) {
				return 0;
			}
			for (int j = startI; j < entries.size(); j++) {
				IEntry entry = entries.get(j);
				if (entry.getDate() > end) {
					endI = j - 1;
					break;
				}
			}
			if (endI == 0)
				endI = entries.size() - 1;

			long start = entries.get(startI).getDate();
			for (int k = startI + 1; k <= endI; k++) {

				IEntry entry = entries.get(k);
				if (!entry.getState()) {
					totalMillisOn += entry.getDate() - start;
				} else {
					start = entry.getDate();
				}
			}
			return (totalMillisOn * multiplier) / 3600000.0;
		} catch (Exception e) {
			return averageHoursOn * period.getMultiplier();
		}
	}

	/**
	 * @return the kwhPrice
	 */
	public static Amount getKwhPrice() {
		if (kwhPrice == null) {
			// Log.e("Light","Belgian prices are used!");
			return new Amount(0.2289);
		}
		return kwhPrice;
	}

	/**
	 * @param kwhPrice
	 *            the kwhPrice to set
	 */
	public static void setKwhPrice(Amount kwhPrice) {
		Consumer.kwhPrice = kwhPrice;
	}

	/**
	 * @return the waterPrice
	 */
	public static Amount getWaterPrice() {
		if (waterPrice == null) {
			// Log.e("Light","Belgian prices are used!");
			return new Amount(0.0014);
		}
		return waterPrice;
	}

	/**
	 * @param waterPrice
	 *            the waterPrice to set
	 */
	public static void setWaterPrice(Amount waterPrice) {
		Consumer.waterPrice = waterPrice;
	}

	/**
	 * @return the id
	 */
	public String getName() {
		if (name == null) {
			throw new IllegalStateException("A consumer without name");
		}
		return name;
	}

	/**
	 * @param name
	 *            the id to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public abstract Amount getPrice();

	@Override
	public String toString() {
		return getName();
	}

	public static void setCO2Value(double co2Value) {
		Consumer.co2Value = co2Value;

	}

	public CO2 getCO2Value() {
		if (co2Value == 0) {
			return new CO2(getPower() * Constants.BELGIAN_CO2, WeightUnit.GRAM);
		}
		return new CO2(getPower() * co2Value, WeightUnit.GRAM);
	}

	public Fuel getFuel() {
		return new Fuel(getPower(), FuelKind.DIESEL);
	}

	public void putEntries(List<IEntry> list) {
		this.entries = list;
	}

	public abstract double getPower();

}