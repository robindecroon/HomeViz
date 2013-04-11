/**
 * 
 */
package robindecroon.homeviz;

import java.io.BufferedReader;
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

import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.util.AYield;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Country;
import robindecroon.homeviz.util.GroundWater;
import robindecroon.homeviz.util.Person;
import robindecroon.homeviz.util.RainWater;
import robindecroon.homeviz.util.SolarPanel;
import robindecroon.homeviz.util.ToastMessages;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Eigen applicatieklasse om variabelen bij te houden.
 * 
 * @author Robin
 * 
 */
public class HomeVizApplication extends Application {

	private List<Room> rooms = new ArrayList<Room>();

	private AYield solarPanel;
	private AYield rainWater;
	private AYield groundWater;
	

	/**
	 * @return the solarPanel
	 */
	public AYield getSolarPanel() {
		if (solarPanel == null) {
			return SolarPanel.getDummy(getResources().getString(R.string.kwh));
		}
		return solarPanel;
	}

	/**
	 * @param solarPanel
	 *            the solarPanel to set
	 */
	public void setSolarPanel(AYield solarPanel) {
		this.solarPanel = solarPanel;
	}
	
	/**
	 * @return the solarPanel
	 */
	public AYield getRainWater() {
		if (rainWater == null) {
			return RainWater.getDummy(getResources().getString(R.string.liter));
		}
		return rainWater;
	}

	/**
	 * @param solarPanel
	 *            the solarPanel to set
	 */
	public void setRainWater(AYield rainWater) {
		this.rainWater = rainWater;
	}
	
	/**
	 * @return the solarPanel
	 */
	public AYield getGroundWater() {
		if (groundWater == null) {
			return GroundWater.getDummy(getResources().getString(R.string.liter));
		}
		return groundWater;
	}

	/**
	 * @param solarPanel
	 *            the solarPanel to set
	 */
	public void setGroundWater(AYield groundWater) {
		this.groundWater = groundWater;
	}

	/**
	 * @param currentCountry
	 *            the currentCountry to set
	 */
	public void setCurrentCountry(String currentCountry) {
		if (currentCountry != null) {
			try {
				InputStream input = getAssets().open(Constants.CO2_DATA_FILE_NAME);
				BufferedReader in = new BufferedReader(new InputStreamReader(input));
				String line = null;
				Map<String, Country> countries = new HashMap<String, Country>();

				NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
				NumberFormat dotFormat = NumberFormat.getInstance(Locale.US);
				try {
					// The headers
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
					e.printStackTrace();
					Log.e(getClass().getSimpleName(), "Error in country CSV file!");
				}
				Country country = countries.get(currentCountry);
				Consumer.setCO2Value(country.getCo2Value());
				Consumer.setKwhPrice(country.getKwh());
				Consumer.setWaterPrice(country.getLiterPrice());
				SharedPreferences settings = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(Constants.COUNTRY, currentCountry);
				editor.commit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Log.e(getClass().getSimpleName(), "Country is null"); 
		}
	}

	/**
	 * Constructor om een applicatie aan te maken. Laad automatisch de XML in.
	 */
	public HomeVizApplication() {
		ToastMessages.setContext(this);
	}

	public void addRoom(Room room) {
		this.rooms.add(room);
	}

	public List<Room> getRooms() {
		return this.rooms;
	}

	public void reset() {
		try {
			rooms.clear();
		} catch (Exception e) {
			// nothing to clear
		}
	}
}
