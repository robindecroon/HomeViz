package robindecroon.homeviz.you;

import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.TouchListener;
import robindecroon.homeviz.usage.FullScreenActivity;
import robindecroon.homeviz.util.SystemUiHider;
import robindecroon.homeviz.util.webviews.MyJavaScriptInterface;
import robindecroon.homeviz.util.webviews.MyWebView;
import robindecroon.homeviz.util.webviews.MyWebViewClient;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint("SetJavaScriptEnabled")
public class YouActivity extends FullScreenActivity {

	private MyWebView myBrowser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.you_layout);

		myBrowser = (MyWebView) findViewById(R.id.you_webview);
		myBrowser.setListener(new TouchListener(this));

		final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(
				this);
		myBrowser.addJavascriptInterface(myJavaScriptInterface,
				"AndroidFunction");

		myBrowser.setWebViewClient(new MyWebViewClient(myBrowser,
				"TreemapClient"));

		myBrowser.getSettings().setJavaScriptEnabled(true);
		myBrowser.getSettings().setUseWideViewPort(true);
		myBrowser.getSettings().setLoadWithOverviewMode(true);
		myBrowser.loadUrl("file:///android_asset/www/treemap.html");

		refreshElements();
	}

	@Override
	public void onSwypeToLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSwypeToRight() {

	}

	@Override
	public void onSwypeToUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSwypeToDown() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setPeriod() {
		final TextView youPeriod = (TextView) findViewById(R.id.you_period);
		youPeriod.setText(currentPeriod.getName(this));
	}

	@Override
	public void refreshElements() {
		super.refreshElements();
		// Nodig want anders valt resizen op.
		myBrowser.loadUrl("javascript:window.location.reload( true )");
		myBrowser.loadUrl("file:///android_asset/www/treemap.html");
	}
}
