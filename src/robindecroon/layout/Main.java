package robindecroon.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.MetaphorActionBarSpinnerListener;
import robindecroon.homeviz.listeners.TotalActionBarSpinnerListener;
import robindecroon.homeviz.listeners.UsageActionBarSpinnerListener;
import robindecroon.homeviz.listeners.YieldActionBarSpinnerListener;
import robindecroon.homeviz.util.Amount;
import robindecroon.homeviz.util.Country;
import robindecroon.homeviz.xml.HomeVizXMLParser;
import robindecroon.stackoverflow.NoDefaultSpinner;
import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class Main extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		// read the configuration XML file
		readHomeVizXML();
		
		// read CO2 data
		readCO2Data();

		// init the actionbar and spinners
		setupActionBar();

		// initial fragment
		Bundle args = new Bundle();
		args.putInt(Constants.UsageType, 0);
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

		final NoDefaultSpinner usageActionBarSpinner = (NoDefaultSpinner) v
				.findViewById(R.id.usage_spinner);
		usageActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.usage_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		usageActionBarSpinner
				.setOnItemSelectedListener(new UsageActionBarSpinnerListener(
						this));

		NoDefaultSpinner totalActionBarSpinner = (NoDefaultSpinner) v
				.findViewById(R.id.you_spinner);
		totalActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.you_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		totalActionBarSpinner
				.setOnItemSelectedListener(new TotalActionBarSpinnerListener(
						this));

		NoDefaultSpinner metaphorActionBarSpinner = (NoDefaultSpinner) v
				.findViewById(R.id.metaphor_spinner);
		metaphorActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(
				this, R.array.metaphor_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		metaphorActionBarSpinner
				.setOnItemSelectedListener(new MetaphorActionBarSpinnerListener(
						this));

		NoDefaultSpinner yieldActionBarSpinner = (NoDefaultSpinner) v
				.findViewById(R.id.yield_spinner);
		yieldActionBarSpinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.yield_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		yieldActionBarSpinner
				.setOnItemSelectedListener(new YieldActionBarSpinnerListener(
						this));

		ab.setCustomView(v);

		return true;
	}

	private void readHomeVizXML() {
		try {
			InputStream in = null;
			in = getAssets().open(Constants.XML_FILE_NAME);
			HomeVizXMLParser parser = new HomeVizXMLParser(
					(HomeVizApplication) getApplication());
			parser.parse(in);
			((HomeVizApplication) getApplication())
					.randomizeLocationsOfPersons();
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
			String[] keys = in.readLine().split(";");
			String line = null;
			Map<String, Country> countries = new HashMap<String, Country>();
			
			 NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
			try {
				while ((line = in.readLine()) != null) {
					String[] values = line.split(";");
					Number co2number = format.parse(values[1]);
					Number kwhnumber = format.parse(values[2]);
					Number liternumber = format.parse(values[3]);

					double co2 = co2number.doubleValue();
					double kwh = kwhnumber.doubleValue();
					double liter = liternumber.doubleValue();
					countries.put(values[0],new Country(values[0],co2, new Amount(kwh), new Amount(liter)));
				}
			} catch (ParseException e) {
				Log.e(getClass().getSimpleName(),"Error in country CSV file!");
			}
			((HomeVizApplication) getApplication()).setCountries(countries);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		// if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
		// getActionBar().setSelectedNavigationItem(
		// savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		// }
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		// outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
		// .getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
