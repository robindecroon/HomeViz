/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.listeners;

import java.util.GregorianCalendar;

/**
 * The listener interface for receiving datePicker events.
 * The class that is interested in processing a datePicker
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDatePickerListener<code> method. When
 * the datePicker event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DatePickerEvent
 */
public interface DatePickerListener {

	/**
	 * Update.
	 *
	 * @param gregorianCalendar the gregorian calendar
	 * @param string the string
	 */
	public void update(GregorianCalendar gregorianCalendar, String string);
}
