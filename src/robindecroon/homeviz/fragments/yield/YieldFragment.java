package robindecroon.homeviz.fragments.yield;

import robindecroon.homeviz.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class YieldFragment extends Fragment {
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.total_treemap_layout,
				container, false);
		TextView t = (TextView) rootView.findViewById(R.id.yield_solar_text);
		t.setText("Hello!!! ");
		return rootView;

	}

}
