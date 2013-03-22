package robindecroon.homeviz.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.exceptions.ParseXMLException;
import robindecroon.homeviz.users.Person;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;

/**
 * Klasse die een kamer voorstelt.
 * 
 * @author Robin
 * 
 */
public class Room implements RoomPrices {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName().toUpperCase();
	}

	/**
	 * De naam van de kamer.
	 */
	private String name;
	private Amount heating;

	private List<Light> lights = new ArrayList<Light>();
	private List<Water> waters = new ArrayList<Water>();;
	private List<HomeCinema> homeCinemas = new ArrayList<HomeCinema>();
	private List<Appliance> appliances = new ArrayList<Appliance>();
	private List<Heating> heatings = new ArrayList<Heating>();
	
	public List<Heating> getHeatings() {
		return this.heatings;
	}

	private Map<Person, Integer> personPercentageMap = new HashMap<Person, Integer>();

	public Room(String name) {
		this();
		this.name = name;
	}

	/**
	 * @return the personPercentageMap
	 */
	public Map<Person, Integer> getPersonPercentageMap() {
		return personPercentageMap;
	}

	/**
	 * Constructor nodig voor de XML parser
	 */
	public Room() {
		this.heating = new Amount(Math.random());
	};

	public Map<String, Amount> getPricesMap(Period period) {
		Map<String, Amount> map = new HashMap<String, Amount>();
		map.put("Light", getLightPrice(period));
		map.put("Water", getWaterPrice(period));
		map.put("Heating", getHeating(period));
		map.put("Appliances", getAppliancesPrice(period));
		map.put("Home Cinema", getHomeCinemaPrice(period));
		return map;
	}

	public Map<String, Amount> getLightsMap(Period period) {
		Map<String, Amount> map = new HashMap<String, Amount>();
		for (Light light : lights) {
			map.put(light.getName(), light.getPrice(period));
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
			throw new ParseXMLException(this);
		}
		return name;
	}

	public CharSequence getTotalPrice(Period p) {
		Amount total = getLightPrice(p).add(getWaterPrice(p))
				.add(getAppliancesPrice(p)).add(getHomeCinemaPrice(p))
				.add(getHeating(p));
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
	public Amount getLightPrice(Period currentPeriod) {
		Amount total = new Amount(0);
		for (Light light : lights) {
			total = total.add(light.getPrice(currentPeriod));
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
	public Amount getWaterPrice(Period currentPeriod) {
		Amount total = new Amount(0);
		for (Water water : waters) {
			total = total.add(water.getPrice(currentPeriod));
		}
		return total;
	}

	/**
	 * @return the heating
	 */
	public Amount getHeating(Period currentPeriod) {
		return heating.multiply(currentPeriod.getMultiplier());
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
	public Amount getAppliancesPrice(Period currentPeriod) {
		Amount total = new Amount(0);
		for (Appliance appliance : appliances) {
			total = total.add(appliance.getPrice(currentPeriod));
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
	public Amount getHomeCinemaPrice(Period currentPeriod) {
		Amount total = new Amount(0);
		for (HomeCinema homeCinema : homeCinemas) {
			total = total.add(homeCinema.getPrice(currentPeriod));
		}
		return total;
	}

	public void addLight(Light tempLight) {
		this.lights.add(tempLight);
	}

	public List<Light> getLights() throws NoSuchDevicesInRoom {
		if (this.lights.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.lights;
	}

	public Light getLight(String name) {
		for (Light light : lights) {
			if (light.getName().equals(name)) {
				return light;
			}
		}
		throw new IllegalArgumentException("No light with name: " + name
				+ " in room: " + this.getName());
	}

	public void setPercentageForPerson(Person person, int i) {
		personPercentageMap.put(person, i);
	}

	public void printPercentages() {
		System.out.println("Room " + this.getName());
		System.out.println("----------------------------------------");
		for (Person person : personPercentageMap.keySet()) {
			System.out.println(person + " is "
					+ personPercentageMap.get(person) + "% in "
					+ this.getName());
		}
	}

	public List<Water> getWaters() throws NoSuchDevicesInRoom {
		if (this.waters.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.waters;
	}

	public void addWater(Water tempWater) {
		this.waters.add(tempWater);
	}

	public Water getWater(String name) {
		for (Water water : waters) {
			if (water.getName().equals(name)) {
				return water;
			}
		}
		throw new IllegalArgumentException("No water with name: " + name
				+ " in room: " + this.getName());
	}

	public void addHomeCinema(HomeCinema tempHomeCinema) {
		this.homeCinemas.add(tempHomeCinema);
	}

	public HomeCinema getHomeCinema(String name) {
		for (HomeCinema homeCinema : homeCinemas) {
			if (homeCinema.getName().equals(name)) {
				return homeCinema;
			}
		}
		throw new IllegalArgumentException("No HomeCinema with name: " + name
				+ " in room: " + this.getName());
	}

	public List<HomeCinema> getHomeCinemas() throws NoSuchDevicesInRoom {
		if (this.homeCinemas.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.homeCinemas;
	}

	public List<Appliance> getAppliances() throws NoSuchDevicesInRoom {
		if (this.appliances.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.appliances;
	}

	public void addAppliance(Appliance tempAppliance) {
		this.appliances.add(tempAppliance);
	}
}
