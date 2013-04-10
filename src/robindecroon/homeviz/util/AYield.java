package robindecroon.homeviz.util;

import java.text.NumberFormat;

public abstract class AYield implements IYield {
	
	protected final double total;
	protected final double current;
	protected final double today;	
	protected final double yesterday;
	protected final double twoDays;
	protected final double thisWeek;
	protected final double lastWeek;
	protected final double thisMonth;
	protected final double lastMonth;
	protected final double thisYear;
	protected final double lastYear;
	protected NumberFormat nf;

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

		this.nf = NumberFormat.getInstance();
	}
}