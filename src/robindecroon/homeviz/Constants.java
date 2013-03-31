package robindecroon.homeviz;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.graphics.Color;

@SuppressLint("SimpleDateFormat")
public class Constants {

	public final static long CURRENT_TIME = getStartTime("2013-01-01 00:00:00");

	/*
	 * Configuration
	 */
	public final static String XML_FILE_NAME = "HomeViz.xml";
	public final static String CO2_DATA_FILE_NAME = "co2.csv";

	/*
	 * SharedPreferences
	 */
	public final static String CURRENT_USER = "Current User";
	public final static String VIZ_TYPE = "Visualization Type";

	/*
	 * Constants
	 */
	public final static String PREF_NAME = "HomeVizSettings";
	public final static String USER = "CURRENT_USER";

	/*
	 * Loxone
	 */
	// public final static String LOXONE_IP = "192.168.1.102";
	// public final static String LOXONE_USER = "anonymous";
	// public final static String LOXONE_PASSWORD = "";
	// public final static String WORKING_DIRECTORY = "pools/A/A0/HomeViz/temp";
	public final static String LOXONE_IP = "192.168.1.200";
	// public final static String LOXONE_USER = "admin";
	// public final static String LOXONE_PASSWORD = "admin";
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
	public final static String FRAGMENT_BUNDLE_TYPE = "charttype";
	public final static String DUMMY_FRAGMENT_NAME = "DummyFragmentName";
	public final static String USAGE_TYPE = "Usage type";

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
	public static final String METAPHOR_WATER_TEXT = " bottles of water";
	public static final String METAPHOR_CONSUMER_NAME = "consumerName";

	public static final double BELGIAN_CO2 = 253.6057761;

	private static long getStartTime(String date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(date).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

}
