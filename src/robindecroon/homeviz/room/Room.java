package robindecroon.homeviz.room;


public class Room {
	
	private String name;

	public Room(String name) {
		this.name = name;
	}
	
	// Constructor nodig voor XML parser
	public Room(){};

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
