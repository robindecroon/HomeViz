package robindecroon.homeviz.you;


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
	
	
	public static JsonObject getTestJson() {
		JsonObject een = new JsonObject("Keuken", null);
		JsonObject twee = new JsonObject("Living", null);
		JsonObject drie = new JsonObject("Berging", null);
		JsonObject vier = new JsonObject("Porch", null);
		
		JsonObject robin = new JsonObject("Robin","5");
		JsonObject vienna = new JsonObject("Vienna","3");
		JsonObject silke = new JsonObject("silke", "2");
		JsonObject roosje = new JsonObject("roosje", "20");
		
		een.setChildren(new JsonObject[]{robin, vienna});
		twee.setChildren(new JsonObject[]{robin,vienna,silke});
		drie.setChildren(new JsonObject[]{vienna,silke});
		vier.setChildren(new JsonObject[]{vienna,roosje});
		
		JsonObject root = new JsonObject("Home",null);
		
		root.setChildren(new JsonObject[]{een,twee,drie,vier});
		
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
