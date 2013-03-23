package robindecroon.homeviz.room;

public enum WeightUnit {

	GRAM {
		@Override
		public double getMultiplier() {
			return 1;
		}

		@Override
		public String toString() {
			return "g";
		}
	},
	KILOGRAM {
		@Override
		public double getMultiplier() {
			return 0.001;
		}

		@Override
		public String toString() {
			return "kg";
		}
	};

	public abstract double getMultiplier();

}
