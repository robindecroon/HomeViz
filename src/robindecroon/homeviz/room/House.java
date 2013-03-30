package robindecroon.homeviz.room;

import java.util.List;

import robindecroon.homeviz.util.Amount;

public class House extends Room {

	private List<Room> rooms;

	public House(List<Room> rooms, String name) {
		this.rooms = rooms;
		setName(name);
	}

	@Override
	public Amount getLightPrice() {
		Amount total = new Amount(0);
		for (Room room : rooms) {
			total = total.add(room.getLightPrice());
		}
		return total;
	}

	@Override
	public Amount getWaterPrice() {
		Amount total = new Amount(0);
		for (Room room : rooms) {
			total = total.add(room.getWaterPrice());
		}
		return total;
	}

	@Override
	public Amount getAppliancesPrice() {
		Amount total = new Amount(0);
		for (Room room : rooms) {
			total = total.add(room.getAppliancesPrice());
		}
		return total;
	}

	@Override
	public Amount getHomeCinemaPrice() {
		Amount total = new Amount(0);
		for (Room room : rooms) {
			total = total.add(room.getHomeCinemaPrice());
		}
		return total;
	}

}
