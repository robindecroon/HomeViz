package robindecroon.homeviz.total;

import java.util.List;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.house.Room.ConsumerType;
import robindecroon.homeviz.house.device.Consumer;
import android.util.Log;

public class JsonObject {

	private String name;

	private String value;

	private JsonObject[] children;

	public JsonObject() {
	}

	public void setChildren(JsonObject[] children) {
		this.children = children;
	}

	public JsonObject(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static JsonObject getWattJSON(List<Room> rooms, ConsumerType type,
			TreemapType mapType) {
		JsonObject root = new JsonObject(Constants.TOTAL_HOME, null);
		JsonObject[] roomsJson = new JsonObject[rooms.size()];
		int j = 0;
		double total = 0;
		for (Room room : rooms) {
			try {
				for (Consumer cons : room.getConsumersOfType(type)) {
					switch (mapType) {
					case Watt:
						total += cons.getWatt();
						break;
					case Power:
						total += cons.getPower();
						break;
					}
				}
			} catch (NoSuchDevicesInRoom e) {
				// skip this room
			}
		}
		for (Room room : rooms) {
			try {
				List<Consumer> consumers = null;
				consumers = room.getConsumersOfType(type);
				JsonObject json = new JsonObject(room.getName(), null);
				JsonObject[] children = new JsonObject[consumers.size()];
				int i = 0;
				for (Consumer consumer : consumers) {
					double temp = 0;
					switch (mapType) {
					case Watt:
						temp = consumer.getWatt();
						break;
					case Power:
						temp = consumer.getPower();
						break;
					}
					children[i] = new JsonObject(consumer.getName(), ""
							+ (int) ((100 * temp / (total))));
					i++;
				}
				json.setChildren(children);
				roomsJson[j] = json;
			} catch (NoSuchDevicesInRoom e) {
				roomsJson[j] = new JsonObject();
				Log.i("JSONObject", "No consumers of type  " + type + " in "
						+ room.getName());
			} finally {
				j++;
			}
		}
		root.setChildren(roomsJson);

		return root;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the children
	 */
	public JsonObject[] getChildren() {
		return children;
	}
}
