package robindecroon.homeviz.util;

import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class SolarPanel extends AYield implements IYield {


	public SolarPanel(double total, double current, double today,
			double yesterday, double twoDays, double thisWeek, double lastWeek,
			double thisMonth, double lastMonth, double thisYear, double lastYear, String unit) {
		super(total, current, today, yesterday, twoDays, thisWeek, lastWeek, thisMonth,
				lastMonth, thisYear, lastYear);
		SolarPanel.unit = unit;
	}
	
	private static AYield dummy = newDummy();

	
	public static AYield getDummy(String unit) {
		SolarPanel.unit = unit;
		return dummy;
	}
	
	private static String unit;
	@Override
	public String unit() {
		return " " + unit; 
	}

	private static AYield newDummy() {
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
				year * 3650, unit);
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getTotal()
	 */
	@Override
	public String getTotal() {
		return nf.format(total) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getCurrent()
	 */
	@Override
	public String getCurrent() {
		return nf.format(current) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getYesterday()
	 */
	@Override
	public String getYesterday() {
		return nf.format(yesterday) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getTwoDays()
	 */
	@Override
	public String getTwoDays() {
		return nf.format(twoDays) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getThisWeek()
	 */
	@Override
	public String getThisWeek() {
		return nf.format(thisWeek) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getLastWeek()
	 */
	@Override
	public String getLastWeek() {
		return nf.format(lastWeek) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getThisMonth()
	 */
	@Override
	public String getThisMonth() {
		return nf.format(thisMonth) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getLastMonth()
	 */
	@Override
	public String getLastMonth() {
		return nf.format(lastMonth) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getThisYear()
	 */
	@Override
	public String getThisYear() {
		return nf.format(thisYear) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getLastYear()
	 */
	@Override
	public String getLastYear() {
		return nf.format(lastYear) + unit();
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.util.AYield#getToday()
	 */
	@Override
	public String getToday() {
		return nf.format(today) + unit();
	}

}
