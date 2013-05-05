/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.yield;

import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * The Class GroundWater.
 */
public class GroundWater extends AYield {

	/**
	 * Instantiates a new ground water.
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
	 * @param unit the unit
	 */
	public GroundWater(double total, double current, double today,
			double yesterday, double twoDays, double thisWeek, double lastWeek,
			double thisMonth, double lastMonth, double thisYear,
			double lastYear, String unit) {
		super(total, current, today, yesterday, twoDays, thisWeek, lastWeek,
				thisMonth, lastMonth, thisYear, lastYear);
		GroundWater.unit = unit;
	}

	/** The dummy. */
	private static AYield dummy = newDummy();

	/**
	 * Gets the dummy.
	 *
	 * @param unit the unit
	 * @return the dummy
	 */
	public static AYield getDummy(String unit) {
		GroundWater.unit = unit;
		return dummy;
	}

	/** The unit. */
	private static String unit;

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#unit()
	 */
	@Override
	public String unit() {
		return " " + unit;
	}

	/**
	 * New dummy.
	 *
	 * @return the a yield
	 */
	private static AYield newDummy() {
		Random r = new Random();
		double offset = 0.01;
		double today = r.nextDouble();
		double weekmulti = offset + Days.daysBetween(new DateTime(),
						new DateTime().withDayOfWeek(1)).getDays() * -10;
		double monthmulti = offset+ Days.daysBetween(new DateTime(),
						new DateTime().withDayOfMonth(1)).getDays() * -10;
		double yearmulti = offset+ Days.daysBetween(new DateTime(),
						new DateTime().withDayOfYear(1)).getDays() * -10;

		double week = r.nextDouble();
		double month = r.nextDouble() + week;
		double year = r.nextDouble() + month;

		return new GroundWater(r.nextDouble() * 5000, Math.abs(today - 0.12),
				today, r.nextDouble() * 10, r.nextDouble() * 10, week
						* weekmulti, week * 70, month * monthmulti,
				month * 310, year * yearmulti, year * 3650, unit);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getTotal()
	 */
	@Override
	public String getTotal() {
		return nf.format(total) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getCurrent()
	 */
	@Override
	public String getCurrent() {
		return nf.format(current) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getYesterday()
	 */
	@Override
	public String getYesterday() {
		return nf.format(yesterday) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getTwoDays()
	 */
	@Override
	public String getTwoDays() {
		return nf.format(twoDays) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getThisWeek()
	 */
	@Override
	public String getThisWeek() {
		return nf.format(thisWeek) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getLastWeek()
	 */
	@Override
	public String getLastWeek() {
		return nf.format(lastWeek) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getThisMonth()
	 */
	@Override
	public String getThisMonth() {
		return nf.format(thisMonth) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getLastMonth()
	 */
	@Override
	public String getLastMonth() {
		return nf.format(lastMonth) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getThisYear()
	 */
	@Override
	public String getThisYear() {
		return nf.format(thisYear) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getLastYear()
	 */
	@Override
	public String getLastYear() {
		return nf.format(lastYear) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.yield.AYield#getToday()
	 */
	@Override
	public String getToday() {
		return nf.format(today) + unit();
	}
}
