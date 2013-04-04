/**
 * 
 */
package robindecroon.homeviz.util.webviews;

import java.util.List;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.room.Room.ConsumerType;
import robindecroon.homeviz.total.JsonObject;
import robindecroon.homeviz.total.TreemapType;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

/**
 * @author Robin
 * 
 */
public class MyWebViewClient extends WebViewClient {

	public final static String CHART = "Chart";
	public final static String TREEMAP = "Treemap";

	WebView myBrowser;
	private String string;
	private String name;

	public MyWebViewClient(WebView myBrowser, String name, List<Room> rooms,
			int option) {
		this.myBrowser = myBrowser;
		this.setName(name);
		Gson gson = new Gson();
		ConsumerType consType = null;
		TreemapType treemapType = null;
		switch (option) {
		case Constants.TREEMAP_LIGHT_WATT:
			consType = ConsumerType.Light;
			treemapType = TreemapType.Watt;
			break;
		case Constants.TREEMAP_LIGHT_KWH:
			consType = ConsumerType.Light;
			treemapType = TreemapType.Power;
			break;
		case Constants.TREEMAP_APPLIANCE_WATT:
			consType = ConsumerType.Appliance;
			treemapType = TreemapType.Watt;
			break;
		case Constants.TREEMAP_APPLIANCE_KWH:
			consType = ConsumerType.Appliance;
			treemapType = TreemapType.Power;
			break;
		case Constants.TREEMAP_HOMECINEMA_WATT:
			consType = ConsumerType.HomeCinema;
			treemapType = TreemapType.Watt;
			break;
		case Constants.TREEMAP_HOMECINEMA_KWH:
			consType = ConsumerType.HomeCinema;
			treemapType = TreemapType.Power;
			break;
		}
		this.string = "'"
				+ gson.toJson(JsonObject.getWattJSON(rooms, consType,
						treemapType)) + "'";
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		if (name.equals(TREEMAP)) {
			myBrowser.loadUrl("javascript:window.setTimeout(go(" + string
					+ "),100)");
		} else if (name.equals(CHART)) {
			myBrowser.loadUrl("javascript:go(" + string + ")");
		}
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		Log.e("WebViewClient", "Error in  " + name + "description: "
				+ description);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
