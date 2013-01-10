package robindecroon.homeviz.room;

import robindecroon.homeviz.exceptions.ParseXMLException;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;

public class Light {
	
	private String id;
	
	private Amount price;

	public Light() {
		this.price = new Amount(Math.random()/3);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		if(id == null) {
			throw new ParseXMLException(this);
		}
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Amount getPrice(Period currentPeriod) {
		return price.multiply(currentPeriod.getMultiplier());
	}

}
