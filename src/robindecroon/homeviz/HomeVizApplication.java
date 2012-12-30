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
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.xml.XMLHandler;
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
	
	/**
	 * Constructor om een applicatie aan te maken.
	 * Laad automatisch de XML in.
	 */
	public HomeVizApplication() {
		ToastMessages.setContext(this);
		parseXML("/storage/extSdCard/rooms.xml");
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
		} catch (Exception e) {
			ToastMessages.xmlError();
			Log.e("ParseXML", e.getMessage());
		}
		setRooms(handler.getRooms());
	}
}
