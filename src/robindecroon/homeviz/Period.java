/**
 * 
 */
package robindecroon.homeviz;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;

/**
 * @author Robin
 * 
 */
public enum Period {

	DAY("Day", R.string.period_day) {
		@Override
		public Period previous() {
			return null;
		}

		@Override
		public Period next() {
			return WEEK;
		}

		@Override
		public GregorianCalendar begin() {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			return calendar;
		}
	},
	WEEK("Week", R.string.period_week) {
		@Override
		public Period previous() {
			return DAY;
		}

		@Override
		public Period next() {
			return MONTH;
		}

		@Override
		public GregorianCalendar begin() {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(Calendar.WEEK_OF_MONTH, -1);
			return calendar;
		}
	},
	MONTH("Month", R.string.period_month) {
		@Override
		public Period previous() {
			return WEEK;
		}

		@Override
		public Period next() {
			return YEAR;
		}

		@Override
		public GregorianCalendar begin() {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(Calendar.MONTH, -1);
			return calendar;
		}
	},
	YEAR("Year", R.string.period_year) {
		@Override
		public Period previous() {
			return MONTH;
		}

		@Override
		public Period next() {
			return null;
		}

		@Override
		public GregorianCalendar begin() {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(Calendar.YEAR, -1);
			return calendar;
		}
	},
	CUSTOM("Custom",R.string.period_custom) {

		@Override
		public Period previous() {
			return WEEK;
		}

		@Override
		public Period next() {
			return WEEK;
		}
	};

	private final String name;

	private final int nameId;
	
	private GregorianCalendar begin;
	private GregorianCalendar end;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the nameId
	 */
	public int getNameId() {
		return nameId;
	}

	Period(String name, int nameId) {
		this.name = name;
		this.nameId = nameId;
		this.end = new GregorianCalendar();
	}
	
	public void setBegin(GregorianCalendar begin) {
		this.begin = begin;
	}
	
	public void setEnd(GregorianCalendar end) {
		this.end = end;
	}

	public abstract Period previous();

	public abstract Period next();
	
	public GregorianCalendar begin() {
		return begin;
	}
	
	public GregorianCalendar end() {
		return end;
	}

	public String getCurrentName(Context context) {
		return context.getResources().getString(nameId);
	}

}
