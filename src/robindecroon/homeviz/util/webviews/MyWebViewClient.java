/**
 * 
 */
package robindecroon.homeviz.util.webviews;

import robindecroon.homeviz.you.JsonObject;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

/**
 * @author Robin
 *
 */
public class MyWebViewClient extends WebViewClient {
	
	WebView myBrowser;
	private String json;
	private String name;
	
	public MyWebViewClient(WebView myBrowser, String name) {
		this.myBrowser = myBrowser;
		this.setName(name);
		Gson gson = new Gson();
		this.json= "'" + gson.toJson(JsonObject.getTestJson()) + "'";
	}
	
	public void setJson(String json) {
		this.json = json;
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		myBrowser.loadUrl("javascript:window.setTimeout(go(" + json
				+ "),35)");
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		Log.e("WebViewClient", "Error in  " + name + "description: " + description);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
