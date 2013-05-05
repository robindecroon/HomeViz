/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.listeners.actionbarlisteners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.activities.MainActivity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * The listener for receiving actionBarSpinner events.
 *
 * @see ActionBarSpinnerEvent
 */
public abstract class ActionBarSpinnerListener implements
		AdapterView.OnItemSelectedListener {

	/** The context. */
	protected MainActivity context;

	/**
	 * Instantiates a new action bar spinner listener.
	 *
	 * @param context the context
	 */
	public ActionBarSpinnerListener(MainActivity context) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {

		// Color the selected item
		TextView t = (TextView) parentView.getChildAt(0);
		t.setTextColor(Constants.SPINNER_TEXT_COLOR);

		// underline the selected item
		String mystring = (String) t.getText();
		SpannableString content = new SpannableString(mystring);
		content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
		t.setText(content);
	}

	/**
	 * Start intent.
	 *
	 * @param position the position
	 * @param category the category
	 */
	protected void startIntent(int position, int category) {
		MainActivity.clickCounter++;
		MainActivity.categoryStack.push(category);
		MainActivity.selectionStack.push(position);
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(Constants.CATEGORY, category);
		intent.putExtra(Constants.SELECTION, position);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		if (MainActivity.lastCatergory != category) {
			context.startActivity(intent);
		} else if (MainActivity.lastPosition != position) {
			context.startActivity(intent);
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android.widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
