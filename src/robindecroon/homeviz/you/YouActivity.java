package robindecroon.homeviz.you;

import java.io.File;
import java.io.FilenameFilter;

import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.activity.FullScreenActivity;
import robindecroon.homeviz.listeners.TouchListener;
import robindecroon.homeviz.util.PeriodListener;
import robindecroon.homeviz.util.webviews.MyJavaScriptInterface;
import robindecroon.homeviz.util.webviews.MyWebView;
import robindecroon.homeviz.util.webviews.MyWebViewClient;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
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
				MyWebViewClient.TREEMAP));
		
		myBrowser.setBackgroundColor(0x00000000);

		myBrowser.getSettings().setJavaScriptEnabled(true);
		myBrowser.getSettings().setUseWideViewPort(true);
		myBrowser.getSettings().setLoadWithOverviewMode(true);
		myBrowser.loadUrl("file:///android_asset/www/treemap.html");

		TextView userView = (TextView) findViewById(R.id.you_current_user);
		userView.setText(((HomeVizApplication) getApplication()).getCurrentUser().getName());
		
		GraphUser user = ((HomeVizApplication) getApplication()).getFacebookUser();
		if( user != null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.you_user_layout);
			layout.removeAllViews();
			ProfilePictureView profilePictureView = new ProfilePictureView(this);
			profilePictureView.setProfileId(user.getId());
			layout.addView(profilePictureView);
		}
		
		
		
		refreshElements();
		
//		try {
//			File f = new File("http://www.student.kuleuven.be/~s0206928/d3/");
//
//			File[] files = f.listFiles(new FilenameFilter() {
//			    @Override
//				public boolean accept(File dir, String name) {
//			        // Specify the extentions of files to be included.
//			        return name.endsWith(".json") || name.endsWith(".gif");
//			    }
//			});
//
//			if(files == null) return;
//			for (int indx = 0; indx < files.length; indx++) {
//				System.out.println(files[indx].getName());
//			}
//		} catch (Exception e) {
//			System.out.println("MESSSSAAAGGGEEEE");
////			System.out.println(e.getMessage());
//			e.printStackTrace();
//			System.out.println("nnnnnnnnnnnnnnaaaaaaaaaaaaaa");
//		}
	}

	@Override
	public void onSwypeToLeft() {

	}

	@Override
	public void onSwypeToRight() {

	}

	@Override
	public void onSwypeToUp() {

	}

	@Override
	public void onSwypeToDown() {

	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setPeriod() {
		final TextView youPeriod = (TextView) findViewById(R.id.you_period);
		youPeriod.setText(currentPeriod.getName(this));
		youPeriod.setOnClickListener(new PeriodListener(this));
	}

	@Override
	public void refreshElements() {
		super.refreshElements();
		// Nodig want anders valt resizen op.
		myBrowser.loadUrl("javascript:window.location.reload( true )");
		myBrowser.loadUrl("file:///android_asset/www/treemap.html");
	}
}
