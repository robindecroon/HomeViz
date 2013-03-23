package robindecroon.homeviz;

import android.graphics.Color;

public class Constants {

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
	public final static String LOXONE_IP = "192.168.1.102";
	public final static String LOXONE_USER = "anonymous";
	public final static String LOXONE_PASSWORD = "";
	public final static String WORKING_DIRECTORY = "pools/A/A0/HomeViz/temp";

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
}
