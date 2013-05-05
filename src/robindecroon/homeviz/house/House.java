/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.house;

import java.util.List;

import robindecroon.homeviz.util.Amount;

/**
 * The Class House.
 */
public class House extends Room {

	/** The rooms. */
	private List<Room> rooms;

	/**
	 * Instantiates a new house.
	 *
	 * @param rooms the rooms
	 * @param name the name
	 */
	public House(List<Room> rooms, String name) {
		super(name);
		this.rooms = rooms;
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.Room#getLightPrice()
	 */
	@Override
	public Amount getLightPrice() {
		Amount total = new Amount(0);
		for (Room room : rooms) {
			total = total.add(room.getLightPrice());
		}
		return total;
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.Room#getWaterPrice()
	 */
	@Override
	public Amount getWaterPrice() {
		Amount total = new Amount(0);
		for (Room room : rooms) {
			total = total.add(room.getWaterPrice());
		}
		return total;
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.Room#getAppliancesPrice()
	 */
	@Override
	public Amount getAppliancesPrice() {
		Amount total = new Amount(0);
		for (Room room : rooms) {
			total = total.add(room.getAppliancesPrice());
		}
		return total;
	}

	/* (non-Javadoc)
	 * @see robindecroon.homeviz.house.Room#getHomeCinemaPrice()
	 */
	@Override
	public Amount getHomeCinemaPrice() {
		Amount total = new Amount(0);
		for (Room room : rooms) {
			total = total.add(room.getHomeCinemaPrice());
		}
		return total;
	}

}
