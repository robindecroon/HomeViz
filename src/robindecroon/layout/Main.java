package robindecroon.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.actionbarlisteners.MetaphorActionBarSpinnerListener;
import robindecroon.homeviz.listeners.actionbarlisteners.TotalActionBarSpinnerListener;
import robindecroon.homeviz.listeners.actionbarlisteners.UsageActionBarSpinnerListener;
import robindecroon.homeviz.listeners.actionbarlisteners.YieldActionBarSpinnerListener;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Country;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.xml.HomeVizXMLParser;
import robindecroon.stackoverflow.NoDefaultSpinner;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

public class Main extends FragmentActivity implements LocationListener {

	public static int lastCatergory;
	public static int lastPosition;

	private static boolean INIT = true;

	private NoDefaultSpinner usageActionBarSpinner;
	private NoDefaultSpinner totalActionBarSpinner;
	private NoDefaultSpinner metaphorActionBarSpinner;
	private NoDefaultSpinner yieldActionBarSpinner;

	public static int page;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		// init the actionbar and spinners
		setupActionBar();

		if (savedInstanceState == null && INIT) {
			// read the configuration XML file
			readHomeVizXML();

			// read CO2 data
			readCO2Data();

			// start with Usage (icons)
			startUsageContainerFragment(0, 0);

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
					lastCatergory = Constants.METAPHOR;
					metaphorActionBarSpinner.setSelection(selection);
					break;
				case Constants.YIELD:
					lastCatergory = Constants.YIELD;
					yieldActionBarSpinner.setSelection(selection);
					break;
				}

			}
		}
	}

	private void startTotalFragment(int selection) {
		lastCatergory = Constants.TOTAL;
		totalActionBarSpinner.setSelection(selection);
		Fragment fragment2 = new YouFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment2).commit();
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
			in = getAssets().open(Constants.XML_FILE_NAME);
			HomeVizXMLParser parser = new HomeVizXMLParser(
					(HomeVizApplication) getApplication());
			parser.parse(in);
			app.randomizeLocationsOfPersons();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}

	private void readCO2Data() {
		try {
			InputStream input = getAssets().open(Constants.CO2_DATA_FILE_NAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			Map<String, Country> countries = new HashMap<String, Country>();

			NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
			try {
				// The headers
				in.readLine();
				while ((line = in.readLine()) != null) {
					String[] values = line.split(";");
					Number co2number = format.parse(values[1]);
					Number kwhnumber = format.parse(values[2]);
					Number liternumber = format.parse(values[3]);

					double co2 = co2number.doubleValue();
					double kwh = kwhnumber.doubleValue();
					double liter = liternumber.doubleValue();
					countries.put(values[0], new Country(values[0], co2,
							new Amount(kwh), new Amount(liter)));
				}
			} catch (ParseException e) {
				Log.e(getClass().getSimpleName(), "Error in country CSV file!");
			}
			((HomeVizApplication) getApplication()).setCountries(countries);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if (item.getItemId() == android.R.id.home) {
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
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

	// ////////////////////////////////////////////////////
	// / Locatie ///
	// ////////////////////////////////////////////////////

	/**
	 * Deze methode initialiseert het opzoeken van de locatie.
	 * 
	 * De primaire methode is het bepalen van de locatie via Netwerkgegevens.
	 * Als dit niet lukt, wordt er gebruikgemaakt van de GPS (als deze
	 * aanstaat). Is er geen internetverbinding en staat de GPS af, dan wordt de
	 * locatie niet opgezocht.
	 */
	private void initCurrentLocation() {
		LocationManager locationManager;
		String provider;
		try {
			String svcName = Context.LOCATION_SERVICE;
			locationManager = (LocationManager) getSystemService(svcName);

			provider = null;
			boolean gpsEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			boolean internetEnabled = isNetworkConnected();

			if (internetEnabled) {
				Log.i("Homescreen", "Tracking location through internet");
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
				criteria.setPowerRequirement(Criteria.POWER_LOW);
				criteria.setAltitudeRequired(false);
				criteria.setBearingRequired(false);
				criteria.setSpeedRequired(false);
				criteria.setCostAllowed(true);
				provider = locationManager.getBestProvider(criteria, true);
				locationManager.requestSingleUpdate(provider, this, null);
			} else if (gpsEnabled) {
				Log.i("Homescreen", "Tracking location through GPS");
				provider = LocationManager.GPS_PROVIDER;
				locationManager.requestSingleUpdate(provider, this, null);
			} else {
				ToastMessages.noLocationResource();
			}
		} catch (Exception e) {
			Log.i("Homescreen", "locationServices are disabled");
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
	 * Methode die nagaat of de gebruiker met internet verbonden is.
	 * 
	 * @return True als de gebruiker met internet verbonden is.
	 */
	private boolean isNetworkConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			// Geen actieve netwerken
			return true;
		}

		return false;
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

		Geocoder geocoder = new Geocoder(getApplicationContext(),
				Locale.getDefault());
		List<Address> addresses;
		try {
			if (isNetworkConnected()) {
				addresses = geocoder.getFromLocation(lat, lng, 1);
				Address first = addresses.get(0);
				currentCity = first.getLocality();
				currentCountry = first.getCountryName();
			}
		} catch (IOException e) {
			e.printStackTrace();
			currentCity = getResources().getString(R.string.no_location);
			currentCountry = getResources().getString(R.string.no_location);
		}

		((HomeVizApplication) getApplication())
				.setCurrentCountry(currentCountry);
		Log.i("Homescreen", "Updated location, we are in: " + currentCity
				+ ", " + currentCountry);
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
		Log.i("HomeScreen", "Tracking location through: " + provider);
		initCurrentLocation();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}