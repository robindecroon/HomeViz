package robindecroon.homeviz.room;

import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Period;

public interface RoomPrices {

	/**
	 * @return the light
	 */
	public abstract Amount getLightPrice(Period currentPeriod);

	/**
	 * @return the water
	 */
	public abstract Amount getWaterPrice(Period currentPeriod);

	/**
	 * @return the appliances
	 */
	public abstract Amount getAppliancesPrice(Period currentPeriod);

	/**
	 * @return the tv
	 */
	public abstract Amount getHomeCinemaPrice(Period currentPeriod);

}