/**
 * 
 */
package robindecroon.homeviz;

import android.app.Application;

/**
 * @author Robin
 *
 */
public class HomeVizApplication extends Application {
	
	private Period currentPeriod = Period.CUSTOM;

	/**
	 * 
	 */
	public HomeVizApplication() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the currentPeriod
	 */
	public Period getCurrentPeriod() {
		return currentPeriod;
	}

	/**
	 * @param currentPeriod the currentPeriod to set
	 */
	public void setCurrentPeriod(Period currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

}
