package robindecroon.homeviz.visualization;

import java.util.Map;

import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;
import android.content.Context;

public abstract class GoogleChartTools {

	private final static String startDetail = "<html><head><script type=\"text/javascript\" src=\""
			+ "https://www.google.com/jsapi\"></script><script type=\"text/javascript\">"
			+ "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});google."
			+ "setOnLoadCallback(drawChart);function drawChart() {var data = google.visualization."
			+ "arrayToDataTable([";

	private final static String mid1Detail = "]);var options = {backgroundColor: { fill: 'none' }, legend: {position: 'top', textStyle: {color: 'white', fontSize: 16}}, titleTextStyle: {color: 'white'}, hAxis: {textStyle: {color: 'white'}}, vAxis: {textStyle: {color: 'white'}}, ";

	private final static String mid2Detail = "};var chart = new google.visualization.";

	private final static String mid3 = "(document.getElementById('chart_div'));chart.draw(data,"
			+ " options);}</script> </head> <body bgcolor=\"black\"> <div id=\"chart_div\" style=\"width:";

	private final static String mid4 = "px; height: ";

	private final static String end = "px;\"></div></body></html>";

	public static String getUsageViz(String title, Period currentPeriod,
			Context context, Map<String, Amount> map, int width, int height,
			GoogleChartType type) {

		String data = makeData(currentPeriod, context, map);

		// De hoogte moet iets kleiner zijn, anders is er een scrollbar
		return startDetail + data + mid1Detail + "title: '" + title
				+ " in euro'" + mid2Detail + type + mid3 + (width - 10) + mid4
				+ (height - 20) + end;
	}

	private static String makeData(Period currentPeriod, Context context,
			Map<String, Amount> map) {
		String data = "['Period',";

		String[] values = new String[map.size()];
		int i = 0;
		for (String key : map.keySet()) {
			data += "'" + key + "',";
			values[i] = Double.toString(map.get(key).getEuroValue());
			i++;
		}
		data = data.substring(0, data.length() - 1);
		data += "],['" + currentPeriod.getName(context) + "', ";

		for (String value : values) {
			data += value + ",";
		}
		data = data.substring(0, data.length() - 1);
		data += "]";
		return data;
	}
}
