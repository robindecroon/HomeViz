package robindecroon.fragments.total;

import robindecroon.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.util.webviews.MyJavaScriptInterface;
import robindecroon.homeviz.util.webviews.MyWebViewClient;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

@SuppressLint("SetJavaScriptEnabled")
public class TotalTreeMapFragment extends OptionSpinnerFragment {

	private WebView myBrowser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.total_treemap_layout,
				container, false);

		initOptionSpinner(rootView, R.id.total_spinner, R.id.total_arrow_left,
				R.id.total_arrow_right);

		myBrowser = (WebView) rootView.findViewById(R.id.you_webview);

		final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(
				getActivity());
		myBrowser.addJavascriptInterface(myJavaScriptInterface,
				"AndroidFunction");

		myBrowser.setWebViewClient(new MyWebViewClient(myBrowser,
				MyWebViewClient.TREEMAP, ((HomeVizApplication) getActivity()
						.getApplication()).getRooms()));

		myBrowser.setBackgroundColor(0x00000000);

		myBrowser.getSettings().setJavaScriptEnabled(true);
		myBrowser.getSettings().setUseWideViewPort(true);
		myBrowser.getSettings().setLoadWithOverviewMode(true);
		myBrowser.loadUrl("file:///android_asset/www/treemap.html");

		TextView userView = (TextView) rootView
				.findViewById(R.id.you_current_user);
		userView.setText(R.string.you_text);

		GraphUser user = ((HomeVizApplication) getActivity().getApplication())
				.getFacebookUser();
		if (user != null) {
			LinearLayout layout = (LinearLayout) rootView
					.findViewById(R.id.you_user_layout);
			layout.removeAllViews();
			ProfilePictureView profilePictureView = new ProfilePictureView(
					getActivity());
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
