package robindecroon.homeviz.you;

import java.util.List;
import java.util.Map;

import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.users.Person;


public class JsonObject {
	
	private String name;
	
	private String value;
	
	private JsonObject[] children;
		
	public JsonObject(){}
	
	public void setChildren(JsonObject[] children) {
		this.children = children;
	}
	
	public JsonObject(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	
	public static JsonObject getTestJson(List<Room> rooms) {
		JsonObject root = new JsonObject("Home",null);

		JsonObject[] roomsJson = new JsonObject[rooms.size()];
		int j = 0;
		for(Room room : rooms) {
			JsonObject json = new JsonObject(room.getName(),null);
			Map<Person,Integer> map = room.getPersonPercentageMap();
			JsonObject[] children = new JsonObject[map.size()];
			int i = 0;
			for(Person pers : map.keySet()) {
				children[i] = new JsonObject(pers.getName(),map.get(pers) + "");
				i++;
			}
			json.setChildren(children);
			roomsJson[j] = json;
			j++;
		}
		root.setChildren(roomsJson);
		
//		JsonObject een = new JsonObject("Keuken", null);
//		JsonObject twee = new JsonObject("Living", null);
//		JsonObject drie = new JsonObject("Berging", null);
//		JsonObject vier = new JsonObject("Porch", null);
//		
//		JsonObject robin = new JsonObject("Robin","5");
//		JsonObject vienna = new JsonObject("Vienna","3");
//		JsonObject silke = new JsonObject("silke", "2");
//		JsonObject roosje = new JsonObject("roosje", "20");
//		
//		een.setChildren(new JsonObject[]{robin, vienna});
//		twee.setChildren(new JsonObject[]{robin,vienna,silke});
//		drie.setChildren(new JsonObject[]{vienna,silke});
//		vier.setChildren(new JsonObject[]{vienna,roosje});
//		
//		
//		root.setChildren(new JsonObject[]{een,twee,drie,vier});
		
		return root;	
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
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
	 * @param value the value to set
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
