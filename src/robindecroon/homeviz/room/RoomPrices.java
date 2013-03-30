package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Amount;

public interface RoomPrices {

	/**
	 * @return the light
	 */
	public abstract Amount getLightPrice();

	/**
	 * @return the water
	 */
	public abstract Amount getWaterPrice();

	/**
	 * @return the appliances
	 */
	public abstract Amount getAppliancesPrice();

	/**
	 * @return the tv
	 */
	public abstract Amount getHomeCinemaPrice();

}