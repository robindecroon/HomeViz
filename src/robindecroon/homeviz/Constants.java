/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import robindecroon.homeviz.util.Amount;

import android.annotation.SuppressLint;
import android.graphics.Color;

/**
 * The Class Constants.
 */
@SuppressLint("SimpleDateFormat")
public class Constants {

	/*
	 * HomeViz
	 */
	/** The Constant CURRENT_TIME. */
	public static final long CURRENT_TIME = getStartTime("2013-01-01 00:00:00");
	
	/** The Constant DATE_FORMAT. */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/** The Constant PREF_NAME. */
	public static final String PREF_NAME = "HomeVizSettings";
	
	/** The Constant USER. */
	public static final String USER = "CURRENT_USER";
	
	/** The Constant COUNTRY. */
	public static final String COUNTRY = "country";

	/*
	 * Configuration
	 */
	/** The Constant XML_FILE. */
	public static final String XML_FILE = "xml file name";
	
	/** The Constant XML_FILE_NAME. */
	public static final String XML_FILE_NAME = "DemoHouse.xml";
	
	/** The Constant CO2_DATA_FILE_NAME. */
	public static final String CO2_DATA_FILE_NAME = "co2.csv";
	

	/*
	 * SharedPreferences
	 */
	/** The Constant CURRENT_USER. */
	public static final String CURRENT_USER = "Current User";
	
	/** The Constant VIZ_TYPE. */
	public static final String VIZ_TYPE = "Visualization Type";
	
	/** The Constant DEMO_MULTIPLIER. */
	public static final String DEMO_MULTIPLIER = "demo_multiplier";

	/*
	 * Loxone
	 */
	/** The Constant LOXONE_DEFAULT_USER. */
	public static final String LOXONE_DEFAULT_USER = "admin";
	
	/** The Constant LOXONE_DEFAULT_PASSWORD. */
	public static final String LOXONE_DEFAULT_PASSWORD = "admin";
	
	/** The Constant LOXONE_DEFAULT_IP. */
	public static final String LOXONE_DEFAULT_IP = "192.168.1.200";
	
	/** The Constant WORKING_DIRECTORY. */
	public static final String WORKING_DIRECTORY = "stats/";

	/*
	 * Action Bar
	 */
	/** The Constant SPINNER_TEXT_COLOR. */
	public static final int SPINNER_TEXT_COLOR = Color.WHITE;
	
	/** The Constant CATEGORY. */
	public static final String CATEGORY = "category";
	
	/** The Constant SELECTION. */
	public static final String SELECTION = "selection";
	
	/** The Constant USAGE. */
	public static final int USAGE = 1;
	
	/** The Constant TOTAL. */
	public static final int TOTAL = 2;
	
	/** The Constant METAPHOR. */
	public static final int METAPHOR = 3;
	
	/** The Constant YIELD. */
	public static final int YIELD = 4;

	/*
	 * UsageChartFragment
	 */
	/** The Constant USAGE_BUNDLE_TYPE. */
	public static final String USAGE_BUNDLE_TYPE = "charttype";
	
	/** The Constant USAGE_TYPE. */
	public static final String USAGE_TYPE = "Usage type";
	
	/** The Constant USAGE_ROOM. */
	public static final String USAGE_ROOM = "room";

	/*
	 * Consumers
	 */
	/** The Constant LIGHT. */
	public static final int LIGHT = 11;
	
	/** The Constant APPLIANCE. */
	public static final int APPLIANCE = 12;
	
	/** The Constant MULTIMEDIA. */
	public static final int MULTIMEDIA = 13;
	
	/** The Constant WATER. */
	public static final int WATER = 14;
	
	/** The Constant HEATING. */
	public static final int HEATING = 15;
	
	/** The Constant IMAGE_SCALE. */
	public static final int IMAGE_SCALE = 350;
	
	/** The Constant DEFAULT_KWH_PRICE. */
	public static final Amount DEFAULT_KWH_PRICE = new Amount(0.2289);
	
	/** The Constant DEFAULT_WATER_PRICE. */
	public static final Amount DEFAULT_WATER_PRICE = new Amount(0.0014);

	/*
	 * Metaphors
	 */
	/** The Constant METAPHOR_VALUE. */
	public static final String METAPHOR_VALUE = "metavalue";
	
	/** The Constant METAPHOR_TYPE. */
	public static final String METAPHOR_TYPE = "metatype";
	
	/** The Constant METAPHOR_TYPE_CO2. */
	public static final int METAPHOR_TYPE_CO2 = 0;
	
	/** The Constant METAPHOR_TYPE_FUEL. */
	public static final int METAPHOR_TYPE_FUEL = 1;
	
	/** The Constant METAPHOR_TYPE_WATER. */
	public static final int METAPHOR_TYPE_WATER = 2;
	
	/** The Constant METAPHOR_CONSUMER. */
	public static final String METAPHOR_CONSUMER = "metaphorTotal";
	
	/** The Constant BOTTLE_CONTENT. */
	public static final double BOTTLE_CONTENT = 1.5;
	
	/** The Constant METAPHOR_CONSUMER_NAME. */
	public static final String METAPHOR_CONSUMER_NAME = "consumerName";

	/** The Constant DEFAULT_CO2. */
	public static final double DEFAULT_CO2 = 253.6057761;

	/*
	 * Total
	 */
	/** The Constant TOTAL_HOME. */
	public static final String TOTAL_HOME = "Home";
	
	/** The Constant TREEMAP_LIGHT_WATT. */
	public static final int TREEMAP_LIGHT_WATT = 0;
	
	/** The Constant TREEMAP_LIGHT_KWH. */
	public static final int TREEMAP_LIGHT_KWH = 10;
	
	/** The Constant TREEMAP_APPLIANCE_WATT. */
	public static final int TREEMAP_APPLIANCE_WATT = 1;
	
	/** The Constant TREEMAP_APPLIANCE_KWH. */
	public static final int TREEMAP_APPLIANCE_KWH = 11;
	
	/** The Constant TREEMAP_HOMECINEMA_WATT. */
	public static final int TREEMAP_HOMECINEMA_WATT = 2;
	
	/** The Constant TREEMAP_HOMECINEMA_KWH. */
	public static final int TREEMAP_HOMECINEMA_KWH = 12;
	
	/** The Constant TREEMAP_WATER. */
	public static final int TREEMAP_WATER = 3;
	
	/** The Constant TREEMAP_HEATING. */
	public static final int TREEMAP_HEATING = 4;
	
	/** The Constant TREEMAP_OPTION. */
	public static final String TREEMAP_OPTION = "treemap option";

	/*
	 * Yield
	 */
	/** The Constant YIELD_GROUND_WATER. */
	public static final String YIELD_GROUND_WATER = "ground water";
	
	/** The Constant YIELD_TYPE. */
	public static final String YIELD_TYPE = "yield type";
	
	/** The Constant METER_SOLAR. */
	public static final String METER_SOLAR = "solar";
	
	/** The Constant METER_GROUND_WATER. */
	public static final String METER_GROUND_WATER = "groundwater";
	
	/** The Constant METER_RAIN_WATER. */
	public static final String METER_RAIN_WATER = "rainwater";

	/*
	 * DatePicker
	 */
	/** The Constant DATEPICKER_TITLE. */
	public static final String DATEPICKER_TITLE = "title";

	/*
	 * Home Creator
	 */
	/** The Constant MAX_NB_CONSUMERS. */
	public static final int MAX_NB_CONSUMERS = 5;
	
	/** The Constant MULTIMEDIAS. */
	public static final String[] MULTIMEDIAS = { "laptop", "printer",
			"desktop1", "radio3", "tv2", "hifi1", "macbook", "radio1",
			"router", "hifi2", "tv1", "radio2" };
	
	/** The Constant APPLIANCES. */
	public static final String[] APPLIANCES = { "dishwasher", "dryer", "fone",
			"senseo", "shaver", "vacuum", "vacuummini", "washer" };

	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Gets the start time.
	 *
	 * @param date the date
	 * @return the start time
	 */
	private static long getStartTime(String date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
			return df.parse(date).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

}
