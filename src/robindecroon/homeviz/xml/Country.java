package robindecroon.homeviz.xml;

import robindecroon.homeviz.util.Amount;

public class Country {
	
	private String name;
	private double co2Value;
	
	private Amount kwh;
	
	private Amount literPrice;
	
	public Country(String name2, double co2Value2, Amount kwh2) {
		this.name = name2;
		this.co2Value = co2Value2;
		this.kwh = kwh2;
	}
	
	public Country() {}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the co2Value
	 */
	public double getCo2Value() {
		return co2Value;
	}
	/**
	 * @param co2Value the co2Value to set
	 */
	public void setCo2Value(double co2Value) {
		this.co2Value = co2Value;
	}
	/**
	 * @return the kwh
	 */
	public Amount getKwh() {
		return kwh;
	}
	/**
	 * @param kwh the kwh to set
	 */
	public void setKwh(Amount kwh) {
		this.kwh = kwh;
	}
	
	@Override
	public Country clone() {
		return new Country(name,co2Value,kwh);
	}

	public Amount getLiterPrice() {
		return this.literPrice;
	}

	/**
	 * @param literPrice the literPrice to set
	 */
	public void setLiterPrice(Amount literPrice) {
		this.literPrice = literPrice;
	}

}
