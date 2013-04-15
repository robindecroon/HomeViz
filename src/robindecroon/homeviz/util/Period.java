/**
 * 
 */
package robindecroon.homeviz.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.IllegalPeriodModification;
import android.content.Context;
import android.util.Log;

/**
 * @author Robin
 * 
 */
public enum Period {

	DAY(R.string.period_day) {
		@Override
		public int getId() {
			return 0;
		}

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
		public GregorianCalendar getBegin() {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			return calendar;
		}

		@Override
		public int getMultiplier() {
			return 1;
		}
	},
	WEEK(R.string.period_week) {
		@Override
		public int getId() {
			return 1;
		}

		@Override
		public Period previous() {
			return DAY;
		}

		@Override
		public Period next() {
			return MONTH;
		}

		@Override
		public GregorianCalendar getBegin() {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(Calendar.WEEK_OF_MONTH, -1);
			return calendar;
		}

		@Override
		public int getMultiplier() {
			return 7;
		}
	},
	MONTH(R.string.period_month) {
		@Override
		public int getId() {
			return 2;
		}

		@Override
		public Period previous() {
			return WEEK;
		}

		@Override
		public Period next() {
			return YEAR;
		}

		@Override
		public GregorianCalendar getBegin() {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(Calendar.MONTH, -1);
			return calendar;
		}

		@Override
		public int getMultiplier() {
			return 30;
		}
	},
	YEAR(R.string.period_year) {
		@Override
		public int getId() {
			return 3;
		}

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
		public GregorianCalendar getBegin() {
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
		public int getId() {
			return 4;
		}

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
			try {
				return (int) ((getEnd().getTimeInMillis() - getBegin()
						.getTimeInMillis()) / (86400000));
			} catch (Exception e) {
				// sometimes android calls this method before the end is set!
				return 0;
			}
		}

		@Override
		public String getName(Context context) {
			DateFormat formater = DateFormat.getDateInstance(DateFormat.SHORT);
			try {
				return formater.format(getBegin().getTime()) + " - "
						+ formater.format(getEnd().getTime());
			} catch (NullPointerException e) {
				Log.e(getClass().getSimpleName(), "No start date!");
				return context.getResources().getString(R.string.period_custom);
			}
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

	public void setBegin(GregorianCalendar begin)
			throws IllegalPeriodModification {
		if (this == CUSTOM) {
			this.begin = begin;
		} else {
			throw new IllegalPeriodModification(this, "begin");
		}
	}

	public void setEnd(GregorianCalendar end) throws IllegalPeriodModification {
		if (this == CUSTOM) {
			this.end = end;
		} else {
			throw new IllegalPeriodModification(this, "end");
		}
	}

	public abstract Period previous();

	public abstract Period next();

	public GregorianCalendar getBegin() {
		return begin;
	}

	public GregorianCalendar getEnd() {
		return end;
	}

	public static Period getPeriod(int id) {
		for (Period period : values()) {
			if (period.getId() == id) {
				return period;
			}
		}
		return Period.CUSTOM;
	}

	public abstract int getMultiplier();

	public abstract int getId();

}
