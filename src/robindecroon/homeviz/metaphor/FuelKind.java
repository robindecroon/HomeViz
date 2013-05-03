package robindecroon.homeviz.metaphor;

import robindecroon.homeviz.R;
import android.content.Context;

public enum FuelKind {

	DIESEL {
		@Override
		public double getMultiplier() {
			return 1 / 11.1;
		}

		@Override
		public String toString(Context c) {
			// TODO Auto-generated method stub
			return c.getResources().getString(R.string.metaphor_fuel_diesel);
		}
	},
	GAS {

		@Override
		public double getMultiplier() {
			// TODO Auto-generated method stub
			return 1 / 11.4;
		}

		@Override
		public String toString(Context c) {
			// TODO Auto-generated method stub
			return c.getResources().getString(R.string.metaphor_fuel_gas);
		}

	},
	OIL {

		@Override
		public double getMultiplier() {
			// TODO Auto-generated method stub
			return 1 / 12.2;
		}

		@Override
		public String toString(Context c) {
			// TODO Auto-generated method stub
			return c.getResources().getString(R.string.metaphor_fuel_oil);
		}

	},
	GASOLINE {

		@Override
		public double getMultiplier() {
			// TODO Auto-generated method stub
			return 1 / 9.4;
		}

		@Override
		public String toString(Context c) {
			// TODO Auto-generated method stub
			return c.getResources().getString(R.string.metaphor_fuel_gasoline);
		}

	};

	public abstract double getMultiplier();

	public abstract String toString(Context c);

}
