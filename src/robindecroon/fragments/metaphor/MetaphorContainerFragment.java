package robindecroon.fragments.metaphor;

import robindecroon.homeviz.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MetaphorContainerFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.metaphor_layout, container,
				false);

		Fragment fragment = new MetaphorExpandableListFragment();
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.metaphor_list, fragment).commit();

		Fragment fragment2 = new MetaphorContentFragment();
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.metaphor_content, fragment2).commit();

		return rootView;
	}

}
