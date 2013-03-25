package robindecroon.homeviz.listeners.actionbarlisteners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.Main;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class YieldActionBarSpinnerListener extends ActionBarSpinnerListener {

	public YieldActionBarSpinnerListener(Main context) {
		super(context);
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {

		super.onItemSelected(parentView, selectedItemView, position, id);

		startIntent(position, Constants.YIELD);

	}
}