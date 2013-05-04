/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments.total;

import libraries.nielsbillen.SpinnerListener;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.total.MyWebViewClient;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * The Class TotalTreeMapFragment.
 */
@SuppressLint("SetJavaScriptEnabled")
public class TotalTreeMapFragment extends OptionSpinnerFragment implements
		SpinnerListener {

	/** The context. */
	private static FragmentActivity context;

	/** The treemap type. */
	private static int treemapType;

	/** The last arguments. */
	private static Bundle args;

	/** The treemap. */
	private WebView treemap;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View totalFragmentView = inflater.inflate(
				R.layout.total_treemap_layout, container, false);

		context = getActivity();
		args = getArguments();

		initOptionSpinner(totalFragmentView, R.id.total_spinner,
				R.id.total_arrow_left, R.id.total_arrow_right);

		final int lastOption = args.getInt(Constants.TREEMAP_OPTION);
		RadioButton wattButton = initWattButton(totalFragmentView, lastOption);
		RadioButton kwhButton = initKwhButton(totalFragmentView, lastOption);
		if (treemapType == 0) {
			wattButton.setChecked(true);
			kwhButton.setChecked(false);
		} else {
			wattButton.setChecked(false);
			kwhButton.setChecked(true);
		}

		initTreemap(totalFragmentView);

		setSizeTreemapView(totalFragmentView, lastOption);

		TextView userView = (TextView) totalFragmentView
				.findViewById(R.id.you_current_user);
		userView.setText(R.string.you_text);

		return totalFragmentView;
	}

	/**
	 * Initialize the treemap.
	 *
	 * @param totalFragmentView the total fragment view
	 */
	private void initTreemap(View totalFragmentView) {
		treemap = (WebView) totalFragmentView.findViewById(R.id.you_webview);
		treemap.setBackgroundColor(0x00000000);
		treemap.getSettings().setJavaScriptEnabled(true);
		treemap.getSettings().setUseWideViewPort(true);
		treemap.getSettings().setLoadWithOverviewMode(true);
	}

	/**
	 * Sets the size treemap view.
	 *
	 * @param totalFragmentView the total fragment view
	 * @param lastOption the last option
	 */
	private void setSizeTreemapView(View totalFragmentView, final int lastOption) {
		totalFragmentView.post(new Runnable() {
			public void run() {
				Rect rect = new Rect();
				Window win = context.getWindow(); // Get the Window
				win.getDecorView().getWindowVisibleDisplayFrame(rect);
				// Get the height of Status Bar
				int statusBarHeight = rect.top;
				// Get the height occupied by the decoration contents
				int contentViewTop = win
						.findViewById(Window.ID_ANDROID_CONTENT).getTop();
				// Calculate titleBarHeight by deducting statusBarHeight from
				// contentViewTop
				int titleBarHeight = contentViewTop - statusBarHeight;

				// By now we got the height of titleBar & statusBar
				// Now lets get the screen size
				DisplayMetrics metrics = new DisplayMetrics();
				context.getWindowManager().getDefaultDisplay()
						.getMetrics(metrics);
				int screenHeight = metrics.heightPixels;
				int screenWidth = metrics.widthPixels;

				// Now calculate the height that our layout can be set
				// If you know that your application doesn't have statusBar
				// added, then don't add here also. Same applies to application
				// bar also
				int layoutHeight = screenHeight
						- (titleBarHeight + statusBarHeight);
				LayoutParams lp = treemap.getLayoutParams();
				lp.width = screenWidth;
				lp.height = layoutHeight;
				treemap.setLayoutParams(lp);
				loadTreemap(lastOption);
			}

		});
	}

	/**
	 * Load treemap.
	 *
	 * @param option the option
	 */
	private void loadTreemap(int option) {
		treemap.setWebViewClient(new MyWebViewClient(treemap,
				Constants.WEBVIEW_TREEMAP, ((HomeVizApplication) context
						.getApplication()).getRooms(), option + treemapType));
		treemap.loadUrl("file:///android_asset/www/treemap.html");
	}

	/**
	 * Initialize the kwh button.
	 *
	 * @param totalFragmentView the total fragment view
	 * @param lastOption the last option
	 * @return the radio button
	 */
	private RadioButton initKwhButton(View totalFragmentView,
			final int lastOption) {
		RadioButton kwhButton = (RadioButton) totalFragmentView
				.findViewById(R.id.total_radio_kwh);
		kwhButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				treemapType = 10;
				loadTreemap(lastOption);
			}
		});
		return kwhButton;
	}

	/**
	 * Initialize the watt button.
	 *
	 * @param totalFragmentView the total fragment view
	 * @param lastOption the last option
	 * @return the radio button
	 */
	private RadioButton initWattButton(View totalFragmentView,
			final int lastOption) {
		RadioButton wattButton = (RadioButton) totalFragmentView
				.findViewById(R.id.total_radio_watt);
		wattButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				treemapType = 0;
				loadTreemap(lastOption);
			}
		});
		return wattButton;
	}

	/**
	 * Reset views.
	 */
	public static void resetViews() {
		Fragment totalTreeMapFragment = new TotalTreeMapFragment();
		totalTreeMapFragment.setArguments(args);
		context.getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, totalTreeMapFragment).commit();
	}
}
