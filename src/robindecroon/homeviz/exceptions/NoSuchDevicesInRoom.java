/**
 * 
 */
package robindecroon.homeviz.exceptions;

import robindecroon.homeviz.house.Room;

/**
 * @author Robin
 * 
 */
public class NoSuchDevicesInRoom extends Exception {

	private Room room;

	public NoSuchDevicesInRoom(Room room) {
		super("There are no lights in room: " + room.getName());
		this.setRoom(room);
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room
	 *            the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
