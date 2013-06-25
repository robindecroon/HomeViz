/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.total;

import java.util.List;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.house.device.Consumer;
import robindecroon.homeviz.house.device.ConsumerType;
import android.util.Log;

/**
 * The Class JsonObject. All fields, getters and setters are needed for gson.
 */
public class JsonObject {

	/** The name. */
	private String name;

	/** The value. */
	private String value;

	/** The children. */
	private JsonObject[] children;

	// Constructor needed for gson
	/**
	 * Instantiates a new json object.
	 */
	public JsonObject() {
	}

	/**
	 * Instantiates a new json object.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public JsonObject(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the children.
	 * 
	 * @return the children
	 */
	public JsonObject[] getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 * 
	 * @param children
	 *            the new children
	 */
	public void setChildren(JsonObject[] children) {
		this.children = children;
	}

	/**
	 * Gets the watt json.
	 * 
	 * @param rooms
	 *            the rooms
	 * @param type
	 *            the type
	 * @param mapType
	 *            the map type
	 * @return the watt json
	 */
	public static JsonObject getWattJSON(List<Room> rooms, ConsumerType type,
			TreemapType mapType) {
		double total = calculateTotal(rooms, type, mapType);

		JsonObject root = new JsonObject(Constants.TOTAL_HOME, null);
		JsonObject[] roomsJson = new JsonObject[rooms.size()];
		int j = 0;
		for (Room room : rooms) {
			try {
				JsonObject json;
				if (mapType == TreemapType.Heating) {
					json = new JsonObject(room.getName(), String.valueOf(room
							.getHeating().getEuroValue()));
				} else {
					int i = 0;
					List<Consumer> consumers = null;
					json = new JsonObject(room.getName(), null);
					consumers = room.getConsumersOfType(type);
					JsonObject[] children = new JsonObject[consumers.size()];
					for (Consumer consumer : consumers) {
						double temp = 0;
						switch (mapType) {
						case Watt:
							temp = consumer.getWatt();
							break;
						case Power:
							temp = consumer.getPower();
							break;
						case Water:
							temp = consumer.getPrice().getEuroValue();
						}
						children[i] = new JsonObject(consumer.getName(), ""
								+ (int) ((100 * temp / (total))));
						i++;
					}
					json.setChildren(children);
				}
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
	 * Calculate total.
	 * 
	 * @param rooms
	 *            the rooms
	 * @param type
	 *            the type
	 * @param mapType
	 *            the map type
	 * @return the double
	 */
	private static double calculateTotal(List<Room> rooms, ConsumerType type,
			TreemapType mapType) {
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
					case Water:
						total += cons.getPrice().getEuroValue();
						break;
					default:
						return 0;
					}
				}
			} catch (NoSuchDevicesInRoom e) {
			}
		}
		return total;
	}
}
