/**
 * 
 */
package robindecroon.homeviz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import robindecroon.homeviz.exceptions.LocationUnknownException;
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.House;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.users.Person;
import robindecroon.homeviz.util.Country;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.stackoverflow.RandomNumberGenerator;
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

	/**
	 * De huidige periode.
	 */
	private Period currentPeriod = Period.WEEK;

	private int currentRoomIndex;

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
		for (Person person: getPersons()) {
			if(person.getFirstName().equalsIgnoreCase(name))
				return person;
		}
		throw new IllegalStateException("No current user found!");
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public int getTabPosition() {
		return this.currentRoomIndex;
	}

	/**
	 * Constructor om een applicatie aan te maken. Laad automatisch de XML in.
	 */
	public HomeVizApplication() {
		ToastMessages.setContext(this);
	}

	/**
	 * Geef de huidige periode in welke staat de app zich bevindt.
	 * 
	 * @return the currentPeriod
	 */
	public Period getCurrentPeriod() {
		return currentPeriod;
	}

	/**
	 * Stel een nieuwe periode in.
	 * 
	 * @param currentPeriod
	 *            the currentPeriod to set
	 */
	public void setCurrentPeriod(Period currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	
	public void addPerson(Person person) {
		this.persons.add(person);
	}

	/**
	 * Stel nieuwe kamers in.
	 * 
	 * @param rooms
	 *            the rooms to set
	 */
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	public void addRoom(Room room) {
		this.rooms.add(room);
	}

	public Room nextRoom() {
		currentRoomIndex++;
		if (currentRoomIndex >= rooms.size()) {
			currentRoomIndex = 0;
		}
		return rooms.get(currentRoomIndex);
	}

	public Room previousRoom() {
		currentRoomIndex--;
		if (currentRoomIndex < 0) {
			currentRoomIndex = rooms.size() - 1;
		}
		return rooms.get(currentRoomIndex);
	}

	public Room getCurrentRoom() {
		return rooms.get(currentRoomIndex);
	}

	public void setCurrentRoom(int id) {
		currentRoomIndex = id;
	}
	
	public void parseXML() {
		parseXML(null);
	}

	/**
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parseXML(String path) {
//		XMLHandler handler = null; 
//		try {
//			SAXParserFactory factory = SAXParserFactory.newInstance();
//			SAXParser saxParser = factory.newSAXParser();
//
//			handler = new XMLHandler();
//			InputStream file = null;
//			try {
//				file = getAssets().open("prototype1.xml");
//			} catch (Exception e) {
//				Log.e("XML", e.getMessage());
//			}
//			if (file == null) {
//				Log.e("XML", "XML file not found!");
//			}
//			 saxParser.parse(file, handler);
////			 type = handler.getVizType();
//		} catch (Exception e) {
//			System.out.println("FOOOOOUUUUTJEEEE");
//			e.printStackTrace();
//			// Log.e("ParseXML", e.getMessage());
//			// Toast gaat nog niet, want er is nog geen context
//		}
//		setRooms(handler.getRooms());
//		setPersons(handler.getPersons());
//		currentUser = handler.getCurrentUser();
//		countryMap = handler.getCountryMap();
//		Log.i("HomeVizApplication", "The currentUser is " + currentUser);

		// TODO
//		randomizeLocationsOfPersons();
	}

	public void setCountries(Map<String, Country> map) {
		this.countryMap = map;
	}
	
	public void randomizeLocationsOfPersons() {
		for (Person person : persons) {
			if (!person.equals(getCurrentUser())) {
				int[] percantages = RandomNumberGenerator.genNumbers(
						rooms.size(), 100);
				for (int i = 0; i < percantages.length; i++) {
					rooms.get(i).setPercentageForPerson(person, percantages[i]);
				}
			}
		}
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

	private static boolean housePresent = false;

	public void addHouse() {
		if (!housePresent) {
			List<Room> old = getRooms();
			List<Room> hack = new ArrayList<Room>();
			for (Room room : old) {
				hack.add(room);
			}
			rooms.add(0,
					new House(hack, getResources().getString(R.string.house)));
			housePresent = true;
		}
	}

	public void removeHouse() {
		if (housePresent) {
			rooms.remove(0);
			housePresent = false;
		}
	}

	public Room getHouse() {
		return new House(getRooms(), getResources().getString(R.string.house));
	}

	/**
	 * @return the facebookUser
	 */
	public GraphUser getFacebookUser() {
		return facebookUser;
	}

	/**
	 * @param facebookUser the facebookUser to set
	 */
	public void setFacebookUser(GraphUser facebookUser) {
		this.facebookUser = facebookUser;
	}

}
