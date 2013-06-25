/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.house.device.Consumer;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Country;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.xml.HomeVizXMLParser;
import robindecroon.homeviz.yield.AYield;
import robindecroon.homeviz.yield.GroundWater;
import robindecroon.homeviz.yield.RainWater;
import robindecroon.homeviz.yield.SolarPanel;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * The Class HomeVizApplication.
 */
public class HomeVizApplication extends Application {

	/** The rooms. */
	private List<Room> rooms = new ArrayList<Room>();

	/** The solar panel. */
	private AYield solarPanel;
	
	/** The rain water. */
	private AYield rainWater;
	
	/** The ground water. */
	private AYield groundWater;
	
	/**
	 * Instantiates a new home viz application.
	 */
	public HomeVizApplication() {
		ToastMessages.setContext(this);
	}
	
	/**
	 * Adds a room.
	 *
	 * @param room the room
	 */
	public void addRoom(Room room) {
		this.rooms.add(room);
	}

	/**
	 * Gets the rooms.
	 *
	 * @return the rooms
	 */
	public List<Room> getRooms() {
		if (this.rooms == null) {
			generateRooms();
		}
		return this.rooms;
	}

	/**
	 * Sets the current country.
	 *
	 * @param currentCountry the new current country
	 */
	public void setCurrentCountry(String currentCountry) {
		try {
			if (currentCountry != null) {
				try {
					Map<String, Country> countries = readCountryCSVFile();
					
					// Initialize the country specific values
					Country country = countries.get(currentCountry);
					if (country != null) {
						Consumer.setCO2Value(country.getCo2Value());
						Consumer.setKwhPrice(country.getKwh());
						Consumer.setWaterPrice(country.getLiterPrice());
						// Save the last known location
						SharedPreferences settings = PreferenceManager
								.getDefaultSharedPreferences(this);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString(Constants.COUNTRY, currentCountry);
						editor.commit();
					} else {
						Log.e(getClass().getSimpleName(), "No local data for " + currentCountry);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Log.e(getClass().getSimpleName(), "Country is null");
			}
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), "This should not happen! Exception in setCountry");
		}
	}

	/**
	 * Reset.
	 */
	public void reset() {
		try {
			rooms.clear();
		} catch (Exception e) {
			// nothing to clear
		}
	}
	
	/**
	 * Gets the solar panel.
	 *
	 * @return the solar panel
	 */
	public AYield getSolarPanel() {
		if (solarPanel == null) {
			return SolarPanel.getDummy(getResources().getString(R.string.kwh));
		}
		return solarPanel;
	}

	/**
	 * Sets the solar panel.
	 *
	 * @param solarPanel the new solar panel
	 */
	public void setSolarPanel(AYield solarPanel) {
		this.solarPanel = solarPanel;
	}

	/**
	 * Gets the rain water.
	 *
	 * @return the rain water
	 */
	public AYield getRainWater() {
		if (rainWater == null) {
			return RainWater.getDummy(getResources().getString(R.string.liter));
		}
		return rainWater;
	}

	/**
	 * Sets the rain water.
	 *
	 * @param rainWater the new rain water
	 */
	public void setRainWater(AYield rainWater) {
		this.rainWater = rainWater;
	}

	/**
	 * Gets the ground water.
	 *
	 * @return the ground water
	 */
	public AYield getGroundWater() {
		if (groundWater == null) {
			return GroundWater.getDummy(getResources()
					.getString(R.string.liter));
		}
		return groundWater;
	}

	/**
	 * Sets the ground water.
	 *
	 * @param groundWater the new ground water
	 */
	public void setGroundWater(AYield groundWater) {
		this.groundWater = groundWater;
	}
	
	/**
	 * Read country csv file.
	 *
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Map<String, Country> readCountryCSVFile() throws IOException {
		InputStream input = getAssets().open(Constants.CO2_DATA_FILE_NAME);
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		String line = null;
		Map<String, Country> countries = new HashMap<String, Country>();

		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
		NumberFormat dotFormat = NumberFormat.getInstance(Locale.US);
		try {
			in.readLine();
			while ((line = in.readLine()) != null) {
				String[] values = line.split(";");
				Number co2number = format.parse(values[1]);
				Number kwhnumber = dotFormat.parse(values[2]);
				Number liternumber = dotFormat.parse(values[3]);

				double co2 = co2number.doubleValue();
				double kwh = kwhnumber.doubleValue();
				double liter = liternumber.doubleValue();
				countries.put(values[0], new Country(values[0], co2,
						new Amount(kwh), new Amount(liter)));
			}
		} catch (ParseException e) {
			Log.e(getClass().getSimpleName(), "Error in country CSV file!");
		}
		return countries;
	}
	
	/**
	 * Rarely android clears the rooms to release memory.
	 */
	private void generateRooms() {
		try {
			Log.e("Application", "Regenerated the rooms");
			
			// Find location of last CSV file
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			String xmlFileName = prefs.getString(Constants.XML_FILE, Constants.XML_FILE_NAME);
			
			// Read the CSV file
			InputStream in = null;
			if (xmlFileName.equals(Constants.XML_FILE_NAME)) {
				in = getAssets().open(Constants.XML_FILE_NAME);
			} else {
				in = openFileInput(xmlFileName);
			}
			
			// Reparse the configuration file in order to link the devices with their rooms.
			HomeVizXMLParser parser = new HomeVizXMLParser(this);
			parser.parse(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
}
