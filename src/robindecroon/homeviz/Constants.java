package robindecroon.homeviz;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.graphics.Color;

@SuppressLint("SimpleDateFormat")
public class Constants {

	/*
	 * HomeViz
	 */
	public static final long CURRENT_TIME = getStartTime("2013-01-01 00:00:00");
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String PREF_NAME = "HomeVizSettings";
	public static final String USER = "CURRENT_USER";
	public static final String COUNTRY = "country";

	/*
	 * Configuration
	 */
	public static final String XML_FILE = "xml file name";
	public static final String XML_FILE_NAME = "DemoHouse.xml";
	public static final String CO2_DATA_FILE_NAME = "co2.csv";

	/*
	 * SharedPreferences
	 */
	public static final String CURRENT_USER = "Current User";
	public static final String VIZ_TYPE = "Visualization Type";

	/*
	 * Loxone
	 */
	// public final static String LOXONE_DEFAULT_IP = "192.168.1.102";
	// public final static String LOXONE_DEFAULT_USER = "anonymous";
	// public final static String LOXONE_DEFAULT_PASSWORD = "";
	// public final static String WORKING_DIRECTORY = "pools/A/A0/HomeViz/temp";
	public static final String LOXONE_DEFAULT_USER = "admin";
	public static final String LOXONE_DEFAULT_PASSWORD = "admin";
	public static final String LOXONE_DEFAULT_IP = "192.168.1.200";
	public static final String WORKING_DIRECTORY = "stats/";

	/*
	 * Action Bar
	 */
	public static final int SPINNER_TEXT_COLOR = Color.WHITE;
	public static final String CATEGORY = "category";
	public static final String SELECTION = "selection";
	public static final int USAGE = 1;
	public static final int TOTAL = 2;
	public static final int METAPHOR = 3;
	public static final int YIELD = 4;

	/*
	 * UsageChartFragment
	 */
	public static final String USAGE_BUNDLE_TYPE = "charttype";
	public static final String USAGE_TYPE = "Usage type";
	public static final String USAGE_ROOM = "room";
	public static final String USAGE_CHART_TITLE = "Usage details";

	/*
	 * Consumers
	 */
	public static final int LIGHT = 11;
	public static final int APPLIANCE = 12;
	public static final int HOMECINEMA = 13;
	public static final int WATER = 14;
	public static final int HEATING = 15;
	public static final int IMAGE_SCALE = 350;

	/*
	 * Metaphors
	 */
	public static final String METAPHOR_VALUE = "metavalue";
	public static final String METAPHOR_TYPE = "metatype";
	public static final int METAPHOR_TYPE_CO2 = 0;
	public static final int METAPHOR_TYPE_FUEL = 1;
	public static final int METAPHOR_TYPE_WATER = 2;
	public static final String METAPHOR_CONSUMER = "metaphorTotal";
	public static final double BOTTLE_CONTENT = 1.5;
	public static final String METAPHOR_CONSUMER_NAME = "consumerName";

	public static final double BELGIAN_CO2 = 253.6057761;

	/*
	 * Total
	 */
	public static final String TOTAL_HOME = "Home";
	public static final int TREEMAP_LIGHT_WATT = 0;
	public static final int TREEMAP_LIGHT_KWH = 10;
	public static final int TREEMAP_APPLIANCE_WATT = 1;
	public static final int TREEMAP_APPLIANCE_KWH = 11;
	public static final int TREEMAP_HOMECINEMA_WATT = 2;
	public static final int TREEMAP_HOMECINEMA_KWH = 12;
	public static final String TREEMAP_OPTION = "treemap option";
	public static final String WEBVIEW_TREEMAP = "treemap";

	/*
	 * Yield
	 */
	public static final String YIELD_GROUND_WATER = "ground water";
	public static final String YIELD_TYPE = "yield type";
	public static final String METER_SOLAR = "solar";
	public static final String METER_GROUND_WATER = "groundwater";
	public static final String METER_RAIN_WATER = "rainwater";

	/*
	 * DatePicker
	 */
	public static final String DATEPICKER_TITLE = "title";

	/*
	 * Home Creator
	 */
	public static final int MAX_NB_CONSUMERS = 5;
	public static final String[] MULTIMEDIAS = { "laptop", "printer",
			"desktop1", "radio3", "tv2", "hifi1", "macbook", "radio1",
			"router", "hifi2", "tv1", "radio2" };
	public static final String[] APPLIANCES = { "dishwasher", "dryer", "fone",
			"senseo", "shaver", "vacuum", "vacuummini", "washer" };

	// /////////////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////

	private static long getStartTime(String date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
			return df.parse(date).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

}
