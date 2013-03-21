package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.layout.Main;
import robindecroon.layout.SpinnerEnum;
import android.view.View;
import android.widget.AdapterView;

public class MetaphorActionBarSpinnerListener extends ActionBarSpinnerListener {


	public MetaphorActionBarSpinnerListener(Main context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {
		
		super.onItemSelected(parentView, selectedItemView, position, id);
		
		startIntent(position, Constants.METAPHOR);

	}

	@Override
	public SpinnerEnum getType() {
		return SpinnerEnum.METAPHOR;
	}
}