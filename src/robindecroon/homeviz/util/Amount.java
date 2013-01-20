package robindecroon.homeviz.util;

public class Amount {
	
	private enum Currency {
		EURO {
			@Override
			public String toString() {
				return "€";
			}
		}, DOLLAR {
			@Override
			public String toString() {
				return "$";
			}
		};
		
		@Override
		public abstract String toString();
	}
	
	private double valueInEuro;
	private Currency currentCurrency = Currency.EURO;

	public Amount(double euroValue) {
		this.valueInEuro = euroValue;
	}
	
	public Amount(Amount other) {
		this.valueInEuro = other.getEuroValue();
	}
	
	public double getEuroValue() {
		return this.valueInEuro;
	}
	
	@Override
	public String toString(){
		if(currentCurrency == Currency.EURO) {
			return currentCurrency + String.format("%.2f",valueInEuro);			
		} else {
			throw new UnsupportedOperationException("Enkel EURO!");
		}
	}
	
	public Amount add(Amount other) {
		return new Amount(getEuroValue() + other.getEuroValue());
	}
	
	public Amount multiply(int other) {
		return new Amount(getEuroValue() * other);
	}
	

}
