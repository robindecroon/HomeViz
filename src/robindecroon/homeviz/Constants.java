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
	public final static long CURRENT_TIME = getStartTime("2013-01-01 00:00:00");
	public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String PREF_NAME = "HomeVizSettings";
	public final static String USER = "CURRENT_USER";
	public static final String COUNTRY = "country";

	/*
	 * Configuration
	 */
	public final static String XML_FILE_NAME = "Delnat.xml";
	public final static String CO2_DATA_FILE_NAME = "co2.csv";

	/*
	 * SharedPreferences
	 */
	public final static String CURRENT_USER = "Current User";
	public final static String VIZ_TYPE = "Visualization Type";

	/*
	 * Loxone
	 */
	// public final static String LOXONE_DEFAULT_IP = "192.168.1.102";
	// public final static String LOXONE_DEFAULT_USER = "anonymous";
	// public final static String LOXONE_DEFAULT_PASSWORD = "";
	// public final static String WORKING_DIRECTORY = "pools/A/A0/HomeViz/temp";
	public final static String LOXONE_DEFAULT_USER = "admin";
	public final static String LOXONE_DEFAULT_PASSWORD = "admin";
	public final static String LOXONE_DEFAULT_IP = "192.168.1.200";
	public final static String WORKING_DIRECTORY = "stats/";

	/*
	 * Action Bar
	 */
	public final static int SPINNER_TEXT_COLOR = Color.WHITE;
	public final static String CATEGORY = "category";
	public final static String SELECTION = "selection";
	public final static int USAGE = 1;
	public final static int TOTAL = 2;
	public final static int METAPHOR = 3;
	public final static int YIELD = 4;

	/*
	 * UsageChartFragment
	 */
	public final static String USAGE_BUNDLE_TYPE = "charttype";
	public final static String USAGE_TYPE = "Usage type";
	public static final String USAGE_ROOM = "room";
	public static final String USAGE_CHART_TITLE = "Usage details";


	/*
	 * Consumers
	 */
	public final static int LIGHT = 11;
	public final static int APPLIANCE = 12;
	public final static int HOMECINEMA = 13;
	public final static int WATER = 14;
	public final static int HEATING = 15;
	public final static int IMAGE_SCALE = 350;

	/*
	 * Metaphors
	 */
	public final static String METAPHOR_VALUE = "metavalue";
	public final static String METAPHOR_TYPE = "metatype";
	public final static int METAPHOR_TYPE_CO2 = 0;
	public final static int METAPHOR_TYPE_FUEL = 1;
	public final static int METAPHOR_TYPE_WATER = 2;
	public final static String METAPHOR_CONSUMER = "metaphorTotal";
	public final static double BOTTLE_CONTENT = 1.5;
//	public static final String METAPHOR_WATER_TEXT = " bottles of water";
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
