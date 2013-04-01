package robindecroon.homeviz.you;

import java.util.List;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.room.Light;
import robindecroon.homeviz.room.Room;
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

	public static JsonObject getTestJson(List<Room> rooms) {
		JsonObject root = new JsonObject(Constants.TOTAL_HOME, null);
		JsonObject[] roomsJson = new JsonObject[rooms.size()];
		int j = 0;
		double total = 0;
		for (Room room : rooms) {
			try {
				total += room.getTotalLightWatt();
			} catch (NoSuchDevicesInRoom e) {

			}
		}
		for (Room room : rooms) {
			List<Light> elecs = null;
			try {
				elecs = room.getLights();
				JsonObject json = new JsonObject(room.getName(), null);
				JsonObject[] children = new JsonObject[elecs.size()];
				int i = 0;
				for (Light elec : elecs) {
					children[i] = new JsonObject(elec.getName(), ""
							+ (int) ((100 * elec.getWatt() / (total))));
					i++;
				}
				json.setChildren(children);
				roomsJson[j] = json;
			} catch (NoSuchDevicesInRoom e) {
				Log.i("JSONObject", "No lights in " + room.getName());
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
