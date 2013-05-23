/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import libraries.stackoverflow.NoDefaultSpinner;

import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.fragments.metaphor.MetaphorContainerFragment;
import robindecroon.homeviz.fragments.total.TotalTreeMapFragment;
import robindecroon.homeviz.fragments.usage.UsageContainerFragment;
import robindecroon.homeviz.fragments.yield.YieldFragment;
import robindecroon.homeviz.listeners.actionbarlisteners.MetaphorActionBarSpinnerListener;
import robindecroon.homeviz.listeners.actionbarlisteners.TotalActionBarSpinnerListener;
import robindecroon.homeviz.listeners.actionbarlisteners.UsageActionBarSpinnerListener;
import robindecroon.homeviz.listeners.actionbarlisteners.YieldActionBarSpinnerListener;
import robindecroon.homeviz.task.DownloadLoxoneXMLTask;
import robindecroon.homeviz.util.Network;
import robindecroon.homeviz.util.Period;
import robindecroon.homeviz.util.Storage;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.xml.HomeVizXMLParser;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * The Class MainActivity.
 */
public class MainActivity extends FragmentActivity implements LocationListener {
	
	/** The current period. */
	public static Period currentPeriod = Period.WEEK;

	/** The last catergory. */
	public static int lastCatergory;
	/** The last position. */
	public static int lastPosition;
	/** The page. */
	public static int page;
	/** The downloaded. */
	public static boolean downloaded = false;
	/** The click counter. */
	public static int clickCounter = 0;

	/** The category stack. */
	public static Stack<Integer> categoryStack = new Stack<Integer>();
	/** The selection stack. */
	public static Stack<Integer> selectionStack = new Stack<Integer>();

	/** The usage action bar spinner. */
	private NoDefaultSpinner usageActionBarSpinner;
	/** The total action bar spinner. */
	private NoDefaultSpinner totalActionBarSpinner;
	/** The metaphor action bar spinner. */
	private NoDefaultSpinner metaphorActionBarSpinner;
	/** The yield action bar spinner. */
	private NoDefaultSpinner yieldActionBarSpinner;

	/**
	 * Clear all values.
	 */
	private void clearStatics() {
		downloaded = false;
		lastCatergory = Constants.USAGE;
		lastPosition = 0;
		categoryStack.clear();
		selectionStack.clear();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		// init the actionbar and spinners
		setupActionBar();

		Bundle extras = getIntent().getExtras();
		// Check if initial activity
		if (extras == null) {
			// read the configuration XML file
			readHomeVizXML();

			// start with Usage (icons)
			lastPosition = 0;
			startUsageContainerFragment(0);

			// search the current location
			initCurrentLocation();
		} else {
			lastPosition = extras.getInt(Constants.SELECTION);
			int category = extras.getInt(Constants.CATEGORY);
			switch (category) {
			case Constants.USAGE:
				startUsageContainerFragment(extras.getInt("room"));
				break;
			case Constants.TOTAL:
				startTotalFragment();
				break;
			case Constants.METAPHOR:
				startMetaphorFragment();
				break;
			case Constants.YIELD:
				startYieldFragment();
				break;
			}
		}
		if (!downloaded) {
			downloadStatistics();
		}
	}

	/**
	 * Download statistics.
	 */
	private void downloadStatistics() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		String user = sp.getString("loxone_user", Constants.LOXONE_DEFAULT_USER);
		String password = sp.getString("loxone_password", Constants.LOXONE_DEFAULT_PASSWORD);
		String ip = sp.getString("loxone_ip", Constants.LOXONE_DEFAULT_IP);
		new DownloadLoxoneXMLTask(((HomeVizApplication) getApplication()))
				.execute(user, password, ip);
		downloaded = true;
	}

	/**
	 * Start total fragment.
	 *
	 * @param selection the selection
	 */
	private void startTotalFragment() {
		lastCatergory = Constants.TOTAL;
		totalActionBarSpinner.setSelection(lastPosition);
		Fragment totalTreeMapFragment = new TotalTreeMapFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.TREEMAP_OPTION, lastPosition);
		totalTreeMapFragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, totalTreeMapFragment).commit();
	}

	/**
	 * Start metaphor fragment.
	 *
	 * @param selection the selection
	 */
	private void startMetaphorFragment() {
		lastCatergory = Constants.METAPHOR;
		metaphorActionBarSpinner.setSelection(lastPosition);
		Fragment metaphorFragment = new MetaphorContainerFragment();
		Bundle args = new Bundle();
		args.putBoolean(Constants.METAPHOR_CONSUMER, false);
		args.putInt(Constants.METAPHOR_TYPE, lastPosition);
		metaphorFragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, metaphorFragment).commit();
	}

	/**
	 * Start usage container fragment.
	 *
	 * @param selection the selection
	 * @param roomIndex the room index
	 */
	private void startUsageContainerFragment(int roomIndex) {
		lastCatergory = Constants.USAGE;
		usageActionBarSpinner.setSelection(lastPosition);
		Bundle args = new Bundle();
		args.putInt(Constants.USAGE_TYPE, lastPosition);
		args.putInt("room", UsageContainerFragment.getCurrentSelection());
		Fragment usageContainerFragment = new UsageContainerFragment();
		usageContainerFragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, usageContainerFragment).commit();
	}

	/**
	 * Start yield fragment.
	 *
	 * @param selection the selection
	 */
	private void startYieldFragment() {
		lastCatergory = Constants.YIELD;
		yieldActionBarSpinner.setSelection(lastPosition);
		Bundle args = new Bundle();
		args.putInt(Constants.YIELD_TYPE, lastPosition);
		Fragment yieldFragment = new YieldFragment();
		yieldFragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, yieldFragment).commit();
	}

	/**
	 * Initialize the actionbar.
	 *
	 * @return true, if successful
	 */
	private boolean setupActionBar() {
		ActionBar ab = getActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayShowTitleEnabled(false);

		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View actionbarView = inflator.inflate(R.layout.action_bar_title, null);

		initSpinners(actionbarView);

		ab.setCustomView(actionbarView);

		return true;
	}

	/**
	 * Sets the usage icons selection.
	 *
	 * @param selection the new usage icons selection
	 */
	public void setUsageIconsSelection(int selection) {
		usageActionBarSpinner.setSelection(selection);
	}

	/**
	 * Initialize the spinners.
	 *
	 * @param view the rootview
	 */
	public void initSpinners(View view) {
		usageActionBarSpinner = (NoDefaultSpinner) view.findViewById(R.id.usage_spinner);
		usageActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.usage_spinner_data, android.R.layout.simple_spinner_dropdown_item));
		usageActionBarSpinner.setOnItemSelectedListener(new UsageActionBarSpinnerListener(this));
		usageActionBarSpinner.setSaveEnabled(false);

		totalActionBarSpinner = (NoDefaultSpinner) view.findViewById(R.id.you_spinner);
		totalActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.you_spinner_data, android.R.layout.simple_spinner_dropdown_item));
		totalActionBarSpinner.setOnItemSelectedListener(new TotalActionBarSpinnerListener(this));
		totalActionBarSpinner.setSaveEnabled(false);

		metaphorActionBarSpinner = (NoDefaultSpinner) view.findViewById(R.id.metaphor_spinner);
		metaphorActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(
				this, R.array.metaphor_spinner_data, android.R.layout.simple_spinner_dropdown_item));
		metaphorActionBarSpinner.setOnItemSelectedListener(new MetaphorActionBarSpinnerListener(this));
		metaphorActionBarSpinner.setSaveEnabled(false);

		yieldActionBarSpinner = (NoDefaultSpinner) view.findViewById(R.id.yield_spinner);
		yieldActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.yield_spinner_data,	android.R.layout.simple_spinner_dropdown_item));
		yieldActionBarSpinner.setOnItemSelectedListener(new YieldActionBarSpinnerListener(this));
		yieldActionBarSpinner.setSaveEnabled(false);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onPreparePanel(int, android.view.View, android.view.Menu)
	 */
	@Override
	public boolean onPreparePanel(int featureId, View view, Menu menu) {
		super.onPreparePanel(featureId, view, menu); // this returns false if all items are hidden
		return true; // return true to prevent the menu's deletion
	}

	/**
	 * Read the house configuration file.
	 */
	private void readHomeVizXML() {
		try {
			HomeVizApplication app = (HomeVizApplication) getApplication();
			app.reset();
			InputStream in = null;
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			String xmlFileName = settings.getString(Constants.XML_FILE,
					Constants.XML_FILE_NAME);
			if (xmlFileName.equals(Constants.XML_FILE_NAME)) {
				in = getAssets().open(Constants.XML_FILE_NAME);
			} else {
				in = openFileInput(xmlFileName);
			}
			HomeVizXMLParser parser = new HomeVizXMLParser(app);
			parser.parse(in);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			handleHomeMenuButton();
			return true;
		case R.id.menu_settings:
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingsIntent);
			return true;
		case R.id.menu_refresh:
			ToastMessages.refreshingStatistics();
			downloadStatistics();
			return true;
		case R.id.menu_share:
			handleShareMenuButton();
			return true;
		case R.id.menu_about:
			Intent aboutIntent = new Intent(this, AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		case R.id.menu_xml_creator:
			Intent xmlCreatorIntent = new Intent(this, HomeCreatorActivity.class);
			startActivity(xmlCreatorIntent);
			return true;
		case R.id.menu_community:
			ToastMessages.community();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Gets the default ShareIntent.
	 *
	 * @return the default ShareIntent
	 */
	private Intent getDefaultShareIntent() {
		if (!Storage.externalStorageIsAvailable()) {
			throw new IllegalStateException("No external storage available");
		}
		View viewToDraw = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);

		viewToDraw.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(viewToDraw.getDrawingCache());
		viewToDraw.setDrawingCacheEnabled(false);

		String saved = Images.Media.insertImage(
				this.getContentResolver(), bitmap, "HomeViz", "View of HomeViz");
		Uri sdCardUri = Uri.parse("file://" + Environment.getExternalStorageDirectory());
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, sdCardUri));

		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("image/png");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(saved));

		return sharingIntent;
	}

	/**
	 * Handle share menu button.
	 */
	private void handleShareMenuButton() {
		try {
			String shareText = getResources().getString(R.string.share_text);
			startActivity(Intent.createChooser(getDefaultShareIntent(), shareText));
		} catch (IllegalStateException e) {
			ToastMessages.noExternalStorage();
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), "Sharing failed!");
		}
	}

	/**
	 * Handle home menu button.
	 */
	private void handleHomeMenuButton() {
//		Bundle extras = getIntent().getExtras();
//		int selection = 0;
//		int category = 0;
//		if (extras != null) {
//			category = extras.getInt(Constants.CATEGORY);
//			selection = extras.getInt(Constants.SELECTION);
//		}
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(Constants.CATEGORY, Constants.USAGE);
		intent.putExtra(Constants.SELECTION, 0);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
//		if(selection == 0 && category == 0) 
//			overridePendingTransition(R.anim.down_enter, R.anim.down_leave);
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setIcon(R.drawable.icon);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		prepareBackStack();
		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * Prepare back stack.
	 */
	private void prepareBackStack() {
		try {
			categoryStack.pop();
			categoryStack.pop();
			selectionStack.pop();
			selectionStack.pop();
		} catch (EmptyStackException e) {
		}
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		finish();
		clickCounter--;
		if (clickCounter <= 0) {
			close();
		}
		try {
			prepareBackStack();
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra(Constants.CATEGORY, categoryStack.pop());
			intent.putExtra(Constants.SELECTION, selectionStack.pop());
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} catch (Exception e) {
			close();
		}
	}

	/**
	 * Close.
	 */
	private void close() {
		clearStatics();
		finish();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	/// Location 																				///
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Inits the current location.
	 */
	private void initCurrentLocation() {
		Log.i(getClass().getSimpleName(), "LocationManager initialized");
		try {
			LocationManager locationManager = 
					(LocationManager) getSystemService(Context.LOCATION_SERVICE);
			boolean internetLocation = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			boolean internetEnabled = Network.isNetworkConnected(this);

			if (internetEnabled && internetLocation) {
				Log.i(getClass().getSimpleName(), "Tracking location through internet");
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
				criteria.setPowerRequirement(Criteria.POWER_HIGH);
				criteria.setAltitudeRequired(false);
				criteria.setBearingRequired(false);
				criteria.setSpeedRequired(false);
				criteria.setCostAllowed(true);
				String provider = locationManager.getBestProvider(criteria, true);
				locationManager.requestSingleUpdate(provider, this, null);
			} else {
				ToastMessages.noLocationResource();
			}
		} catch (Exception e) {
			Log.i(getClass().getSimpleName(), "locationServices are disabled");
			showLocationSettings();
		}
	}

	/**
	 * Let the user enable location services.
	 */
	private void showLocationSettings() {
		DialogInterface.OnClickListener dialogClickListener = 
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					ToastMessages.enableLocation();
					Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					MainActivity.this.startActivity(myIntent);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.question_enable_location))
			.setPositiveButton(getResources().getString(R.string.Yes), dialogClickListener)
			.setNegativeButton(getResources().getString(R.string.No), dialogClickListener).show();
	}

	/**
	 * Sets the latest known location.
	 * 
	 * @param currentLocation the latest location
	 */
	private void setLocation(Location currentLocation) {
		double lat = currentLocation.getLatitude();
		double lng = currentLocation.getLongitude();

		String currentCity = getResources().getString(R.string.no_location);
		String currentCountry = getResources().getString(R.string.no_location);

		// US is needed for lookup in CSV file (english country names)
		Geocoder geocoder = new Geocoder(this, Locale.US);
		try {
			if (Network.isNetworkConnected(this)) {
				List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
				if (addresses.size() > 0) {
					Address first = addresses.get(0);
					currentCity = first.getLocality();
					currentCountry = first.getCountryName();
					((HomeVizApplication) getApplication()).setCurrentCountry(currentCountry);
					Log.i(getClass().getSimpleName(), "Updated location, we are in: " + currentCity
									+ ", " + currentCountry);
				} else {
					Log.e(getClass().getSimpleName(), "GeoCoder is down!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		setLocation(location);
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String provider) {
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider) {
		initCurrentLocation();
	}

	/* (non-Javadoc)
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}