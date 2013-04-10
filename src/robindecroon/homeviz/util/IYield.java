package robindecroon.homeviz.util;

public interface IYield {
		
	/**
	 * @return the total
	 */
	public abstract String getTotal();

	/**
	 * @return the current
	 */
	public abstract String getCurrent();

	/**
	 * @return the yesterday
	 */
	public abstract String getYesterday();

	/**
	 * @return the twoDays
	 */
	public abstract String getTwoDays();

	/**
	 * @return the thisWeek
	 */
	public abstract String getThisWeek();

	/**
	 * @return the lastWeek
	 */
	public abstract String getLastWeek();

	/**
	 * @return the thisMonth
	 */
	public abstract String getThisMonth();

	/**
	 * @return the lastMonth
	 */
	public abstract String getLastMonth();

	/**
	 * @return the thisYear
	 */
	public abstract String getThisYear();

	/**
	 * @return the lastYear
	 */
	public abstract String getLastYear();

	/**
	 * @return the today
	 */
	public abstract String getToday();
	
	/**
	 * @return the unit
	 */
	public abstract String unit();

}
