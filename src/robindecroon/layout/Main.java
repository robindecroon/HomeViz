package robindecroon.layout;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.xml.HomeVizXMLParser;
import robindecroon.stackoverflow.NoDefaultSpinner;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Main extends FragmentActivity {

	@SuppressLint("NewApi")
	private boolean setupActionBar() {
		ActionBar ab = getActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title, null);

		NoDefaultSpinner spinner2 = (NoDefaultSpinner) v.findViewById(R.id.you_spinner);
		spinner2.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.you_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				((TextView) parentView.getChildAt(0))
						.setTextColor(Constants.SPINNER_TEXT_COLOR);
				if (position == 0) {
					Fragment fragment = new YouFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, fragment).commit();
				} else if (position == 1) {

				} else if (position == 2) {

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		NoDefaultSpinner spinner3 = (NoDefaultSpinner) v.findViewById(R.id.metaphor_spinner);
		spinner3.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.metaphor_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				((TextView) parentView.getChildAt(0))
						.setTextColor(Constants.SPINNER_TEXT_COLOR);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		NoDefaultSpinner spinner4 = (NoDefaultSpinner) v.findViewById(R.id.yield_spinner);
		spinner4.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.yield_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				((TextView) parentView.getChildAt(0))
						.setTextColor(Constants.SPINNER_TEXT_COLOR);
				Fragment yieldFragment = new UsageContainerFragment();
				getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, yieldFragment).commit();		
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		final NoDefaultSpinner spinner = (NoDefaultSpinner) v.findViewById(R.id.usage_spinner);
		spinner.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.usage_spinner_data,
				android.R.layout.simple_spinner_dropdown_item));
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				((TextView) parentView.getChildAt(0))
						.setTextColor(Constants.SPINNER_TEXT_COLOR);
				if (position == 0) {
					Fragment fragment = new UsageFragment();
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();					
				} else if (position == 1) {
					Bundle bundle = new Bundle();
					bundle.putInt(Constants.FRAGMENT_BUNDLE_TYPE, 0);
					Fragment fragment = new UsageChartFragment();
					fragment.setArguments(bundle);
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();					
				} else if (position == 2) {
					Bundle bundle = new Bundle();
					bundle.putInt(Constants.FRAGMENT_BUNDLE_TYPE, 1);
					Fragment fragment = new UsageChartFragment();
					fragment.setArguments(bundle);
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, fragment).commit();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				spinner.setSelected(false);
			}
		});
		setSpinnerSelectionWithoutCallingListener(spinner,0);
		setSpinnerSelectionWithoutCallingListener(spinner2,0);
		setSpinnerSelectionWithoutCallingListener(spinner3,0);
		setSpinnerSelectionWithoutCallingListener(spinner4,0);

		ab.setCustomView(v);
		ab.setDisplayHomeAsUpEnabled(true);

		return true;
	}

	private void setSpinnerSelectionWithoutCallingListener(
			final Spinner spinner, final int selection) {
		spinner.setSelection(-1);
		
//		final OnItemSelectedListener l = spinner.getOnItemSelectedListener();
//		spinner.setOnItemSelectedListener(null);
//		spinner.post(new Runnable() {
//			@Override
//			public void run() {
//				spinner.setSelection(selection);
//				spinner.post(new Runnable() {
//					@Override
//					public void run() {
//						spinner.setOnItemSelectedListener(l);
//					}
//				});
//			}
//		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		readHomeVizXML();

		boolean done = setupActionBar();

		while (!done) {
		}
		;

		Fragment fragment = new UsageContainerFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
	}

	private void readHomeVizXML() {
		try {
			InputStream in = null;
			in = getAssets().open("HomeViz.xml");
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
