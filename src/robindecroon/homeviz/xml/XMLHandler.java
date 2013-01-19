package robindecroon.homeviz.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import robindecroon.homeviz.exceptions.ParseXMLException;
import robindecroon.homeviz.room.Light;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.users.Person;
import robindecroon.homeviz.visualization.GoogleChartType;

/**
 * Handler om HomeViz XML bestanden te parsen.
 * 
 * @author Robin
 * 
 */
public class XMLHandler extends DefaultHandler {

	/**
	 * Variabele om de huidige waarde bij te houden die nu wordt uitgelezen.
	 */
	private String tempVal;

	/**
	 * Variabele om de kamer bij te houden die nu wordt uitgelezen.
	 */
	private Room tempRoom;

	/**
	 * Lijst met alle aangemaakte kamers.
	 */
	private List<Room> rooms = new ArrayList<Room>();

	/**
	 * Variabele om de lamp bij te houden die nu wordt uitgelezen.
	 */
	private Light tempLight;

	private Person tempPerson;
	private String currentUser;

	private GoogleChartType type;

	private List<Person> persons = new ArrayList<Person>();
	
	private Map<String,Double> coValues = new HashMap<String,Double>();
	
	/**
	 * @return the coValues
	 */
	public Map<String, Double> getCoValues() {
		return coValues;
	}

	private Country tempCountry;

	/**
	 * Wordt opgeroepen bij een nieuw element.
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		tempVal = "";
		if (qName.equalsIgnoreCase("Room")) {
			tempRoom = new Room();
		} else if (qName.equalsIgnoreCase("Light")) {
			tempLight = new Light();
		} else if (qName.equalsIgnoreCase("Person")) {
			tempPerson = new Person();
		} else if (qName.equalsIgnoreCase("User")) {
			// Do nothing			
		} else if (qName.equalsIgnoreCase("Country")) {
			tempCountry = new Country();
		}
	}

	/**
	 * Lees de waarde tussen tags uit.
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tempVal = new String(ch, start, length);
	}

	/**
	 * Wordt opgeroepen bij het einde van een element.
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase("Room")) {
			rooms.add(tempRoom);
			tempRoom = null;
		} else if (qName.equalsIgnoreCase("Name")) {
			if (tempRoom != null) {
				tempRoom.setName(tempVal);
			} else if (tempPerson != null) {
				tempPerson.setName(tempVal);
			} else if (tempCountry != null) {
				tempCountry.setName(tempVal);
			}
		} else if (qName.equalsIgnoreCase("Light")) {
			tempLight.setId(tempVal);
			tempRoom.addLight(tempLight);
		} else if (qName.equalsIgnoreCase("Person")) {
			persons.add(tempPerson);
			tempPerson = null;
		} else if (qName.equalsIgnoreCase("VizType")) {
			for (GoogleChartType type1 : GoogleChartType.values()) {
				if (tempVal.equalsIgnoreCase(type1.toString())) {
					type = type1;
					break;
				}
			}
		} else if (qName.equalsIgnoreCase("User")) {
			currentUser = tempVal;
		} else if (qName.equalsIgnoreCase("Country")) {
			coValues.put(tempCountry.getName(), tempCountry.getCo2Value());
			tempCountry = null;
		} else if (qName.equalsIgnoreCase("CO2Value")) {
			tempCountry.setCo2Value(Double.valueOf(tempVal));
		}
	}

	/**
	 * Geef een lijst met de ingelezen kamers terug.
	 * 
	 * @return
	 */
	public List<Room> getRooms() {
		return this.rooms;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public GoogleChartType getVizType() {
		return type;
	}

	public Person getCurrentUser() {
		for (Person person : persons) {
			if (person.getName().equalsIgnoreCase(currentUser)) {
				return person;
			}
		}
		throw new ParseXMLException(currentUser);
	}
}
