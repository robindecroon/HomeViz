package robindecroon.homeviz.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import robindecroon.homeviz.room.Room;

/**
 * Handler om HomeViz XML bestanden te parsen.
 * @author Robin
 *
 */
public class XMLHandler extends DefaultHandler{

	/**
	 * Variabele om de huidige waarde bij te houden die nu wordt uitgelezen.
	 */
	private String tempVal;

	/**
	 * Variabele om de kamer bij te houden die nu wordt uitgelezen.
	 */
	private Room tempRoom;

	List<Room> rooms = new ArrayList<Room>();

	/**
	 * Wordt opgeroepen bij een nieuw element.
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		tempVal = "";
		if (qName.equalsIgnoreCase("Room")) {
			tempRoom = new Room();
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
		} else if (qName.equalsIgnoreCase("Name")) {
			tempRoom.setName(tempVal);
		}
	}

	/**
	 * Geef een lijst met de ingelezen kamers terug.
	 * @return
	 */
	public List<Room> getRooms() {
		return this.rooms;
	}
}
