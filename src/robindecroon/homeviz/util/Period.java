/**
 * 
 */
package robindecroon.homeviz.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.IllegalPeriodModification;

import android.content.Context;

/**
 * @author Robin
 * 
 */
public enum Period {

	DAY(R.string.period_day) {
		@Override
		public Period previous() {
			ToastMessages.noSmallerPeriod();
			return this;
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

		@Override
		public int getMultiplier() {
			return 1;
		}
	},
	WEEK( R.string.period_week) {
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

		@Override
		public int getMultiplier() {
			return 7;
		}
	},
	MONTH( R.string.period_month) {
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

		@Override
		public int getMultiplier() {
			return 30;
		}
	},
	YEAR( R.string.period_year) {
		@Override
		public Period previous() {
			return MONTH;
		}

		@Override
		public Period next() {
			ToastMessages.noLargerPeriod();
			return this;
		}

		@Override
		public GregorianCalendar begin() {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(Calendar.YEAR, -1);
			return calendar;
		}

		@Override
		public int getMultiplier() {
			return 365;
		}
	},
	CUSTOM(R.string.period_custom) {

		@Override
		public Period previous() {
			return WEEK;
		}

		@Override
		public Period next() {
			return WEEK;
		}

		@Override
		public int getMultiplier() {
			return (int) ((end().getTimeInMillis() - begin().getTimeInMillis()) / (86400000));
		}
	};

	private final int nameId;
	
	private GregorianCalendar begin;
	private GregorianCalendar end;

	/**
	 * @return the name
	 */
	public String getName(Context context) {
		return context.getResources().getString(nameId);
	}

	Period(int nameId) {
		this.nameId = nameId;
		this.end = new GregorianCalendar();
	}
	
	public void setBegin(GregorianCalendar begin) throws IllegalPeriodModification {
		if(this == CUSTOM) {
			this.begin = begin;			
		} else {
			throw new IllegalPeriodModification(this, "begin");
		}
	}
	
	public void setEnd(GregorianCalendar end) throws IllegalPeriodModification {
		if(this == CUSTOM) {
			this.end = end;
		} else {
			throw new IllegalPeriodModification(this,"end");
		}
	}

	public abstract Period previous();

	public abstract Period next();
	
	public GregorianCalendar begin() {
		return begin;
	}
	
	public GregorianCalendar end() {
		return end;
	}
	
	public abstract int getMultiplier();

}
