package robindecroon.homeviz;

import java.io.IOException;
import java.io.InputStream;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import libraries.stackoverflow.NoDefaultSpinner;

import org.xmlpull.v1.XmlPullParserException;

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

public class Main extends FragmentActivity implements LocationListener {

	public static int lastCatergory;
	public static int lastPosition;

	public static Period currentPeriod = Period.WEEK;
	private static boolean INIT = true;
	public static int page;
	public static boolean downloaded = false;

	public static int clickCounter = 0;

	// nodig voor backbutton
	public static Stack<Integer> categoryStack = new Stack<Integer>();
	public static Stack<Integer> selectionStack = new Stack<Integer>();

	private NoDefaultSpinner usageActionBarSpinner;
	private NoDefaultSpinner totalActionBarSpinner;
	private NoDefaultSpinner metaphorActionBarSpinner;
	private NoDefaultSpinner yieldActionBarSpinner;

	private void clearStatics() {
		INIT = true;
		downloaded = false;
		lastCatergory = Constants.USAGE;
		lastPosition = 0;
		categoryStack.clear();
		selectionStack.clear();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		// init the actionbar and spinners
		setupActionBar();

		if (INIT) {
			// read the configuration XML file
			readHomeVizXML();

			// start with Usage (icons)
			startUsageContainerFragment(0, 0);

			// search the current location
			initCurrentLocation();
			INIT = false;
		} else {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				int selection = extras.getInt(Constants.SELECTION);
				lastPosition = selection;
				int category = extras.getInt(Constants.CATEGORY);
				switch (category) {
				case Constants.USAGE:
					startUsageContainerFragment(selection,
							extras.getInt("room"));
					break;
				case Constants.TOTAL:
					startTotalFragment(selection);
					break;
				case Constants.METAPHOR:
					startMetaphorFragment(selection);
					break;
				case Constants.YIELD:
					startYieldFragment(selection);
					break;
				}

			}
		}
		if (!downloaded) {
			downloadStatistics();
			downloaded = true;
		}
	}

	private void downloadStatistics() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		String user = sp
				.getString("loxone_user", Constants.LOXONE_DEFAULT_USER);
		String password = sp.getString("loxone_password",
				Constants.LOXONE_DEFAULT_PASSWORD);
		String ip = sp.getString("loxone_ip", Constants.LOXONE_DEFAULT_IP);
		new DownloadLoxoneXMLTask(((HomeVizApplication) getApplication()))
				.execute(user, password, ip);
		downloaded = true;
	}

	private void startTotalFragment(int selection) {
		lastCatergory = Constants.TOTAL;
		totalActionBarSpinner.setSelection(selection);
		Fragment fragment2 = new TotalTreeMapFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.TREEMAP_OPTION, selection);
		fragment2.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment2).commit();
	}

	private void startMetaphorFragment(int selection) {
		lastCatergory = Constants.METAPHOR;
		metaphorActionBarSpinner.setSelection(selection);
		Fragment metaphorFragment = new MetaphorContainerFragment();
		Bundle args = new Bundle();
		args.putBoolean(Constants.METAPHOR_CONSUMER, false);
		args.putInt(Constants.METAPHOR_TYPE, selection);
		metaphorFragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, metaphorFragment).commit();
	}

	private void startUsageContainerFragment(int selection, int roomIndex) {
		usageActionBarSpinner.setSelection(selection);
		lastCatergory = Constants.USAGE;
		Bundle args = new Bundle();
		args.putInt(Constants.USAGE_TYPE, selection);
		args.putInt("room", UsageContainerFragment.getCurrentSelection());
		Fragment fragment = new UsageContainerFragment();
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
	}

	private void startYieldFragment(int selection) {
		yieldActionBarSpinner.setSelection(selection);
		lastCatergory = Constants.YIELD;
		Bundle args = new Bundle();
		args.putInt(Constants.YIELD_TYPE, selection);
		Fragment fragment = new YieldFragment();			
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
	}

	private boolean setupActionBar() {
		ActionBar ab = getActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayShowTitleEnabled(false);

		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title, null);

		initSpinners(v);

		ab.setCustomView(v);

		return true;
	}

	public void setUsageIconsSelection(int selection) {
		usageActionBarSpinner.setSelection(selection);
	}

	public void initSpinners(View v) {

		usageActionBarSpinner = (NoDefaultSpinner) v
				.findViewById(R.id.usage_spinner);
		usageActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.usage_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		usageActionBarSpinner
				.setOnItemSelectedListener(new UsageActionBarSpinnerListener(
						this));
		usageActionBarSpinner.setSaveEnabled(false);

		totalActionBarSpinner = (NoDefaultSpinner) v
				.findViewById(R.id.you_spinner);
		totalActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.you_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		totalActionBarSpinner
				.setOnItemSelectedListener(new TotalActionBarSpinnerListener(
						this));
		totalActionBarSpinner.setSaveEnabled(false);

		metaphorActionBarSpinner = (NoDefaultSpinner) v
				.findViewById(R.id.metaphor_spinner);
		metaphorActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(
				this, R.array.metaphor_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		metaphorActionBarSpinner
				.setOnItemSelectedListener(new MetaphorActionBarSpinnerListener(
						this));
		metaphorActionBarSpinner.setSaveEnabled(false);

		yieldActionBarSpinner = (NoDefaultSpinner) v
				.findViewById(R.id.yield_spinner);
		yieldActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.yield_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		yieldActionBarSpinner
				.setOnItemSelectedListener(new YieldActionBarSpinnerListener(
						this));
		yieldActionBarSpinner.setSaveEnabled(false);

	}

	private void readHomeVizXML() {
		try {
			HomeVizApplication app = (HomeVizApplication) getApplication();
			app.reset();
			InputStream in = null;
			
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			String xmlFileName = settings.getString(Constants.XML_FILE, Constants.XML_FILE_NAME);
			if(xmlFileName.equals(Constants.XML_FILE_NAME)) {
				in = getAssets().open(Constants.XML_FILE_NAME);				
			} else {
				in = openFileInput(xmlFileName);
				System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiin");
				System.out.println(in.toString());
			}
			HomeVizXMLParser parser = new HomeVizXMLParser(
					(HomeVizApplication) getApplication());
			parser.parse(in);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

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

	public static boolean externalStorageIsAvailable() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	private Intent getDefaultShareIntent() {
		if (!externalStorageIsAvailable()) {
			throw new IllegalStateException("No external storage available");
		}
		View view = ((ViewGroup) findViewById(android.R.id.content))
				.getChildAt(0);

		view.setDrawingCacheEnabled(true);
		Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);

		String saved = Images.Media.insertImage(this.getContentResolver(), b,
				"HomeViz", "View of HomeViz");
		Uri sdCardUri = Uri.parse("file://"
				+ Environment.getExternalStorageDirectory());
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, sdCardUri));

		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("image/png");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(saved));

		return sharingIntent;
	}

	private void handleShareMenuButton() {
		try {

			startActivity(Intent.createChooser(getDefaultShareIntent(),
					getResources().getString(R.string.share_text)));
		} catch (IllegalStateException e) {
			ToastMessages.noExternalStorage();
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), "Sharing failed!");
		}
	}

	private void handleHomeMenuButton() {
		Bundle extras = getIntent().getExtras();
		int selection = 0;
		if (extras != null) {
			selection = extras.getInt(Constants.SELECTION);
		}
		Intent intent = new Intent(this, Main.class);
		intent.putExtra(Constants.CATEGORY, Constants.USAGE);
		intent.putExtra(Constants.SELECTION, selection);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		overridePendingTransition(R.anim.down_enter, R.anim.down_leave);
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setIcon(R.drawable.icon);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		prepareBackStack();
		super.onRestoreInstanceState(savedInstanceState);
	}

	private void prepareBackStack() {
		try {
			categoryStack.pop();
			categoryStack.pop();
			selectionStack.pop();
			selectionStack.pop();
		} catch (EmptyStackException e) {
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
		clickCounter--;
		if (clickCounter <= 0) {
			close();
		}
		try {
			prepareBackStack();
			Intent intent = new Intent(this, Main.class);
			intent.putExtra(Constants.CATEGORY, categoryStack.pop());
			intent.putExtra(Constants.SELECTION, selectionStack.pop());
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} catch (Exception e) {
			close();
		}
	}

	private void close() {
		clearStatics();
		finish();
	}

	// ////////////////////////////////////////////////////
	// / Locatie ///
	// ////////////////////////////////////////////////////

	private void initCurrentLocation() {
		Log.i(getClass().getSimpleName(), "LocationManager initialized");
		try {
			String provider= null;
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			boolean internetEnabled = Network.isNetworkConnected(this);

			if (internetEnabled) { 
				Log.i(getClass().getSimpleName(), "Tracking location through internet");
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
				criteria.setPowerRequirement(Criteria.POWER_HIGH);
				criteria.setAltitudeRequired(false);
				criteria.setBearingRequired(false);
				criteria.setSpeedRequired(false);
				criteria.setCostAllowed(true);
				provider = locationManager.getBestProvider(criteria, true);
				locationManager.requestSingleUpdate(provider, this, null);
			} else if (gpsEnabled) {
				Log.i(getClass().getSimpleName(), "Tracking location through GPS");
				provider = LocationManager.GPS_PROVIDER;
				locationManager.requestSingleUpdate(provider, this, null);
			} else {
				ToastMessages.noLocationResource();
			}
		} catch (Exception e) {
			Log.i(getClass().getSimpleName(), "locationServices are disabled");
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						ToastMessages.enableLocation();
						Intent myIntent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						Main.this.startActivity(myIntent);
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						// No button clicked
						break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
			builder.setMessage(
					getResources().getString(R.string.question_enable_location))
					.setPositiveButton(getResources().getString(R.string.Yes),
							dialogClickListener)
					.setNegativeButton(getResources().getString(R.string.No),
							dialogClickListener).show();
		}
	}

	/**
	 * Methode die de currentCity en currentCountry variable invult, gebaseerd
	 * op het meegegeven Location object.
	 * 
	 * @param currentLocation
	 *            De huidige locatie.
	 */
	private void setLocation(Location currentLocation) {
		double lat = currentLocation.getLatitude();
		double lng = currentLocation.getLongitude();

		String currentCity = null;
		String currentCountry = null;

		Geocoder geocoder = new Geocoder(this, Locale.US);
		try {
			if (Network.isNetworkConnected(this)) {
				List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
				if (addresses.size() > 0) {
					Address first = addresses.get(0);
					currentCity = first.getLocality();
					currentCountry = first.getCountryName();					
					((HomeVizApplication) getApplication())
					.setCurrentCountry(currentCountry);
					Log.i(getClass().getSimpleName(), "Updated location, we are in: " + currentCity
							+ ", " + currentCountry);
				} else {
					Log.e(getClass().getSimpleName(), "GeoCoder is down!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			currentCity = getResources().getString(R.string.no_location);
			currentCountry = getResources().getString(R.string.no_location);
		}

	}

	@Override
	public void onLocationChanged(Location location) {
		setLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
		initCurrentLocation();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}