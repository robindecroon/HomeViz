package robindecroon.homeviz.visualization;

import robindecroon.homeviz.util.Period;
import android.content.Context;

public abstract class GoogleChartTools {

	private final static String start = "<html><head><script type=\"text/javascript\" src=\"" +
			"https://www.google.com/jsapi\"></script><script type=\"text/javascript\">"
			+ "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});google." +
			"setOnLoadCallback(drawChart);function drawChart() {var data = google.visualization." +
			"arrayToDataTable([";

	private final static String mid1 = "]);var options = {";

	private final static String mid2 = "};var chart = new google.visualization.";

	private final static String mid3 = "(document.getElementById('chart_div'));chart.draw(data," +
			" options);}</script> </head> <body> <div id=\"chart_div\" style=\"width:";

	private final static String mid4 = "px; height: ";

	private final static String end = "px;\"></div></body></html>";

	public static String getUsageViz(String title, Period currentPeriod,
			Context context, String[] values, int width, int height, GoogleChartType type) {
		String data = "['Period', 'Light', 'Water', 'Heating', 'Appliances','Home Cinema'],['"
				+ currentPeriod.getName(context) + "', ";
		for (String value : values) {
			data += value + ",";
		}
		data = data.substring(0, data.length() - 1);
		data += "]";

		// De hoogte moet iets kleiner zijn, anders is er een scrollbar
		return start + data + mid1 + "title: '" + title + "'" + mid2
				+ type + mid3 + (width - 10) + mid4 + (height - 20)
				+ end;
	}
}
