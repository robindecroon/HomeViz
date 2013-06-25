/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.usage;

import java.util.Map;

import robindecroon.homeviz.activities.MainActivity;
import robindecroon.homeviz.util.Amount;
import android.content.Context;

/**
 * The Class GoogleChartTools.
 */
public abstract class GoogleChartTools {

	/** The first part. */
	private final static String part1 = 
		"<html>" +
			"<head>" +
				"<script type=\"text/javascript\" src=\"file:///android_asset/jsapi.js\"></script>" +
				"<script type=\"text/javascript\">" +
					"google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});" +
					"google.setOnLoadCallback(drawChart);" +
					"function drawChart() {var data = google.visualization.arrayToDataTable([";

	/** The second part. */
	private final static String part2 = 
		"]);" +
		"var options = {" +
			"backgroundColor: { fill: 'none' }, " +
			"legend: {" +
				"position: 'top', " +
				"textStyle: {color: 'white', fontSize: 16}}, " +
				"titleTextStyle: {color: 'white'}, " +
				"hAxis: {textStyle: {color: 'white'}}, " +
				"vAxis: {textStyle: {color: 'white'}}, " +
				"title: '";

	/** The third part. */
	private final static String part3 = 
		"'};" +
		"var chart = new google.visualization.";

	/** The fourth part. */
	private final static String part4 = 
			"(document.getElementById('chart_div'));" +
					"chart.draw(data,options);" +
		"}</script>" +
		" </head>" +
		"	<body bgcolor=\"black\">" +
		"		 <div id=\"chart_div\" style=\"width:";

	/** The fifth part. */
	private final static String part5 = 
		"px; height: ";

	/** The last part. */
	private final static String part6 = 
		"px;\">" +
		"</div>" +
			"</body>" +
				"</html>";

	/**
	 * Gets the google chart tools html.
	 *
	 * @param title the title
	 * @param currentPeriod the current period
	 * @param context the context
	 * @param map the map
	 * @param width the width
	 * @param height the height
	 * @param type the type
	 * @return the google chart tools html
	 */
	public static String getGoogleChartToolsHTML(String title, Context context, 
			Map<String, Amount> map, int width, int height,	GoogleChartType type) {
		String data = makeData(context, map);
		return part1 + data + part2 + title + part3 + type + part4 + (width - 10) + part5 + (height-70) + part6;
	}

	/**
	 * Make data.
	 *
	 * @param context the context
	 * @param map the map
	 * @return the string
	 */
	private static String makeData(Context context,	Map<String, Amount> map) {
		// Prepare the data
		StringBuilder data = new StringBuilder("['Period',");
		
		// Process the keys and save the according values
		String[] values = new String[map.size()];
		int i = 0;
		for (String key : map.keySet()) {
			data.append("'" + key + "',");
			values[i] = Double.toString(map.get(key).getEuroValue());
			i++;
		}
		
		// Append the seperation
		data.append("],['" + MainActivity.currentPeriod.getName(context) + "', ");
		
		// Process the values
		for (String value : values) {
			data.append(value + ",");
		}		
		
		// Finish up
		data.append("]");
		return data.toString();
	}
}
