package robindecroon.homeviz.room;

public enum WeightUnit {
	
	GRAM {
		public double getMultiplier() {
			return 1;
		}
		
		public String toString() {
			return "g";
		}
	}
	, KILOGRAM {
		public double getMultiplier() {
			return 0.001;
		}
		
		public String toString() {
			return "kg";
		}
	};
	
	public abstract double getMultiplier();
	

}
