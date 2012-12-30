/**
 * 
 */
package robindecroon.homeviz.exceptions;

import robindecroon.homeviz.util.Period;

/**
 * @author Robin
 *
 */
public class IllegalPeriodModification extends Exception {
	
	private Period period;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param detailMessage
	 */
	public IllegalPeriodModification(Period period,String method) {
		super("Illegal modification on: " + period + " for method: " + method);
		this.setPeriod(period);
	}

	/**
	 * @return the period
	 */
	public Period getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(Period period) {
		this.period = period;
	}

}
