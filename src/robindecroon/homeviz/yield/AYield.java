/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.yield;

import java.text.NumberFormat;

/**
 * The Class AYield.
 */
public abstract class AYield {

	/** The total. */
	protected final double total;
	
	/** The current. */
	protected final double current;
	
	/** The today. */
	protected final double today;
	
	/** The yesterday. */
	protected final double yesterday;
	
	/** The two days. */
	protected final double twoDays;
	
	/** The this week. */
	protected final double thisWeek;
	
	/** The last week. */
	protected final double lastWeek;
	
	/** The this month. */
	protected final double thisMonth;
	
	/** The last month. */
	protected final double lastMonth;
	
	/** The this year. */
	protected final double thisYear;
	
	/** The last year. */
	protected final double lastYear;
	
	/** The Constant nf. */
	protected final static NumberFormat nf = NumberFormat.getInstance();

	/**
	 * Instantiates a new a yield.
	 *
	 * @param total the total
	 * @param current the current
	 * @param today the today
	 * @param yesterday the yesterday
	 * @param twoDays the two days
	 * @param thisWeek the this week
	 * @param lastWeek the last week
	 * @param thisMonth the this month
	 * @param lastMonth the last month
	 * @param thisYear the this year
	 * @param lastYear the last year
	 */
	public AYield(double total, double current, double today, double yesterday,
			double twoDays, double thisWeek, double lastWeek, double thisMonth,
			double lastMonth, double thisYear, double lastYear) {

		this.total = total;
		this.current = current;
		this.today = today;
		this.yesterday = yesterday;
		this.twoDays = twoDays;
		this.thisWeek = thisWeek;
		this.lastWeek = lastWeek;
		this.thisMonth = thisMonth;
		this.lastMonth = lastMonth;
		this.thisYear = thisYear;
		this.lastYear = lastYear;
	}
	
	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public abstract String getTotal();

	/**
	 * Gets the current.
	 *
	 * @return the current
	 */
	public abstract String getCurrent();

	/**
	 * Gets the yesterday.
	 *
	 * @return the yesterday
	 */
	public abstract String getYesterday();

	/**
	 * Gets the two days.
	 *
	 * @return the two days
	 */
	public abstract String getTwoDays();

	/**
	 * Gets the this week.
	 *
	 * @return the this week
	 */
	public abstract String getThisWeek();

	/**
	 * Gets the last week.
	 *
	 * @return the last week
	 */
	public abstract String getLastWeek();

	/**
	 * Gets the this month.
	 *
	 * @return the this month
	 */
	public abstract String getThisMonth();

	/**
	 * Gets the last month.
	 *
	 * @return the last month
	 */
	public abstract String getLastMonth();

	/**
	 * Gets the this year.
	 *
	 * @return the this year
	 */
	public abstract String getThisYear();

	/**
	 * Gets the last year.
	 *
	 * @return the last year
	 */
	public abstract String getLastYear();

	/**
	 * Gets the today.
	 *
	 * @return the today
	 */
	public abstract String getToday();

	/**
	 * Unit.
	 *
	 * @return the string
	 */
	public abstract String unit();
}