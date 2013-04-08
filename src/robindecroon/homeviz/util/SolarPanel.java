package robindecroon.homeviz.util;

import java.text.NumberFormat;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class SolarPanel {

	private final double total;
	private final double current;
	private final double today;	
	private final double yesterday;
	private final double twoDays;
	private final double thisWeek;
	private final double lastWeek;
	private final double thisMonth;
	private final double lastMonth;
	private final double thisYear;
	private final double lastYear;
	private NumberFormat nf;

	public SolarPanel(double total, double current, double today, double yesterday,
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

		this.nf = NumberFormat.getInstance();
	}

	private static SolarPanel dummy = newDummy();

	public static SolarPanel getDummy() {
		return dummy;
	}

	public static SolarPanel newDummy() {
		Random r = new Random();
		double offset = 0.01;
		double today = r.nextDouble();
		double weekmulti = offset + Days.daysBetween(new DateTime(),
				new DateTime().withDayOfWeek(1)).getDays()
				* -10;
		double monthmulti = offset + Days.daysBetween(new DateTime(),
				new DateTime().withDayOfMonth(1)).getDays()
				* -10;
		double yearmulti = offset + Days.daysBetween(new DateTime(),
				new DateTime().withDayOfYear(1)).getDays()
				* -10;

		double week = r.nextDouble(); 
		double month = r.nextDouble() + week;
		double year = r.nextDouble() + month;

		return new SolarPanel(r.nextDouble() * 5000,  Math.abs(today - 0.12), today,
				r.nextDouble() * 10, r.nextDouble() * 10, week * weekmulti,
				week * 70, month * monthmulti, month * 310, year * yearmulti,
				year * 3650);
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return nf.format(total);
	}

	/**
	 * @return the current
	 */
	public String getCurrent() {
		return nf.format(current);
	}

	/**
	 * @return the yesterday
	 */
	public String getYesterday() {
		return nf.format(yesterday);
	}

	/**
	 * @return the twoDays
	 */
	public String getTwoDays() {
		return nf.format(twoDays);
	}

	/**
	 * @return the thisWeek
	 */
	public String getThisWeek() {
		return nf.format(thisWeek);
	}

	/**
	 * @return the lastWeek
	 */
	public String getLastWeek() {
		return nf.format(lastWeek);
	}

	/**
	 * @return the thisMonth
	 */
	public String getThisMonth() {
		return nf.format(thisMonth);
	}

	/**
	 * @return the lastMonth
	 */
	public String getLastMonth() {
		return nf.format(lastMonth);
	}

	/**
	 * @return the thisYear
	 */
	public String getThisYear() {
		return nf.format(thisYear);
	}

	/**
	 * @return the lastYear
	 */
	public String getLastYear() {
		return nf.format(lastYear);
	}

	public String getToday() {
		return nf.format(today);
	}

}
