package robindecroon.homeviz.xml;

import org.xml.sax.helpers.DefaultHandler;

/**
 * Handler om HomeViz XML bestanden te parsen.
 * 
 * @author Robin
 * 
 */
public class XMLHandler extends DefaultHandler {

	// /**
	// * Variabele om de huidige waarde bij te houden die nu wordt uitgelezen.
	// */
	// private String tempVal;
	//
	// /**
	// * Variabele om de kamer bij te houden die nu wordt uitgelezen.
	// */
	// private Room tempRoom;
	//
	// /**
	// * Lijst met alle aangemaakte kamers.
	// */
	// private List<Room> rooms = new ArrayList<Room>();
	//
	// private List<Person> persons = new ArrayList<Person>();
	//
	// private Map<String,Country> coValues = new HashMap<String,Country>();
	//
	// /**
	// * Variabele om de lamp bij te houden die nu wordt uitgelezen.
	// */
	// private Light tempLight;
	// private Person tempPerson;
	// private Water tempWater;
	// private String currentUserName;
	// private GoogleChartType type;
	// private Country tempCountry;
	// private HomeCinema tempHomeCinema;
	// private Appliance tempAppliance;
	//
	//
	// /**
	// * Wordt opgeroepen bij een nieuw element.
	// */
	// @Override
	// public void startElement(String uri, String localName, String qName,
	// Attributes attributes) throws SAXException {
	// tempVal = "";
	// if (qName.equalsIgnoreCase("Room")) {
	// tempRoom = new Room();
	// } else if (qName.equalsIgnoreCase("Light")) {
	// tempLight = new Light();
	// } else if (qName.equalsIgnoreCase("Person")) {
	// tempPerson = new Person();
	// } else if (qName.equalsIgnoreCase("User")) {
	// // Do nothing
	// } else if (qName.equalsIgnoreCase("Country")) {
	// tempCountry = new Country();
	// } else if (qName.equalsIgnoreCase("Water")) {
	// tempWater = new Water();
	// } else if (qName.equalsIgnoreCase("HomeCinema")) {
	// tempHomeCinema = new HomeCinema();
	// } else if (qName.equalsIgnoreCase("Appliance")) {
	// tempAppliance = new Appliance();
	// }
	// }
	//
	// /**
	// * Lees de waarde tussen tags uit.
	// */
	// @Override
	// public void characters(char[] ch, int start, int length)
	// throws SAXException {
	// tempVal = new String(ch, start, length);
	// }
	//
	// /**
	// * Wordt opgeroepen bij het einde van een element.
	// */
	// @Override
	// public void endElement(String uri, String localName, String qName)
	// throws SAXException {
	// if (qName.equalsIgnoreCase("Room")) {
	// rooms.add(tempRoom);
	// tempRoom = null;
	// } else if (qName.equalsIgnoreCase("Name")) {
	// if (tempRoom != null && tempLight == null && tempWater == null &&
	// tempHomeCinema == null && tempAppliance == null) {
	// tempRoom.setName(tempVal);
	// } else if (tempPerson != null) {
	// tempPerson.setName(tempVal);
	// } else if (tempCountry != null) {
	// tempCountry.setName(tempVal);
	// } else if (tempLight != null) {
	// tempLight.setName(tempVal);
	// } else if (tempWater != null) {
	// tempWater.setName(tempVal);
	// } else if (tempHomeCinema != null) {
	// tempHomeCinema.setName(tempVal);
	// } else if (tempAppliance != null) {
	// tempAppliance.setName(tempVal);
	// }
	// } else if (qName.equalsIgnoreCase("Light")) {
	// tempRoom.addLight(tempLight);
	// tempLight = null;
	// } else if (qName.equalsIgnoreCase("Person")) {
	// persons.add(tempPerson);
	// tempPerson = null;
	// } else if (qName.equalsIgnoreCase("VizType")) {
	// for (GoogleChartType type1 : GoogleChartType.values()) {
	// if (tempVal.equalsIgnoreCase(type1.toString())) {
	// type = type1;
	// break;
	// }
	// }
	// } else if (qName.equalsIgnoreCase("User")) {
	// currentUserName = tempVal;
	// } else if (qName.equalsIgnoreCase("Country")) {
	// coValues.put(tempCountry.getName(), tempCountry.clone());
	// tempCountry = null;
	// } else if (qName.equalsIgnoreCase("CO2Value")) {
	// tempCountry.setCo2Value(Double.valueOf(tempVal));
	// } else if (qName.equalsIgnoreCase("kwh")) {
	// tempCountry.setKwh(new Amount(Double.valueOf(tempVal)));
	// } else if (qName.equalsIgnoreCase("Watt") && tempLight != null) {
	// tempLight.setWatt(Integer.valueOf(tempVal));
	// } else if (qName.equalsIgnoreCase("TimeDay") && tempLight != null) {
	// tempLight.setAverageHoursOn(Integer.valueOf(tempVal));
	// } else if (qName.equalsIgnoreCase("Water")) {
	// tempRoom.addWater(tempWater);
	// tempWater = null;
	// } else if (qName.equalsIgnoreCase("Liter") && tempWater != null) {
	// tempWater.setLiter(Double.valueOf(tempVal));
	// } else if (qName.equalsIgnoreCase("LiterPrice")) {
	// tempCountry.setLiterPrice(new Amount(Double.valueOf(tempVal)));
	// } else if (qName.equalsIgnoreCase("HomeCinema")) {
	// tempRoom.addHomeCinema(tempHomeCinema);
	// tempHomeCinema = null;
	// } else if (qName.equalsIgnoreCase("Watt") && tempHomeCinema != null) {
	// tempHomeCinema.setWatt(Integer.valueOf(tempVal));
	// } else if (qName.equalsIgnoreCase("TimeDay") && tempHomeCinema != null) {
	// tempHomeCinema.setAverageHoursOn(Integer.valueOf(tempVal));
	// } else if (qName.equalsIgnoreCase("Watt") && tempAppliance != null) {
	// tempAppliance.setWatt(Integer.valueOf(tempVal));
	// } else if (qName.equalsIgnoreCase("TimeDay") && tempAppliance != null) {
	// tempAppliance.setAverageHoursOn(Integer.valueOf(tempVal));
	// } else if (qName.equalsIgnoreCase("Liter") && tempAppliance != null) {
	// tempAppliance.setLiter(Double.valueOf(tempVal));
	// } else if (qName.equalsIgnoreCase("Appliance")) {
	// tempRoom.addAppliance(tempAppliance);
	// tempAppliance = null;
	// }
	// }
	//
	// /**
	// * Geef een lijst met de ingelezen kamers terug.
	// *
	// * @return
	// */
	// public List<Room> getRooms() {
	// return this.rooms;
	// }
	//
	// public List<Person> getPersons() {
	// return this.persons;
	// }
	//
	// public GoogleChartType getVizType() {
	// return type;
	// }
	//
	// public Person getCurrentUser() {
	// for (Person person : persons) {
	// if (person.getName().equalsIgnoreCase(currentUserName)) {
	// return person;
	// }
	// }
	// throw new ParseXMLException(currentUserName);
	// }
	//
	// /**
	// * @return the coValues
	// */
	// public Map<String, Country> getCountryMap() {
	// return coValues;
	// }

}
