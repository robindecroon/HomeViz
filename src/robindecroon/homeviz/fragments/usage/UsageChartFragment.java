/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments.usage;

import java.util.Map;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.fragments.OptionSpinnerFragment;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.usage.GoogleChartTools;
import robindecroon.homeviz.usage.GoogleChartType;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Network;
import robindecroon.homeviz.util.UsageActivityUtils;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;

/**
 * The Class UsageChartFragment.
 */
@SuppressLint("SetJavaScriptEnabled")
public class UsageChartFragment extends OptionSpinnerFragment {

	/** The chart. */
	private WebView chart;
	
	/** The content. */
	private LinearLayout content;
	
	/** The data. */
	private Map<String, Amount> data;


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View usageChartView = inflater.inflate(R.layout.usage_detail_layout,
				container, false);
		content = (LinearLayout) usageChartView
				.findViewById(R.id.detail_container);

		initOptionSpinner(usageChartView, R.id.chart_spinner,
				R.id.chart_arraw_left, R.id.chart_arrow_right);

		final FragmentActivity context = getActivity();
		HomeVizApplication app = (HomeVizApplication) context.getApplication();
		Bundle args = getArguments();
		Room currentRoom = null;
		if (Network.isNetworkConnected(context) && args != null) {

			int roomIndex = getArguments().getInt(Constants.USAGE_ROOM);
			currentRoom = app.getRooms().get(roomIndex);
			data = currentRoom.getPricesMap();

			initChartWebView(context);
			loadGoogleChartTools(usageChartView, context);
		} else {
			Log.e(getClass().getSimpleName(), "No internet connection!");
			content.addView(UsageActivityUtils.getNoNetworkConnection(context));
		}
		return usageChartView;
	}

	/**
	 * Initialize the chart web view.
	 *
	 * @param context the context
	 */
	private void initChartWebView(final FragmentActivity context) {
		chart = new WebView(context);
		chart.setLayoutParams(content.getLayoutParams());
		chart.setBackgroundColor(0x00000000);
		chart.getSettings().setJavaScriptEnabled(true);
		chart.getSettings().setUseWideViewPort(true);
		chart.getSettings().setLoadWithOverviewMode(true);
//		chart.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		chart.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		chart.getSettings().setSupportZoom(false);
//		chart.getSettings().setBuiltInZoomControls(false);
//		chart.getSettings().setDisplayZoomControls(false);
		chart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		chart.setInitialScale(1);		
		content.addView(chart);
	}


	/**
	 * Load google chart tools.
	 *
	 * @param usageChartView the usage chart view
	 * @param context the context
	 */
	private void loadGoogleChartTools(View usageChartView,
			final FragmentActivity context) {
		usageChartView.post(new Runnable() { 
        @Override
		public void run() { 
            Rect rect = new Rect(); 
            Window win = context.getWindow();  // Get the Window
            win.getDecorView().getWindowVisibleDisplayFrame(rect); 
            // Get the height of Status Bar 
            int statusBarHeight = rect.top; 
            // Get the height occupied by the decoration contents 
            int contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT).getTop(); 
            // Calculate titleBarHeight by deducting statusBarHeight from contentViewTop  
            int titleBarHeight = contentViewTop - statusBarHeight; 
 
            // By now we got the height of titleBar & statusBar
            // Now lets get the screen size
            DisplayMetrics metrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(metrics);   
            int screenHeight = metrics.heightPixels;
            int screenWidth = metrics.widthPixels;
 
            // Now calculate the height that our layout can be set
            // If you know that your application doesn't have statusBar added, then don't add here also. Same applies to application bar also 
            int layoutHeight = screenHeight - (titleBarHeight + statusBarHeight);
            
            // Finally load the Google Chart Tools figure with the proper dimensions
            if (!data.isEmpty()) {
    			GoogleChartType chartType = determineType();
    			String chartTitle = getResources().getString(R.string.usage_chart_currency);

    			String url = GoogleChartTools.getGoogleChartToolsHTML(chartTitle, context, data, 
    					screenWidth, layoutHeight - 100, chartType);
    			chart.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);
    		} else {
    			throw new IllegalStateException(
    					"Prices data in this room shouldn't be empty!");
    		}
        }

        
		private GoogleChartType determineType() {
			GoogleChartType type;
			switch (getArguments().getInt(Constants.USAGE_BUNDLE_TYPE)) {
			case 1:
				type = GoogleChartType.BarChart;
				break;
			case 2:
				type = GoogleChartType.ColumnChart;
				break;
			default:
				throw new IllegalStateException("Wrong google chart element: "
						+ getArguments().getInt(Constants.USAGE_BUNDLE_TYPE));
			}
			return type;
		} 
       });
	}
}
