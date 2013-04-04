package robindecroon.homeviz.fragments.total;

import libraries.nielsbillen.SpinnerListener;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.util.webviews.MyJavaScriptInterface;
import robindecroon.homeviz.util.webviews.MyWebViewClient;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class TotalTreeMapFragment extends OptionSpinnerFragment implements
		SpinnerListener {

	private static View lastView;

	private static FragmentActivity context;

	private static int lastOption;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.total_treemap_layout,
				container, false);

		context = getActivity();
		lastView = rootView;

		initOptionSpinner(rootView, R.id.total_spinner, R.id.total_arrow_left,
				R.id.total_arrow_right);

		Bundle args = getArguments();
		lastOption = args.getInt(Constants.TREEMAP_OPTION);
		Log.i(getClass().getSimpleName(), "option: " + lastOption);
		loadTreemap(rootView, lastOption);

		TextView userView = (TextView) rootView
				.findViewById(R.id.you_current_user);
		userView.setText(R.string.you_text);

		// GraphUser user = ((HomeVizApplication)
		// getActivity().getApplication()).getFacebookUser();
		// if (user != null) {
		// LinearLayout layout = (LinearLayout) rootView
		// .findViewById(R.id.you_user_layout);
		// layout.removeAllViews();
		// ProfilePictureView profilePictureView = new ProfilePictureView(
		// getActivity());
		// profilePictureView.setProfileId(user.getId());
		// layout.addView(profilePictureView);
		// }

		return rootView;
	}

	private static void loadTreemap(View rootView, int option) {
		WebView myBrowser = (WebView) rootView.findViewById(R.id.you_webview);

		final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(
				context);
		myBrowser.addJavascriptInterface(myJavaScriptInterface,
				"AndroidFunction");

		myBrowser.setWebViewClient(new MyWebViewClient(myBrowser,
				MyWebViewClient.TREEMAP, ((HomeVizApplication) context
						.getApplication()).getRooms(), option));

		myBrowser.setBackgroundColor(0x00000000);

		myBrowser.getSettings().setJavaScriptEnabled(true);
		myBrowser.getSettings().setUseWideViewPort(true);
		myBrowser.getSettings().setLoadWithOverviewMode(true);

		myBrowser.loadUrl("javascript:window.location.reload( true )");
		myBrowser.loadUrl("file:///android_asset/www/treemap.html");
	}

	public static void resetViews() {
		loadTreemap(lastView, lastOption);
	}
}
