package robindecroon.homeviz.xml;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.room.Appliance;
import robindecroon.homeviz.room.HomeCinema;
import robindecroon.homeviz.room.Light;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.room.Water;
import robindecroon.homeviz.util.Person;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Xml;

public class HomeVizXMLParser extends XMLParser {

	// We don't use namespaces
	private static final String ns = null;

	private static final String TAG = "HomeViz";
	private static final String SETTINGS_TAG = "Settings";
	private static final String ROOM_TAG = "Room";
	private static final String LIGHT_TAG = "Light";
	private static final String HOMECINEMA_TAG = "HomeCinema";
	private static final String APPLIANCE_TAG = "Appliance";
	private static final String WATER_TAG = "Water";
	private static final String PERSON_TAG = "Person";

	private HomeVizApplication app;

	public HomeVizXMLParser(HomeVizApplication context) {
		this.app = context;
	}

	public boolean parse(InputStream in) throws XmlPullParserException,
			IOException {
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

	private boolean readHomeViz(XmlPullParser parser)
			throws XmlPullParserException, IOException {

		parser.require(XmlPullParser.START_TAG, ns, TAG);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the right tag
			if (name.equals(SETTINGS_TAG)) {
				readSettings(parser);
			} else if (name.equals(ROOM_TAG)) {
				app.addRoom(readRoom(parser));
			} else if (name.equals(PERSON_TAG)) {
				app.addPerson(readPerson(parser));
			} else {
				skip(parser);
			}
		}
		parser.require(XmlPullParser.END_TAG, ns, TAG);
		return true;
	}

	private Person readPerson(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, PERSON_TAG);

		String firstname = parser.getAttributeValue(null, "firstname");
		String lastname = parser.getAttributeValue(null, "lastname");

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, PERSON_TAG);

		return new Person(firstname, lastname);
	}

	private void readSettings(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, SETTINGS_TAG);

		String vizType = parser.getAttributeValue(null, "viztype");
		String user = parser.getAttributeValue(null, "user");

		SharedPreferences settings = app.getSharedPreferences(
				Constants.PREF_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Constants.CURRENT_USER, user);
		editor.putString(Constants.VIZ_TYPE, vizType);
		// Commit the edits!
		editor.commit();

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, SETTINGS_TAG);
	}

	private Room readRoom(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, ROOM_TAG);

		String roomName = parser.getAttributeValue(null, "name");
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

	private Light readLight(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, LIGHT_TAG);

		String lightName = parser.getAttributeValue(null, "name");
		int watt = Integer.parseInt(parser.getAttributeValue(null, "watt"));

		Light light = new Light(lightName, watt);

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, LIGHT_TAG);
		return light;
	}

	private HomeCinema readHomeCinema(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, HOMECINEMA_TAG);

		String name = parser.getAttributeValue(null, "name");
		int watt = Integer.parseInt(parser.getAttributeValue(null, "watt"));

		HomeCinema homeCinema = new HomeCinema(name, watt);

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, HOMECINEMA_TAG);
		return homeCinema;
	}

	private Appliance readAppliance(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, APPLIANCE_TAG);

		String name = parser.getAttributeValue(null, "name");
		int watt = Integer.parseInt(parser.getAttributeValue(null, "watt"));

		Appliance appliance = new Appliance(name, watt);

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, APPLIANCE_TAG);
		return appliance;
	}

	private Water readWater(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, WATER_TAG);

		String name = parser.getAttributeValue(null, "name");
		Water water = new Water(name);

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, WATER_TAG);
		return water;
	}
}