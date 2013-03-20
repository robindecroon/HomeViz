package robindecroon.layout;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.util.webviews.MyJavaScriptInterface;
import robindecroon.homeviz.util.webviews.MyWebView;
import robindecroon.homeviz.util.webviews.MyWebViewClient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class YouFragment extends Fragment {
	
	private MyWebView myBrowser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.you_layout,
				container, false);

		myBrowser = (MyWebView) rootView.findViewById(R.id.you_webview);

		final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(
				getActivity());
		myBrowser.addJavascriptInterface(myJavaScriptInterface,
				"AndroidFunction");

		myBrowser.setWebViewClient(new MyWebViewClient(myBrowser,
				MyWebViewClient.TREEMAP, ((HomeVizApplication) getActivity().getApplication()).getRooms()));
		
		myBrowser.setBackgroundColor(0x00000000);

		myBrowser.getSettings().setJavaScriptEnabled(true);
		myBrowser.getSettings().setUseWideViewPort(true);
		myBrowser.getSettings().setLoadWithOverviewMode(true);
		myBrowser.loadUrl("file:///android_asset/www/treemap.html");

		TextView userView = (TextView) rootView.findViewById(R.id.you_current_user);
		userView.setText(R.string.you_text);
		
		GraphUser user = ((HomeVizApplication) getActivity().getApplication()).getFacebookUser();
		if( user != null) {
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.you_user_layout);
			layout.removeAllViews();
			ProfilePictureView profilePictureView = new ProfilePictureView(getActivity());
			profilePictureView.setProfileId(user.getId());
			layout.addView(profilePictureView);
		}
		
		myBrowser.loadUrl("javascript:window.location.reload( true )");
		myBrowser.loadUrl("file:///android_asset/www/treemap.html");
		
		return rootView;
	}
	
	@Override
	public String toString() {
		return "Total (treemap)";
	}

}
