/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.exceptions;

import robindecroon.homeviz.house.Room;

/**
 * The Class NoSuchDevicesInRoom.
 *
 * @author Robin
 */
public class NoSuchDevicesInRoom extends Exception {

	/** The room. */
	private Room room;

	/**
	 * Instantiates a new no such devices in room.
	 *
	 * @param room the room
	 */
	public NoSuchDevicesInRoom(Room room) {
		super("There are no lights in room: " + room.getName());
		this.setRoom(room);
	}

	/**
	 * Gets the room.
	 *
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Sets the room.
	 *
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
