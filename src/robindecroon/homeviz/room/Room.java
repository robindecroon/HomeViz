package robindecroon.homeviz.room;

import java.util.ArrayList;
import java.util.List;

import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;

/**
 * Klasse die een kamer voorstelt.
 * 
 * @author Robin
 * 
 */
public class Room {

	/**
	 * De naam van de kamer.
	 */
	private String name;

	private Amount light;
	private Amount water;
	private Amount heating;
	private Amount appliances;
	private Amount tv;

	private List<Light> lights = new ArrayList<Light>();

	/**
	 * Constructor nodig voor de XML parser
	 */
	public Room() {
		this.light = new Amount(Math.random());
		this.water = new Amount(Math.random());
		this.heating = new Amount(Math.random());
		this.appliances = new Amount(Math.random());
		this.tv = new Amount(Math.random());
	};

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
		if (name == null)
			// TODO eventueel een exception
			return "Unknown Room";
		return name;
	}

	public CharSequence getTotalPrice(Period p) {
		Amount total = getLightPrice(p).add(getWater(p)).add(getHeating(p))
				.add(getAppliances(p)).add(getTv(p));
		return total.toString();
	}

	/**
	 * @return the light
	 */
	public Amount getLightPrice(Period currentPeriod) {
		Amount total = new Amount(0);
		for(Light light:lights) {
			total = total.add(light.getPrice(currentPeriod));
		}
		return total;
	}

	/**
	 * @param light
	 *            the light to set
	 */
	public void setLight(Amount light) {
		this.light = light;
	}

	/**
	 * @return the water
	 */
	public Amount getWater(Period currentPeriod) {
		return water.multiply(currentPeriod.getMultiplier());
	}

	/**
	 * @param water
	 *            the water to set
	 */
	public void setWater(Amount water) {
		this.water = water;
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

	/**
	 * @return the appliances
	 */
	public Amount getAppliances(Period currentPeriod) {
		return appliances.multiply(currentPeriod.getMultiplier());
	}

	/**
	 * @param appliances
	 *            the appliances to set
	 */
	public void setAppliances(Amount appliances) {
		this.appliances = appliances;
	}

	/**
	 * @return the tv
	 */
	public Amount getTv(Period currentPeriod) {
		return tv.multiply(currentPeriod.getMultiplier());
	}

	/**
	 * @param tv
	 *            the tv to set
	 */
	public void setTv(Amount tv) {
		this.tv = tv;
	}

	public void addLight(Light tempLight) {
		this.lights.add(tempLight);
	}
	
	public List<Light> getLights() throws NoSuchDevicesInRoom {
		if(this.lights.size() == 0) {
			throw new NoSuchDevicesInRoom(this);
		}
		return this.lights;
	}

	public Light getLight(String id) {
		for (Light light : lights) {
			if (light.getId().equals(id)) {
				return light;
			}
		}
		throw new IllegalArgumentException("No light with ID: " + id
				+ " in room: " + this.getName());
	}
}
