package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Period;

/**
 * Klasse die een kamer voorstelt.
 * @author Robin
 *
 */
public class Room {
	
	private double totalCost = Math.random() * 3;
	
	/**
	 * De naam van de kamer.
	 */
	private String name;
	
	/**
	 * Constructor nodig voor de XML parser
	 */
	public Room(){};

	/**
	 * Geef de kamer een naam.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Geef de naam van de kamer terug.
	 * @return the name
	 */
	public String getName() {
		if(name == null)
			// TODO eventueel een exception
			return "Unknown Room";
		return name;
	}

	public CharSequence getTotalPrice(Period currentPeriod) {
		//TODO currency
		return "€" + String.format("%.2f",getTotalCost() * currentPeriod.getMultiplier());
	}

	/**
	 * @return the totalCost
	 */
	public double getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
}
