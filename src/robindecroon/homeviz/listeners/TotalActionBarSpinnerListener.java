package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.layout.Main;
import robindecroon.layout.SpinnerEnum;
import robindecroon.layout.YouFragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

public class TotalActionBarSpinnerListener extends ActionBarSpinnerListener {

	public TotalActionBarSpinnerListener(Main context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {
		
		super.onItemSelected(parentView, selectedItemView, position, id);
		
		startIntent(position, Constants.TOTAL);

	}

	@Override
	public SpinnerEnum getType() {
		return SpinnerEnum.TOTAL;
	}

}
