/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.house;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import robindecroon.homeviz.activities.MainActivity;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.house.device.Appliance;
import robindecroon.homeviz.house.device.Consumer;
import robindecroon.homeviz.house.device.ConsumerType;
import robindecroon.homeviz.house.device.HomeCinema;
import robindecroon.homeviz.house.device.Light;
import robindecroon.homeviz.house.device.Water;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.xml.XMLSerializable;
import android.annotation.SuppressLint;

/**
 * The Class Room.
 */
public class Room implements XMLSerializable{
	
	/** The room name. */
	private String roomName;
	
	/** The heating. */
	private Amount heating = new Amount(Math.random());

	/** The lights. */
	private List<Consumer> lights = new ArrayList<Consumer>();
	
	/** The waters. */
	private List<Consumer> waters = new ArrayList<Consumer>();;
	
	/** The home cinemas. */
	private List<Consumer> homeCinemas = new ArrayList<Consumer>();
	
	/** The appliances. */
	private List<Consumer> appliances = new ArrayList<Consumer>();
	
	/** The heatings. */
	private List<Consumer> heatings = new ArrayList<Consumer>();
		
	/**
	 * Instantiates a new room.
	 *
	 * @param name the name
	 */
	public Room(String name) {
		this.roomName = name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.roomName = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return roomName;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//// Lights																					////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds the light.
	 *
	 * @param tempLight the temp light
	 */
	public void addLight(Light tempLight) {
		this.lights.add(tempLight);
	}

	/**
	 * Gets the lights.
	 *
	 * @return the lights
	 * @throws NoSuchDevicesInRoom the no such devices in room
	 */
	public List<Consumer> getLights() throws NoSuchDevicesInRoom {
		if (this.lights.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.lights;
	}

	/**
	 * Gets the light.
	 *
	 * @param name the name
	 * @return the light
	 */
	public Light getLight(String name) {
		for (Consumer light : lights) {
			if (light.getName().equals(name)) {
				return (Light) light;
			}
		}
		throw new IllegalArgumentException("No light with roomName: " + name
				+ " in room: " + this.getName());
	}

	/**
	 * Gets the light price.
	 *
	 * @return the light price
	 */
	public Amount getLightPrice() {
		Amount total = new Amount(0);
		for (Consumer light : lights) {
			total = total.add(light.getPrice());
		}
		return total;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//// Water																					////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Gets the waters.
	 *
	 * @return the waters
	 * @throws NoSuchDevicesInRoom the no such devices in room
	 */
	public List<Consumer> getWaters() throws NoSuchDevicesInRoom {
		if (this.waters.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.waters;
	}

	/**
	 * Adds the water.
	 *
	 * @param tempWater the temp water
	 */
	public void addWater(Water tempWater) {
		this.waters.add(tempWater);
	}

	/**
	 * Gets the water.
	 *
	 * @param name the name
	 * @return the water
	 */
	public Water getWater(String name) {
		for (Consumer water : waters) {
			if (water.getName().equals(name)) {
				return (Water) water;
			}
		}
		throw new IllegalArgumentException("No water with roomName: " + name
				+ " in room: " + this.getName());
	}

	/**
	 * Gets the water price.
	 *
	 * @return the water price
	 */
	public Amount getWaterPrice() {
		Amount total = new Amount(0);
		for (Consumer water : waters) {
			total = total.add(water.getPrice());
		}
		return total;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//// Heating																				////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Gets the heating.
	 *
	 * @return the heating
	 */
	public Amount getHeating() {
		return heating.multiply(MainActivity.currentPeriod.getMultiplier());
	}

	/**
	 * Sets the heating.
	 *
	 * @param heating the new heating
	 */
	public void setHeating(Amount heating) {
		this.heating = heating;
	}
	
	/**
	 * Gets the heatings.
	 *
	 * @return the heatings
	 */
	public List<Consumer> getHeatings() {
		return this.heatings;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//// Appliance																				////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Gets the appliances.
	 *
	 * @return the appliances
	 * @throws NoSuchDevicesInRoom the no such devices in room
	 */
	public List<Consumer> getAppliances() throws NoSuchDevicesInRoom {
		if (this.appliances.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.appliances;
	}

	/**
	 * Adds the appliance.
	 *
	 * @param tempAppliance the temp appliance
	 */
	public void addAppliance(Appliance tempAppliance) {
		this.appliances.add(tempAppliance);
	}

	/**
	 * Gets the appliances price.
	 *
	 * @return the appliances price
	 */
	public Amount getAppliancesPrice() {
		Amount total = new Amount(0);
		for (Consumer appliance : appliances) {
			total = total.add(appliance.getPrice());
		}
		return total;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//// Multimedia																				////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Gets the home cinema price.
	 *
	 * @return the home cinema price
	 */
	public Amount getHomeCinemaPrice() {
		Amount total = new Amount(0);
		for (Consumer homeCinema : homeCinemas) {
			total = total.add(homeCinema.getPrice());
		}
		return total;
	}

	/**
	 * Adds the home cinema.
	 *
	 * @param tempHomeCinema the temp home cinema
	 */
	public void addHomeCinema(HomeCinema tempHomeCinema) {
		this.homeCinemas.add(tempHomeCinema);
	}

	/**
	 * Gets the home cinema.
	 *
	 * @param name the name
	 * @return the home cinema
	 */
	public HomeCinema getHomeCinema(String name) {
		for (Consumer homeCinema : homeCinemas) {
			if (homeCinema.getName().equals(name)) {
				return (HomeCinema) homeCinema;
			}
		}
		throw new IllegalArgumentException("No HomeCinema with roomName: " + name
				+ " in room: " + this.getName());
	}

	/**
	 * Gets the home cinemas.
	 *
	 * @return the home cinemas
	 * @throws NoSuchDevicesInRoom the no such devices in room
	 */
	public List<Consumer> getHomeCinemas() throws NoSuchDevicesInRoom {
		if (this.homeCinemas.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.homeCinemas;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//// Other																					////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public CharSequence getTotalPrice() {
		Amount total = getLightPrice().add(getWaterPrice())
				.add(getAppliancesPrice()).add(getHomeCinemaPrice())
				.add(getHeating());
		return total.toString();
	}

	/**
	 * Gets the electrics.
	 *
	 * @return the electrics
	 * @throws NoSuchDevicesInRoom the no such devices in room
	 */
	public List<Consumer> getElectrics() throws NoSuchDevicesInRoom {
		List<Consumer> list = new ArrayList<Consumer>();
		try {
			list.addAll(getLights());
		} catch (NoSuchDevicesInRoom e) {}
		try {
			list.addAll(getHomeCinemas());
		} catch (NoSuchDevicesInRoom e) {}
		try {
			list.addAll(getAppliances());
		} catch (NoSuchDevicesInRoom e) {}
		if (list.isEmpty()) {
			throw new NoSuchDevicesInRoom(this);
		} else {
			return list;
		}
	}

	/**
	 * Gets the consumer with name.
	 *
	 * @param name the name
	 * @return the consumer with name
	 * @throws NoSuchDevicesInRoom the no such devices in room
	 */
	public Consumer getConsumerWithName(String name) throws NoSuchDevicesInRoom {
		List<Consumer> consumers = new ArrayList<Consumer>();
		consumers.addAll(appliances);
		consumers.addAll(heatings);
		consumers.addAll(waters);
		consumers.addAll(homeCinemas);
		consumers.addAll(lights);
		for (Consumer cons : consumers) 
			if (cons.getName().equalsIgnoreCase(name))	
				return cons;
		throw new NoSuchDevicesInRoom(this);
	}

	/**
	 * To xml.
	 *
	 * @return the string
	 */
	 @Override
	public String toXML() {
		StringBuilder xml = new StringBuilder("<Room roomName=\"" + getName() + "\">");
		try {
			for (Consumer light : getElectrics()) 
				xml.append(light.toXML());
		} catch (NoSuchDevicesInRoom e) {}
		try {
			for (Consumer water : getWaters()) 
				xml.append(water.toXML());
		} catch (NoSuchDevicesInRoom e) {}
		xml.append("</Room>");
		return xml.toString();
	}
	
	/**
	 * Gets the consumers of type.
	 *
	 * @param type the type
	 * @return the consumers of type
	 * @throws NoSuchDevicesInRoom the no such devices in room
	 */
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
	
	/**
	 * Gets the prices map.
	 *
	 * @return the prices map
	 */
	public Map<String, Amount> getPricesMap() {
		Map<String, Amount> map = new HashMap<String, Amount>();
		map.put("Light", getLightPrice());
		map.put("Water", getWaterPrice());
		map.put("Heating", getHeating());
		map.put("Appliances", getAppliancesPrice());
		map.put("Home Cinema", getHomeCinemaPrice());
		return map;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@SuppressLint("DefaultLocale")
	@Override
	public String toString() {
		return getName().toUpperCase();
	}
}
