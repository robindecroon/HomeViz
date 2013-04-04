package robindecroon.homeviz.room;

import java.text.NumberFormat;

public class CO2 {

	private final double value;
	private final WeightUnit unit;

	public CO2(double value, WeightUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	public CO2 add(CO2 other) {
		return new CO2((value * unit.getMultiplier())
				+ (other.getValue() * other.getUnit().getMultiplier()),
				WeightUnit.GRAM);
	}

	public CO2 convertTo(WeightUnit unit) {
		return new CO2(value * unit.getMultiplier(), unit);
	}

	@Override
	public String toString() {
		String format = NumberFormat.getInstance().format(value);
		return format + " " + unit.toString();
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @return the unit
	 */
	public WeightUnit getUnit() {
		return unit;
	}

}
