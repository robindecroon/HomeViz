/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.IllegalPeriodModification;
import android.content.Context;

/**
 * The Enum Period.
 */
public enum Period {

	/** The day. */
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
	
	/** The week. */
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
	
	/** The month. */
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
	
	/** The year. */
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
	
	/** The custom. */
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
				return context.getResources().getString(R.string.period_custom);
			}
		}
	};

	/** The name id. */
	private final int nameId;

	/** The begin. */
	private GregorianCalendar begin;
	
	/** The end. */
	private GregorianCalendar end;

	/**
	 * Gets the name.
	 *
	 * @param context the context
	 * @return the name
	 */
	public String getName(Context context) {
		return context.getResources().getString(nameId);
	}

	/**
	 * Instantiates a new period.
	 *
	 * @param nameId the name id
	 */
	Period(int nameId) {
		this.nameId = nameId;
		this.end = new GregorianCalendar();
	}

	/**
	 * Sets the begin.
	 *
	 * @param begin the new begin
	 * @throws IllegalPeriodModification the illegal period modification
	 */
	public void setBegin(GregorianCalendar begin)
			throws IllegalPeriodModification {
		if (this == CUSTOM) {
			this.begin = begin;
		} else {
			throw new IllegalPeriodModification(this, "begin");
		}
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 * @throws IllegalPeriodModification the illegal period modification
	 */
	public void setEnd(GregorianCalendar end) throws IllegalPeriodModification {
		if (this == CUSTOM) {
			this.end = end;
		} else {
			throw new IllegalPeriodModification(this, "end");
		}
	}

	/**
	 * Gets the begin.
	 *
	 * @return the begin
	 */
	public GregorianCalendar getBegin() {
		return begin;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public GregorianCalendar getEnd() {
		return end;
	}

	/**
	 * Gets the period.
	 *
	 * @param id the id
	 * @return the period
	 */
	public static Period getPeriod(int id) {
		for (Period period : values()) 
			if (period.getId() == id) return period;
		return Period.CUSTOM;
	}

	/**
	 * Gets the multiplier.
	 *
	 * @return the multiplier
	 */
	public abstract int getMultiplier();

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public abstract int getId();
	
	/**
	 * Previous.
	 *
	 * @return the period
	 */
	public abstract Period previous();

	/**
	 * Next.
	 *
	 * @return the period
	 */
	public abstract Period next();

}
