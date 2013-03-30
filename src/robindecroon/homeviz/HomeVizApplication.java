/**
 * 
 */
package robindecroon.homeviz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import robindecroon.homeviz.exceptions.LocationUnknownException;
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.util.Country;
import robindecroon.homeviz.util.ImageScaler;
import robindecroon.homeviz.util.Person;
import robindecroon.homeviz.util.ToastMessages;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.model.GraphUser;

/**
 * Eigen applicatieklasse om variabelen bij te houden.
 * 
 * @author Robin
 * 
 */
public class HomeVizApplication extends Application {

	private List<Room> rooms = new ArrayList<Room>();
	private List<Person> persons = new ArrayList<Person>();

	private GraphUser facebookUser;

	private Map<String, Country> countryMap;

	/**
	 * @param currentCountry
	 *            the currentCountry to set
	 */
	public void setCurrentCountry(String currentCountry) {
		// TODO dirty hack
		Country country = countryMap.get(currentCountry);
		Consumer.setCO2Value(country.getCo2Value());
		Consumer.setKwhPrice(country.getKwh());
		Consumer.setWaterPrice(country.getLiterPrice());
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("country", currentCountry);
		editor.commit();
	}

	/**
	 * @return the currentUser
	 */
	public Person getCurrentUser() throws IllegalStateException {
		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME, 0);
		String name = pref.getString(Constants.CURRENT_USER, null);
		for (Person person : getPersons()) {
			if (person.getFirstName().equalsIgnoreCase(name))
				return person;
		}
		throw new IllegalStateException("No current user found!");
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	/**
	 * Constructor om een applicatie aan te maken. Laad automatisch de XML in.
	 */
	public HomeVizApplication() {
		ToastMessages.setContext(this);
		ImageScaler.setContext(this);
	}

	public void addPerson(Person person) {
		this.persons.add(person);
	}

	public void addRoom(Room room) {
		this.rooms.add(room);
	}

	public void setCountries(Map<String, Country> map) {
		this.countryMap = map;
	}

	public List<Room> getRooms() {
		return this.rooms;
	}

	public double getCo2Multiplier() throws LocationUnknownException {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		String currentCountry = settings.getString("country", null);
		if (currentCountry == null) {
			throw new LocationUnknownException("No location");
		} else {
			if (countryMap.containsKey(currentCountry)) {
				return countryMap.get(currentCountry).getCo2Value();
			} else {
				throw new LocationUnknownException("No CO2 data");
			}
		}
	}

	/**
	 * @return the facebookUser
	 */
	public GraphUser getFacebookUser() {
		return facebookUser;
	}

	/**
	 * @param facebookUser
	 *            the facebookUser to set
	 */
	public void setFacebookUser(GraphUser facebookUser) {
		this.facebookUser = facebookUser;
	}

	public void reset() {
		try {
			countryMap.clear();
			facebookUser = null;
			persons.clear();
			rooms.clear();
		} catch (Exception e) {
			// nothing to clear
		}
	}
}
