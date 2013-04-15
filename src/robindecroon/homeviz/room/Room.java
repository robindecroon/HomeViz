package robindecroon.homeviz.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import robindecroon.homeviz.Main;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.util.Amount;
import android.annotation.SuppressLint;

/**
 * Klasse die een kamer voorstelt.
 * 
 * @author Robin
 * 
 */
public class Room implements RoomPrices {

	public enum ConsumerType {
		Light, Appliance, HomeCinema, Water, Heating;
	}

	public List<Consumer> getConsumersOfType(ConsumerType type)
			throws NoSuchDevicesInRoom {
		switch (type) {
		case Light:
			return getLights();
		case Appliance:
			return getAppliances();
		case HomeCinema:
			return getHomeCinemas();
		case Water:
			return getWaters();
		case Heating:
			return getHeatings();
		}
		throw new IllegalArgumentException("Type " + type + " does not exist");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@SuppressLint("DefaultLocale")
	@Override
	public String toString() {
		return getName().toUpperCase();
	}

	/**
	 * De naam van de kamer.
	 */
	private String name;
	private Amount heating;

	private List<Consumer> lights = new ArrayList<Consumer>();
	private List<Consumer> waters = new ArrayList<Consumer>();;
	private List<Consumer> homeCinemas = new ArrayList<Consumer>();
	private List<Consumer> appliances = new ArrayList<Consumer>();
	private List<Consumer> heatings = new ArrayList<Consumer>();

	public List<Consumer> getHeatings() {
		return this.heatings;
	}

	public Room(String name) {
		this();
		this.name = name;
	}

	/**
	 * Constructor nodig voor de XML parser
	 */
	public Room() {
		this.heating = new Amount(Math.random());
	};

	public Map<String, Amount> getPricesMap() {
		Map<String, Amount> map = new HashMap<String, Amount>();
		map.put("Light", getLightPrice());
		map.put("Water", getWaterPrice());
		map.put("Heating", getHeating());
		map.put("Appliances", getAppliancesPrice());
		map.put("Home Cinema", getHomeCinemaPrice());
		return map;
	}

	public Map<String, Amount> getLightsMap() {
		Map<String, Amount> map = new HashMap<String, Amount>();
		for (Consumer light : lights) {
			map.put(light.getName(), light.getPrice());
		}
		return map;
	}

	/**
	 * Geef de kamer een naam.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Geef de naam van de kamer terug.
	 * 
	 * @return the name
	 */
	public String getName() {
		if (name == null) {
			throw new IllegalStateException("A room without name");
		}
		return name;
	}

	public CharSequence getTotalPrice() {
		Amount total = getLightPrice().add(getWaterPrice())
				.add(getAppliancesPrice()).add(getHomeCinemaPrice())
				.add(getHeating());
		return total.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * robindecroon.homeviz.room.RoomPrices#getLightPrice(robindecroon.homeviz
	 * .util.Period)
	 */
	@Override
	public Amount getLightPrice() {
		Amount total = new Amount(0);
		for (Consumer light : lights) {
			total = total.add(light.getPrice());
		}
		return total;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * robindecroon.homeviz.room.RoomPrices#getWaterPrice(robindecroon.homeviz
	 * .util.Period)
	 */
	@Override
	public Amount getWaterPrice() {
		Amount total = new Amount(0);
		for (Consumer water : waters) {
			total = total.add(water.getPrice());
		}
		return total;
	}

	/**
	 * @return the heating
	 */
	public Amount getHeating() {
		return heating.multiply(Main.currentPeriod.getMultiplier());
	}

	/**
	 * @param heating
	 *            the heating to set
	 */
	public void setHeating(Amount heating) {
		this.heating = heating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * robindecroon.homeviz.room.RoomPrices#getAppliancesPrice(robindecroon.
	 * homeviz.util.Period)
	 */
	@Override
	public Amount getAppliancesPrice() {
		Amount total = new Amount(0);
		for (Consumer appliance : appliances) {
			total = total.add(appliance.getPrice());
		}
		return total;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * robindecroon.homeviz.room.RoomPrices#getHomeCinemaPrice(robindecroon.
	 * homeviz.util.Period)
	 */
	@Override
	public Amount getHomeCinemaPrice() {
		Amount total = new Amount(0);
		for (Consumer homeCinema : homeCinemas) {
			total = total.add(homeCinema.getPrice());
		}
		return total;
	}

	public void addLight(Light tempLight) {
		this.lights.add(tempLight);
	}

	public List<Consumer> getLights() throws NoSuchDevicesInRoom {
		if (this.lights.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.lights;
	}

	public Light getLight(String name) {
		for (Consumer light : lights) {
			if (light.getName().equals(name)) {
				return (Light) light;
			}
		}
		throw new IllegalArgumentException("No light with name: " + name
				+ " in room: " + this.getName());
	}

	public List<Consumer> getWaters() throws NoSuchDevicesInRoom {
		if (this.waters.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.waters;
	}

	public void addWater(Water tempWater) {
		this.waters.add(tempWater);
	}

	public Water getWater(String name) {
		for (Consumer water : waters) {
			if (water.getName().equals(name)) {
				return (Water) water;
			}
		}
		throw new IllegalArgumentException("No water with name: " + name
				+ " in room: " + this.getName());
	}

	public void addHomeCinema(HomeCinema tempHomeCinema) {
		this.homeCinemas.add(tempHomeCinema);
	}

	public HomeCinema getHomeCinema(String name) {
		for (Consumer homeCinema : homeCinemas) {
			if (homeCinema.getName().equals(name)) {
				return (HomeCinema) homeCinema;
			}
		}
		throw new IllegalArgumentException("No HomeCinema with name: " + name
				+ " in room: " + this.getName());
	}

	public List<Consumer> getHomeCinemas() throws NoSuchDevicesInRoom {
		if (this.homeCinemas.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.homeCinemas;
	}

	public List<Consumer> getAppliances() throws NoSuchDevicesInRoom {
		if (this.appliances.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.appliances;
	}

	public void addAppliance(Appliance tempAppliance) {
		this.appliances.add(tempAppliance);
	}

	public List<Consumer> getElectrics() throws NoSuchDevicesInRoom {
		List<Consumer> list = new ArrayList<Consumer>();
		try {
			list.addAll(getLights());
		} catch (NoSuchDevicesInRoom e) {

		}
		try {
			list.addAll(getHomeCinemas());
		} catch (NoSuchDevicesInRoom e) {

		}
		try {
			list.addAll(getAppliances());
		} catch (NoSuchDevicesInRoom e) {

		}
		if (list.isEmpty()) {
			throw new NoSuchDevicesInRoom(this);
		} else {
			return list;
		}
	}

	public Consumer getConsumerWithName(String name) throws NoSuchDevicesInRoom {
		List<Consumer> consumers = new ArrayList<Consumer>();
		consumers.addAll(appliances);
		consumers.addAll(heatings);
		consumers.addAll(waters);
		consumers.addAll(homeCinemas);
		consumers.addAll(lights);
		for (Consumer cons : consumers) {
			if (cons.getName().equalsIgnoreCase(name))
				return cons;
		}
		throw new NoSuchDevicesInRoom(this);
	}

	public int getTotalLightWatt() throws NoSuchDevicesInRoom {
		int result = 0;
		for (Consumer cons : getLights()) {
			result += cons.getWatt();
		}
		return result;
	}
	
	public String toXML() {
		StringBuilder xml = new StringBuilder("<Room name=\"" + getName() + "\">");
		try {
			for(Consumer light : getElectrics()) {
				xml.append(light.toXML());
			}
		} catch (NoSuchDevicesInRoom e) {
			
		}
		try {
			for(Consumer water : getWaters()) {
				xml.append(water.toXML());
			}
		} catch (NoSuchDevicesInRoom e) {
			
		}
		xml.append("</Room>");
		return xml.toString();
	}
}
