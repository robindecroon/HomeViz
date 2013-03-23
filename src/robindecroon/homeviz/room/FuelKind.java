package robindecroon.homeviz.room;

public enum FuelKind {
	
	DIESEL {
		@Override
		public double getMultiplier() {
			return 1/11.1;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "l diesel fuel";
		}		
	}, GAS {

		@Override
		public double getMultiplier() {
			// TODO Auto-generated method stub
			return 1/11.4;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "l gas oil";
		}
		
	}, OIL {

		@Override
		public double getMultiplier() {
			// TODO Auto-generated method stub
			return 1/12.2;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "l fuel oil";
		}
		
	}, GASOLINE {

		@Override
		public double getMultiplier() {
			// TODO Auto-generated method stub
			return 1/9.4;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "l gasoline";
		}
		
	};
	
	public abstract double getMultiplier();
	
	public abstract String toString();
	

}
