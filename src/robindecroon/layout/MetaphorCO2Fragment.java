package robindecroon.layout;

import robindecroon.homeviz.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MetaphorCO2Fragment extends OptionSpinnerFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.usage_layout, container,
				false);

		

		// Time TODO
		initOptionSpinner(rootView, R.id.topSpinner, R.id.topArrowLeft,
				R.id.topArrowRight);

		return rootView;
	}

}
