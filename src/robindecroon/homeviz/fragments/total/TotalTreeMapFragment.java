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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class TotalTreeMapFragment extends OptionSpinnerFragment implements
		SpinnerListener {

	private static View lastView;

	private static FragmentActivity context;

	private static int lastOption;

	private static int treemapType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.total_treemap_layout,
				container, false);

		context = getActivity();
		lastView = rootView;

		initOptionSpinner(rootView, R.id.total_spinner, R.id.total_arrow_left,
				R.id.total_arrow_right);

		RadioButton wattButton = (RadioButton) rootView
				.findViewById(R.id.total_radio_watt);
		wattButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				treemapType = 0;
				loadTreemap(lastView, lastOption + treemapType);
			}
		});

		RadioButton kwhButton = (RadioButton) rootView
				.findViewById(R.id.total_radio_kwh);
		kwhButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				treemapType = 10;
				loadTreemap(lastView, lastOption + treemapType);
			}
		});
		if (treemapType == 0) {
			wattButton.setChecked(true);
			kwhButton.setChecked(false);
		} else {
			wattButton.setChecked(false);
			kwhButton.setChecked(true);
		}

		Bundle args = getArguments();
		lastOption = args.getInt(Constants.TREEMAP_OPTION);
		loadTreemap(lastView, lastOption + treemapType);

		TextView userView = (TextView) rootView
				.findViewById(R.id.you_current_user);
		userView.setText(R.string.you_text);

		return rootView;
	}

	private static void loadTreemap(View rootView, int option) {
		WebView myBrowser = (WebView) rootView.findViewById(R.id.you_webview);

		final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(
				context);
		myBrowser.addJavascriptInterface(myJavaScriptInterface,
				"AndroidFunction");

		myBrowser.setWebViewClient(new MyWebViewClient(myBrowser,
				Constants.WEBVIEW_TREEMAP, ((HomeVizApplication) context
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
