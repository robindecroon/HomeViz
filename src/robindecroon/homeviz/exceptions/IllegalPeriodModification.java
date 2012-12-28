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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param detailMessage
	 */
	public IllegalPeriodModification(Period period,String method) {
		super("Illegal modification on: " + period + " for method: " + method);
	}

}
