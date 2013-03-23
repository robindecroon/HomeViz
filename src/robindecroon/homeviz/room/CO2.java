package robindecroon.homeviz.room;

public class CO2 {

	private final double value;
	private final WeightUnit unit;

	public CO2(double value, WeightUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	public CO2 add(CO2 other) {
		return new CO2((value * unit.getMultiplier())
				* (other.getValue() * other.getUnit().getMultiplier()),
				WeightUnit.GRAM);
	}

	public CO2 convertTo(WeightUnit unit) {
		return new CO2(value * unit.getMultiplier(), unit);
	}

	@Override
	public String toString() {
		return String.format("%.2f", value) + " " + unit.toString();
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
