/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.xml;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.house.device.Appliance;
import robindecroon.homeviz.house.device.HomeCinema;
import robindecroon.homeviz.house.device.Light;
import robindecroon.homeviz.house.device.Water;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Xml;

/**
 * The Class HomeVizXMLParser.
 */
public class HomeVizXMLParser extends XMLParser {

	/** The Constant ns. */
	private static final String ns = null;

	/** The Constant TAG. */
	private static final String TAG = "HomeViz";
	
	/** The Constant ROOM_TAG. */
	private static final String ROOM_TAG = "Room";
	
	/** The Constant LIGHT_TAG. */
	private static final String LIGHT_TAG = "Light";
	
	/** The Constant HOMECINEMA_TAG. */
	private static final String HOMECINEMA_TAG = "HomeCinema";
	
	/** The Constant APPLIANCE_TAG. */
	private static final String APPLIANCE_TAG = "Appliance";
	
	/** The Constant WATER_TAG. */
	private static final String WATER_TAG = "Water";
	
	/** The Constant NAME. */
	private static final String NAME = "name";
	
	/** The Constant WATT. */
	private static final String WATT = "watt";

	/** The app. */
	private HomeVizApplication app;
	
	/** The demo multiplier. */
	private int demoMultiplier;

	/**
	 * Instantiates a new home viz xml parser.
	 *
	 * @param app the app
	 */
	public HomeVizXMLParser(HomeVizApplication app) {
		this.app = app;
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(app);
		demoMultiplier = Integer.valueOf(sp.getString(Constants.DEMO_MULTIPLIER, "1"));
	}

	/**
	 * Parses the.
	 *
	 * @param in the in
	 * @return true, if successful
	 * @throws XmlPullParserException the xml pull parser exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean parse(InputStream in) throws XmlPullParserException, IOException {
		Log.i(getClass().getSimpleName(), "Parsing HomeViz XML");
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readHomeViz(parser);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			in.close();
		}
	}

	/**
	 * Read home viz.
	 *
	 * @param parser the parser
	 * @return true, if successful
	 * @throws XmlPullParserException the xml pull parser exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private boolean readHomeViz(XmlPullParser parser)
			throws XmlPullParserException, IOException {

		parser.require(XmlPullParser.START_TAG, ns, TAG);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals(ROOM_TAG)) {
				app.addRoom(readRoom(parser));
			} else {
				skip(parser);
			}
		}
		parser.require(XmlPullParser.END_TAG, ns, TAG);
		return true;
	}

	/**
	 * Read room.
	 *
	 * @param parser the parser
	 * @return the room
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XmlPullParserException the xml pull parser exception
	 */
	private Room readRoom(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, ROOM_TAG);

		String roomName = parser.getAttributeValue(null, NAME);
		Room room = new Room(roomName);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals(LIGHT_TAG)) {
				room.addLight(readLight(parser));
			} else if (name.equals(HOMECINEMA_TAG)) {
				room.addHomeCinema(readHomeCinema(parser));
			} else if (name.equals(WATER_TAG)) {
				room.addWater(readWater(parser));
			} else if (name.equals(APPLIANCE_TAG)) {
				room.addAppliance(readAppliance(parser));
			} else {
				skip(parser);
			}
		}
		parser.require(XmlPullParser.END_TAG, ns, ROOM_TAG);

		return room;
	}

	/**
	 * Read light.
	 *
	 * @param parser the parser
	 * @return the light
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XmlPullParserException the xml pull parser exception
	 */
	private Light readLight(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, LIGHT_TAG);

		String lightName = parser.getAttributeValue(null, NAME);
		int watt = Integer.parseInt(parser.getAttributeValue(null, WATT));
		Light light = new Light(lightName, watt, demoMultiplier);

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, LIGHT_TAG);
		return light;
	}

	/**
	 * Read home cinema.
	 *
	 * @param parser the parser
	 * @return the home cinema
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XmlPullParserException the xml pull parser exception
	 */
	private HomeCinema readHomeCinema(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, HOMECINEMA_TAG);

		String name = parser.getAttributeValue(null, NAME);
		int watt = Integer.parseInt(parser.getAttributeValue(null, WATT));
		HomeCinema homeCinema = new HomeCinema(name, watt, demoMultiplier);

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, HOMECINEMA_TAG);
		return homeCinema;
	}

	/**
	 * Read appliance.
	 *
	 * @param parser the parser
	 * @return the appliance
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XmlPullParserException the xml pull parser exception
	 */
	private Appliance readAppliance(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, APPLIANCE_TAG);

		String name = parser.getAttributeValue(null, NAME);
		int watt = Integer.parseInt(parser.getAttributeValue(null, WATT));
		Appliance appliance = new Appliance(name, watt, demoMultiplier);

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, APPLIANCE_TAG);
		return appliance;
	}

	/**
	 * Read water.
	 *
	 * @param parser the parser
	 * @return the water
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XmlPullParserException the xml pull parser exception
	 */
	private Water readWater(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, WATER_TAG);

		String name = parser.getAttributeValue(null, NAME);
		Water water = new Water(name, demoMultiplier);
		water.setLiter(Math.random() * 5);

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, WATER_TAG);
		return water;
	}
}