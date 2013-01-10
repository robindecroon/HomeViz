package robindecroon.homeviz.you;

import robindecroon.homeviz.R;
import robindecroon.homeviz.R.id;
import robindecroon.homeviz.R.layout;
import robindecroon.homeviz.usage.FullScreenActivity;
import robindecroon.homeviz.util.SystemUiHider;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint("SetJavaScriptEnabled")
public class YouActivity extends FullScreenActivity {

	private WebView myBrowser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.you_layout);

		myBrowser = (WebView) findViewById(R.id.you_webview);

		final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(
				this);
		myBrowser.addJavascriptInterface(myJavaScriptInterface,
				"AndroidFunction");

//		myBrowser.loadUrl("javascript:location.reload()");	
		myBrowser.getSettings().setJavaScriptEnabled(true);
		
//		myBrowser.getSettings().setLoadWithOverviewMode(true);
//		myBrowser.getSettings().setUseWideViewPort(true);
		myBrowser.loadUrl("http://www.student.kuleuven.be/~s0206928/d3/test.html");
//		myBrowser.loadUrl("javascript:drawRobin()");		

		refreshElements();

		// myBrowser.loadUrl("javascript:callFromActivity(\""+msgToSend+"\")");
	}
	 
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//
//		myBrowser = null;
//		myBrowser = (WebView) findViewById(R.id.you_webview);
//
//		final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(
//				this);
//		myBrowser.addJavascriptInterface(myJavaScriptInterface,
//				"AndroidFunction");
//
//		myBrowser.getSettings().setJavaScriptEnabled(true);
//		
//		myBrowser.getSettings().setLoadWithOverviewMode(true);
//		myBrowser.getSettings().setUseWideViewPort(true);
//		myBrowser.loadUrl("http://www.student.kuleuven.be/~s0206928/d3/test.html");
//		myBrowser.loadUrl("javascript:drawRobin()");
//	}
	
//	@Override
//	protected void onPostCreate (Bundle savedInstanceState) {
//		super.onPostCreate(savedInstanceState);
//		myBrowser.loadUrl("http://www.student.kuleuven.be/~s0206928/d3/test.html");
//		myBrowser.loadUrl("javascript:draw()");		
//	}

	public class MyJavaScriptInterface {
		Context mContext;

		MyJavaScriptInterface(Context c) {
			mContext = c;
		}

		public void showToast(String toast) {
			Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
		}

		public void openAndroidDialog() {
			AlertDialog.Builder myDialog = new AlertDialog.Builder(
					YouActivity.this);
			myDialog.setTitle("DANGER!");
			myDialog.setMessage("You can do what you want!");
			myDialog.setPositiveButton("ON", null);
			myDialog.show();
		}

	}

	@Override
	public void onSwypeToLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSwypeToRight() { 
		myBrowser.loadUrl("javascript:drawRobin()");		
		// TODO Auto-generated method stub

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
}
