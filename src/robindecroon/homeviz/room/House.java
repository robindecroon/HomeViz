package robindecroon.homeviz.room;

import java.util.List;

import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;

public class House extends Room {
	
	private List<Room> rooms;
	
	public House(List<Room> rooms, String name) {
		this.rooms = rooms;
		setName(name);
	}

	@Override
	public Amount getLightPrice(Period currentPeriod) {
		Amount total = new Amount(0);
		for(Room room: rooms) {
			total = total.add(room.getLightPrice(currentPeriod));
		}
		return total;
	}

	@Override
	public Amount getWaterPrice(Period currentPeriod) {
		Amount total = new Amount(0);
		for(Room room: rooms) {
			total = total.add(room.getWaterPrice(currentPeriod));
		}
		return total;
	}

	@Override
	public Amount getAppliancesPrice(Period currentPeriod) {
		Amount total = new Amount(0);
		for(Room room: rooms) {
			total = total.add(room.getAppliancesPrice(currentPeriod));
		}
		return total;
	}

	@Override
	public Amount getHomeCinemaPrice(Period currentPeriod) {
		Amount total = new Amount(0);
		for(Room room: rooms) {
			total = total.add(room.getHomeCinemaPrice(currentPeriod));
		}
		return total;
	}

}
