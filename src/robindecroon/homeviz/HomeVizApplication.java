/**
 * 
 */
package robindecroon.homeviz;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.users.Person;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.visualization.GoogleChartType;
import robindecroon.homeviz.xml.XMLHandler;
import robindecroon.stackoverflow.RandomNumberGenerator;
import android.app.Application;
import android.util.Log;

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

	private List<Room> rooms;
	
	private List<Person> persons;
	
	private GoogleChartType type;

	private Person currentUser;
	
	/**
	 * @return the currentUser
	 */
	public Person getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(Person currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Constructor om een applicatie aan te maken.
	 * Laad automatisch de XML in.
	 */
	public HomeVizApplication() {
		ToastMessages.setContext(this);
		parseXML("/storage/extSdCard/rooms.xml");
//		Gson gson = new Gson();
//		String json = gson.toJson(JsonObject.getTestJson());
//		System.out.println(json);
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

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	/**
	 * Stel nieuwe kamers in.
	 * 
	 * @param rooms the rooms to set
	 */
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	public Room nextRoom() {
		currentRoomIndex++;
		if(currentRoomIndex >= rooms.size()) {
			currentRoomIndex = 0;
		}
		return rooms.get(currentRoomIndex);
	}

	public Room previousRoom() {
		currentRoomIndex--;
		if(currentRoomIndex < 0) {
			currentRoomIndex = rooms.size()-1;
		}
		return rooms.get(currentRoomIndex);
	}
	
	public Room getCurrentRoom() {
		return rooms.get(currentRoomIndex);
	}
	
	/**
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parseXML(String path) {
		XMLHandler handler = null;
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			handler = new XMLHandler();
			File file = new File(path);

			saxParser.parse(file, handler);
			type = handler.getVizType();
		} catch (Exception e) {
			Log.e("ParseXML", e.getMessage());
			// TODO toast gaat nog niet, want er is nog geen context
		}
		setRooms(handler.getRooms());
		setPersons(handler.getPersons());
		currentUser = handler.getCurrentUser();
		Log.i("HomeVizApplication" , "The currentUser is " + currentUser);
		
		randomizeLocationsOfPersons();
	}
	
	public void randomizeLocationsOfPersons() {
		for(Person person: persons) {
			if(!person.equals(currentUser)) {
				int[] percantages = RandomNumberGenerator.genNumbers(rooms.size(), 100);
				for(int i = 0; i < percantages.length; i++) {
					rooms.get(i).setPercentageForPerson(person, percantages[i]);
				}
			}
		}
	}

	/**
	 * @return the type
	 */
	public GoogleChartType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(GoogleChartType type) {
		this.type = type;
	}

	public List<Room> getRooms() {
		return this.rooms;
	}
}
