/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */

package robindecroon.homeviz.total;

import java.util.List;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.house.device.ConsumerType;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

/**
 * The Class MyWebViewClient.
 */
public class MyWebViewClient extends WebViewClient {

	/** The my browser. */
	WebView myBrowser;
	
	/** The json. */
	private String json;

	/**
	 * Instantiates a new my web view client.
	 *
	 * @param myBrowser the my browser
	 * @param rooms the rooms
	 * @param option the option
	 */
	public MyWebViewClient(WebView myBrowser, List<Room> rooms,
			int option) {
		this.myBrowser = myBrowser;
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
		this.json = "'"	+ gson.toJson(JsonObject.getWattJSON(rooms, consType, treemapType)) + "'";
	}

	/**
	 * Sets the string.
	 *
	 * @param string the new string
	 */
	public void setString(String string) {
		this.json = string;
	}

	/* (non-Javadoc)
	 * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)
	 */
	@Override
	public void onPageFinished(WebView view, String url) {
		myBrowser.loadUrl("javascript:go(" + json + ")");
	}

	/* (non-Javadoc)
	 * @see android.webkit.WebViewClient#shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String)
	 */
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.webkit.WebViewClient#onReceivedError(android.webkit.WebView, int, java.lang.String, java.lang.String)
	 */
	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		Log.e("WebViewClient", "Error in webviewclien with description: " + description);
	}
}
