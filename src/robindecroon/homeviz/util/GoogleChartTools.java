package robindecroon.homeviz.util;

import java.util.Map;

import android.content.Context;

public class GoogleChartTools {
	
	private final static String start = "<html><head><script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script><script type=\"text/javascript\">"
			+ "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});google.setOnLoadCallback(drawChart);function drawChart() {"
			+ "var data = google.visualization.arrayToDataTable([";
	
	private final static String mid1 =  "		        ]);"
			+ "		        var options = {";
	
	private final static String mid2 = "		        };"
			+ "		        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));"
			+ "		        chart.draw(data, options);"
			+ "		      }</script> </head> <body> <div id=\"chart_div\" style=\"width:";
			
	private final static String mid3 = "px; height: ";
	
	private final static String end = "px;\"></div></body></html>";

	public static String getUsageViz(String title,Period currentPeriod, Context context, String[] values, int width, int height ) {
		String data = "['Period', 'Light', 'Water', 'Heating', 'Appliances','Home Cinema'],['" + currentPeriod.getName(context) + "', ";
		for(String value: values) {
			data +=  value + ",";			
		} 
		data = data.substring(0, data.length()-1);
		data += "]";
		
		return start + data + mid1 +"title: '" +title +"'" + mid2 + width + mid3 + height + end;
	}

	
	
	public GoogleChartTools() {
//		String url = "<html><head><script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script><script type=\"text/javascript\">"
//				+ "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});google.setOnLoadCallback(drawChart);function drawChart() {"
//				+ "var data = google.visualization.arrayToDataTable(["
//				+ "		          ['Year', 'Light', 'Water', 'Heating', 'Appliances','Home Cinema'],"
//				+ "		          ['2004',  1000,      400, 500, 700, 300],"
//				+ "		          ['2005',  1170,      460, 288, 343, 682],"
//				+ "		          ['2006',  660,       1120,792, 1000,342],"
//				+ "		          ['2007',  1030,      540,678,567,991]"
//				+ "		        ]);"
//				+ "		        var options = {"
//				+ "		          title: 'Total Usage'"
//				+ "		        };"
//				+ "		        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));"
//				+ "		        chart.draw(data, options);"
//				+ "		      }</script> </head> <body> <div id=\"chart_div\" style=\"width: 900px; height: 500px;\"></div></body></html>";
	}
}
