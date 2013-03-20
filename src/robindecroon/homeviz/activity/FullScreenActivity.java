package robindecroon.homeviz.activity;

import java.util.GregorianCalendar;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.listeners.HomeVizListener;
import robindecroon.homeviz.listeners.TouchListener;
import robindecroon.homeviz.usage.HouseUsageActivity;
import robindecroon.homeviz.util.DatePickerFragment;
import robindecroon.homeviz.util.DatePickerListener;
import robindecroon.homeviz.util.Period;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Activity die het volledige scherm vult, zonder actionbar en lights out. De
 * periode wordt wel getoond.
 * 
 * @author Robin
 * 
 */
public abstract class FullScreenActivity extends FragmentActivity implements
		HomeVizListener, DatePickerListener {

	/**
	 * De huidige periode waarin HomeViz zich bevindt.
	 */
	protected Period currentPeriod;
	protected boolean actionBarShown = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initPeriod();
		initFullScreen();
	}

	/**
	 * Hang listeners aan eigen views.
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// final Activity context = this;
		// final View rootView = getWindow().getDecorView();
		// rootView.setOnLongClickListener(new OnLongClickListener() {
		//
		// @Override
		// public boolean onLongClick(View v) {
		// System.out.println("LOOOOOOOONG CLICK");
		// if (actionBarShown ) {
		// context.getActionBar().hide();
		// rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		// actionBarShown = false;
		// } else {
		// context.getActionBar().show();
		// rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		// actionBarShown = true;
		// }
		// return false;
		// }
		// });
		setListeners();
	}

	/**
	 * Maak een Settings menu aan.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 0, Menu.NONE, "Settings");
		menu.add(Menu.NONE, 1, Menu.NONE, "House");
		return true;
	}

	/**
	 * Verwerk een menu optie.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case 1:
			Intent intent = new Intent(this, HouseUsageActivity.class);
			startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Verberg de actionbar en initialiseer de main listener.
	 */
	private void initFullScreen() {
		// getActionBar().hide();
		View rootView = getWindow().getDecorView();
		rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		rootView.setOnTouchListener(new TouchListener(this));
	}

	/**
	 * Verwerk een zoom in evenement.
	 */
	@Override
	public void onZoomIn() {
		refreshElements();
	}

	/**
	 * Verwerk een zoom uit evenement.
	 */
	@Override
	public void onZoomOut() {
		refreshElements();
	}

	/**
	 * Refresh de elementen op het scherm. Periode + locatie
	 */
	public void refreshElements() {
		setPeriod();
	}

	/**
	 * Stel de huidige periode in. Ook in de applicatie!
	 * 
	 * @param period
	 */
	public void setCurrentPeriod(Period period) {
		((HomeVizApplication) getApplicationContext()).setCurrentPeriod(period);
		this.currentPeriod = period;
		refreshElements();
	}

	/**
	 * Stel de periode in. Haal deze op uit de applicatie.
	 */
	private void initPeriod() {
		currentPeriod = ((HomeVizApplication) getApplication())
				.getCurrentPeriod();
	}

	@Override
	public void update(GregorianCalendar gregorianCalendar, String tag) {
		try {
			((HomeVizApplication) getApplicationContext())
					.setCurrentPeriod(Period.CUSTOM);
			this.currentPeriod = Period.CUSTOM;

			if (tag.equals(DatePickerFragment.FROM)) {
				currentPeriod.setBegin(gregorianCalendar);
			} else if (tag.equals(DatePickerFragment.UNTIL)) {
				currentPeriod.setEnd(gregorianCalendar);
				refreshElements();
			}
		} catch (Exception e) {
			Log.e("FullScreenActivity", e.getMessage());
		}
	}

	/**
	 * Hang listeners aan de custom views!
	 */
	protected abstract void setListeners();

	/**
	 * Stel de huidige periode in.
	 */
	protected abstract void setPeriod();

}