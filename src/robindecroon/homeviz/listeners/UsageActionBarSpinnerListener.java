package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.layout.Main;
import robindecroon.layout.SpinnerEnum;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

public class UsageActionBarSpinnerListener extends ActionBarSpinnerListener {

	public UsageActionBarSpinnerListener(Main context) {
		super(context);
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {
		
		super.onItemSelected(parentView, selectedItemView, position, id);
				
		startIntent(position, Constants.USAGE);

		
//		Fragment fragment = new UsageContainerFragment();
//		Bundle args = new Bundle();
//		args.putInt(Constants.USAGE_TYPE, position);
//		args.putInt(Constants.FRAGMENT_BUNDLE_TYPE, position);
//		fragment.setArguments(args);
//		context.getSupportFragmentManager().beginTransaction()
//				.replace(R.id.container, fragment).commit();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public SpinnerEnum getType() {
		return SpinnerEnum.USAGE;
	}

}
